package com.example.customannotationexample.annotation.secret.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kangdongyang
 * @version 1.0
 * @description: 用户
 * @date 2024/9/5 18:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends BaseVO {
    private Integer id;
    private String name;
}
