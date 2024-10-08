package com.example.customannotationexample.annotation.apichecked.message;

import lombok.Data;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:38
 */
@Data
public class CommonResult<T> {
    private Boolean success;

    private String msg;

    private T data;

    public CommonResult(boolean success) {
        this.success = success;
    }
    public CommonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg ;
    }
    public CommonResult(T data) {
        this.success = Boolean.TRUE;
        this.data = data;
    }
    public CommonResult(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg ;
        this.data = data;
    }
    public static <T> CommonResult<T> ok(){
        return new CommonResult(Boolean.TRUE);
    }
    public static <T> CommonResult<T> ok(String msg){
        return new CommonResult(Boolean.TRUE, msg);
    }

    public static <T> CommonResult<T> ok(String msg, T data){
        return new CommonResult(Boolean.TRUE, msg, data);
    }

    public static <T> CommonResult<T> ok(T data){
        return new CommonResult(data);
    }

    public static <T> CommonResult<T> fail(){
        return new CommonResult(Boolean.FALSE);
    }
    public static <T> CommonResult<T> fail(String msg){
        return new CommonResult(Boolean.FALSE, msg);
    }





}
