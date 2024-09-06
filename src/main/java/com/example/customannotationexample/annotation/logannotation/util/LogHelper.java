package com.example.customannotationexample.annotation.logannotation.util;

import com.example.customannotationexample.annotation.logannotation.aop.LogAspect;
import com.example.customannotationexample.annotation.logannotation.constant.LogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kangdongyang
 * @version 1.0
 * @description: 必须配合LogAnnotation使用
 * @date 2024/9/3 15:46
 */
public class LogHelper {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static void debug(String var1){
        log(LogConstant.LogLevel.DEBUG, var1, null, null);
    }

    public static void debug(String var1, Object... var2){
        log(LogConstant.LogLevel.DEBUG, var1, var2, null);
    }

    public static void debug(String var1, Throwable var2){
        log(LogConstant.LogLevel.DEBUG, var1, null, var2);
    }

    public static void info(String var1){
        log(LogConstant.LogLevel.INFO, var1, null, null);
    }

    public static void info(String var1, Object... var2){
        log(LogConstant.LogLevel.INFO, var1, var2, null);
    }

    public static void info(String var1, Throwable var2){
        log(LogConstant.LogLevel.INFO, var1, null, var2);
    }

    public static void warn(String var1){
        log(LogConstant.LogLevel.WARN, var1, null, null);
    }

    public static void warn(String var1, Object... var2){
        log(LogConstant.LogLevel.WARN, var1, var2, null);
    }

    public static void warn(String var1, Throwable var2){
        log(LogConstant.LogLevel.WARN, var1, null, var2);
    }

    public static void error(String var1){
        log(LogConstant.LogLevel.ERROR, var1, null, null);
    }

    public static void error(String var1, Object... var2){
        log(LogConstant.LogLevel.ERROR, var1, var2, null);
    }

    public static void error(String var1, Throwable var2){
        log(LogConstant.LogLevel.ERROR, var1, null, var2);
    }

    private static void log(LogConstant.LogLevel logLevel, String logStr, Object[] args, Throwable throwable){
        LogAspect.log(logLevel, logStr, args, throwable);
    }
}
