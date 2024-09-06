package com.example.customannotationexample.annotation.businesslog;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 20:22
 */
public class StudentService {
    @BusinessLog(url = "学习接口",value = "/user/study")
    public void study() {
        System.out.println("正在教室学习");
    }
}
