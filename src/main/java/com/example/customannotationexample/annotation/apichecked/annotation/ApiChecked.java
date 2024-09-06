package com.example.customannotationexample.annotation.apichecked.annotation;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:25
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)   //TYPE类  FIeld 变量
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiChecked {
    /**
     * 用来区分api的名字
     * @return String
     */
    String name() default "default";
    /**
     * 表示当前api开启限制标记
     * @return boolean
     */
    boolean limit() default true;
    /**
     * 表示当前api限制的时间窗口期，单位是秒
     * @return boolean
     */
    long second() default 60;
    /**
     * 表示当前api限制的时间窗口期内允许访问次数
     * @return boolean
     */
    long totalCount() default 100;
    /**
     * 表示当前api限制之后是否开启延迟等待
     * @return boolean
     */
    boolean waitEnable() default false;
    /**
     * 表示当前api限制之后开启延迟等待的限制时间，单位是秒
     * @return long
     */
    long limitWaiting() default 3;


}
