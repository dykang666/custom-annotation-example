package com.example.customannotationexample.annotation.logannotation.annotation;

import com.example.customannotationexample.annotation.logannotation.constant.LogConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:  统一入参，输出，异常日志处理注解
 * @date 2024/9/2 16:56
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 日志标题
     */
    String logTitle();

    /**
     * 是否打印参数日志
     */
    boolean argsLog() default false;

    /**
     * 是否打印返回值日志
     */
    boolean returnLog() default false;

    /**
     *
     */
    LogConstant.LogLevel defaultLogLevel() default LogConstant.LogLevel.INFO;

    LogConstant.LogType logType() default LogConstant.LogType.TEXT;
}
