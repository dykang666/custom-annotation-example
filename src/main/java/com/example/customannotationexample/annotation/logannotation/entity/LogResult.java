package com.example.customannotationexample.annotation.logannotation.entity;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kangdongyang
 * @version 1.0
 * @description: 保存日志返回格式
 * @date 2024/9/3 15:42
 */
@Data
@JSONType(orders={"title","args","result","logLevel","itemList","logEntityList","exception"})
public class LogResult {
    /**
     * 根方法
     */
    private String title;

    /**
     * 保存方法内LogHelper日志信息
     */
    private List<Item> itemList;

    /**
     * 日志等级
     */
    private String logLevel;

    /**
     * 入参
     */
    private Object[] args;

    /**
     * 返回值
     */
    private Object result;

    /**
     * 异常
     * */
    private String exception;

    /**
     * 方法日志链
     */
    List<LogResult> logEntityList;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Item{

        private String logLevel;

        private String logStr;

        private String exception;
    }
}
