package com.example.customannotationexample.annotation.loginrequird;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:27
 */
@Component
@Slf4j
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod handlerMethod = (HandlerMethod)handler;
//        LoginRequird annotation = handlerMethod.getMethod().getAnnotation(LoginRequird.class);
//        //有LoginRequired表明要鉴权,否则放行
//        if (annotation != null) {
//            Object name = request.getSession().getAttribute("name");
//            log.info("name : {}", name);
//            if (name == null) {
//                response.sendRedirect("/user/login");
//                return false;
//            } else {
//                return true;
//            }
//        }
//        log.info("prehandler通过");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
