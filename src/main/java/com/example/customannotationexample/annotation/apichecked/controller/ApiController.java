package com.example.customannotationexample.annotation.apichecked.controller;

import com.example.customannotationexample.annotation.apichecked.annotation.ApiChecked;
import com.example.customannotationexample.annotation.apichecked.message.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/5 20:48
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping("/check")
    @ApiChecked(second = 10, totalCount = 3)
    public CommonResult checkUser() {
        log.info("--@InterfaceChecked  normal  do business ...");
        return CommonResult.ok("@InterfaceChecked is normal, please watch console log.");
    }

    @RequestMapping("/check2")
    @ApiChecked(second = 15, totalCount = 5, limit = false)
    public CommonResult checkUser2() {
        log.info("--@InterfaceChecked limit false  business ...");
        return CommonResult.ok("@InterfaceChecked limit is false, please watch console log.");
    }

}
