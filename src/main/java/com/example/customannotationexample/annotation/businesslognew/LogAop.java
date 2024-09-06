package com.example.customannotationexample.annotation.businesslognew;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 22:25
 */
@Aspect
@Component
@Slf4j
public class LogAop {
    @Pointcut(value = "@annotation(com.example.customannotationexample.annotation.businesslognew.BussinessLogNew)")
    public void cutService() {
    }
    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        //先执行业务
        Object result = point.proceed();
        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }
    private void handle(ProceedingJoinPoint point) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();
        //如果当前用户未登录，不做日志
//        ShiroUser user = ShiroKit.getUser();
//        if (null == user) {
//            return;
//        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        //获取操作名称
        BussinessLogNew annotation = currentMethod.getAnnotation(BussinessLogNew.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        //Class dictClass = annotation.dict();
        String url = annotation.url();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }
        //保存日志
        //LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(), user.getShopId(), bussinessName, className, methodName, msg, url));

    }


}
