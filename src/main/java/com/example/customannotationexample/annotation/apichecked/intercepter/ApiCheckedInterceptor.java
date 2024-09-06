package com.example.customannotationexample.annotation.apichecked.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.example.customannotationexample.annotation.apichecked.annotation.ApiChecked;
import com.example.customannotationexample.annotation.apichecked.cache.MyCacheManager;
import com.example.customannotationexample.annotation.apichecked.message.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:36
 */
@Slf4j
@Component
public class ApiCheckedInterceptor implements HandlerInterceptor {
    private final String DEFAULT_NAME = "default";
    private final String TIME_KEY = "limitTime";
    private  final String TOTAL_KEY = "limitTotal";
    private final String WAIT_KEY = "limitWait";

    /**
     * 自定义缓存器，按API分别缓存
     */
    private static final HashMap<String, MyCacheManager> cache = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(" into interceptor ---");
        // 进行注解解析并作相关限制验证
        if (!apiChecked(handler, response)){
            return false;
        }
        return true;
    }
    /**
     * 拦截器方式使用注解，解析注解参数
     * @param handler
     * @param response
     * @return
     * @throws Exception
     */
    private boolean apiChecked(Object handler, HttpServletResponse response) throws Exception{
        // 反射获取方法上的LoginRequred注解
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        ApiChecked interfaceChecked = handlerMethod.getMethod().getAnnotation(ApiChecked.class);
        if(interfaceChecked == null){
            log.info("preHandle interfaceChecked is null ");
            return true;
        }
        // 注解验证无限制直接放行
        if (interfaceChecked.limit()){
            String nameKey = interfaceChecked.name();
            nameKey = DEFAULT_NAME.equals(nameKey) ?  handlerMethod.getMethod().getName() : nameKey ;
            long total = interfaceChecked.totalCount();
            long sec = interfaceChecked.second();
            long wait = interfaceChecked.limitWaiting();
            log.info("total = {}, sec = {}, wait = {}" , total, sec, wait);
            MyCacheManager cacheManager = cache.get(nameKey) ;
            if ( null == cacheManager){
                cacheManager = new MyCacheManager(sec);
                cache.put(nameKey, cacheManager);
                log.info("init cacheManager for api [ {} ], and the period is {} ", nameKey, sec);
            }
            if ( null != cacheManager.get(WAIT_KEY)){
                responseMsg(response, "the [ "+nameKey+" ] API limit, please visit it later wait "+wait+" sec");
                return false ;
            }
            long expiryTime = sec * 1000 ;
            if (null == cacheManager.get(TIME_KEY)){
                LocalDateTime newLimit = LocalDateTime.now().plus(sec, ChronoUnit.SECONDS);
                cacheManager.put(TIME_KEY, newLimit, expiryTime);
                cacheManager.put(TOTAL_KEY, total, expiryTime);
                log.debug("--put cacheManager -- key = {} , value = {}", TIME_KEY, newLimit);
                log.debug("--put cacheManager -- key = {} , value = {}", TOTAL_KEY, total);
            }

            LocalDateTime limitTime = (LocalDateTime) cacheManager.get(TIME_KEY);
            log.debug("limit limitTime -- key = {} , value = {}", TIME_KEY, limitTime);
            long newTotal = (long)cacheManager.get(TOTAL_KEY);
            log.debug("limit newTotal -- key = {} , value = {}", TOTAL_KEY, newTotal);

            long waitTime = wait * 1000 ;
            if (limitTime.isBefore(LocalDateTime.now()) ){
                if (interfaceChecked.waitEnable()) cacheManager.put(WAIT_KEY, wait, waitTime);
                responseMsg(response, "the [ "+nameKey+" ] API limit, The time window has reached the limit time of expiry time");
                return false ;
            }
            if ( newTotal <= 0){
                if (interfaceChecked.waitEnable()) cacheManager.put(WAIT_KEY, wait, waitTime);
                responseMsg(response, "the [ "+nameKey+" ] API limit, The time window has reached the limit number of times ");
                return false ;
            }
            newTotal = newTotal -1 ;
            cacheManager.put(TOTAL_KEY, newTotal, expiryTime);
            log.debug("--put new total value -- key : {} , value {}", TOTAL_KEY, newTotal);
        }
        return true ;
    }


    /**
     * 拦截限制返回
     * @param response
     * @param msg
     * @throws IOException
     */
    private void responseMsg(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding( "UTF-8");
        response.setContentType( "application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            CommonResult<Object> result = CommonResult.fail(msg);
            String res = JSONObject.toJSONString(result);
            out = response.getWriter();
            out.append(res);
        } catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
        }
    }

}
