package com.example.customannotationexample.annotation.secret.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 18:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptVO {
    private Integer id;
    private String deptName;

    // 自己实现的一个参数，用来给前端传递加密字符串
    private String encryptJson;
}
