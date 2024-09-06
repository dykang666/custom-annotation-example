package com.example.customannotationexample.annotation.businesslog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:21
 */
@Target(ElementType.METHOD)   //TYPE类  FIeld 变量
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessLog {
    String url() default "";
    String value() default "";
}
