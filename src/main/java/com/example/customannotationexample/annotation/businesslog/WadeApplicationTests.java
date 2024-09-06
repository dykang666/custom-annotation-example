package com.example.customannotationexample.annotation.businesslog;

import java.lang.reflect.Method;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:22
 */
public class WadeApplicationTests {
    public static void main(String[] args) {
        Class<StudentService> studentServiceClass = StudentService.class;
        for (Method m : studentServiceClass.getDeclaredMethods()) {
            BusinessLog annotation = m.getAnnotation(BusinessLog.class);
            System.out.println("读取自定义注解内容:"+annotation.url()+"----"+annotation.value());   //  读取自定义注解内容:user/study----学习接口
        }
    }
}
