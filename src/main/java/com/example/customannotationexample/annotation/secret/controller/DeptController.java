package com.example.customannotationexample.annotation.secret.controller;

import com.example.customannotationexample.annotation.secret.annotation.Secret;
import com.example.customannotationexample.annotation.secret.vo.DeptVO;
import com.example.customannotationexample.annotation.secret.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 18:29
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    @GetMapping("getDeptName/{id}")
    public ResultVO getDeptName(@PathVariable("id") String id){
        return new ResultVO(0,"查询成功","财务部" + id);
    }

    // 注解在方法上，并传递了encryptStrName自己定义的加密字符串名称encryptJson
    @Secret(value = DeptVO.class,encryptStrName = "encryptJson")
    @PostMapping("addDept")
    public ResultVO addDept(@RequestBody DeptVO dept){
        return new ResultVO(0,"新增成功",dept);
    }
}
