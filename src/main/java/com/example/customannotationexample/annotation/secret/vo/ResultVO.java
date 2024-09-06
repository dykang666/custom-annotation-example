package com.example.customannotationexample.annotation.secret.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 18:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;
}
