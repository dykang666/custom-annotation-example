package com.example.customannotationexample.annotation.businesslognew;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLogNew {
    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";
    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
//    Class<? extends AbstractDictMap> dict() default SystemDict.class;

    /**
     * 菜单url
     */
    String url() default "";







}
