package com.example.customannotationexample.annotation.loginrequird;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:25
 */
//接口需要登录认证
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  LoginRequird {
}
