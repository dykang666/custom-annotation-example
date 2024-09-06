package com.example.customannotationexample.annotation.apichecked.config;

import com.example.customannotationexample.annotation.apichecked.intercepter.ApiCheckedInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:40
 */
@Configuration
public class InterceptorTrainConfig implements WebMvcConfigurer {
    /**
     * 拦截器注册类 InterceptorRegistry 加入自己的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiCheckedInterceptor()).addPathPatterns("/api/**");
    }
}
