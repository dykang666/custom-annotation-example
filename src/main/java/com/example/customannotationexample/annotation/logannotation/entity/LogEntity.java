package com.example.customannotationexample.annotation.logannotation.entity;

import com.example.customannotationexample.annotation.logannotation.constant.LogConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:  保存日志组件所需数据所用类
 * @date 2024/9/3 15:41
 */
@Data
public class LogEntity {

    private LogEntity parent;

    /**
     * 根方法
     */
    private String method;

    /**
     * 保存方法内LogHelper日志信息
     */
    private List<Item> itemList;

    /**
     * 日志等级
     */
    private LogConstant.LogLevel logLevel;

    /**
     * 入参
     */
    private Object[] args;

    /**
     * 标题
     */
    private String title;

    /**
     * 返回值
     */
    private Object result;

    /**
     * 异常
     */
    private Throwable throwable;

    /**
     * 方法日志链
     */
    List<LogEntity> logEntityList;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Item{

        private LogConstant.LogLevel level;

        private String logStr;

        private Object[] args;

        private Throwable throwable;
    }

}
