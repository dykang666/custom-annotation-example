package com.example.customannotationexample.annotation.loginrequird;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:33
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private MyHandlerInterceptor myHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
    }
}
