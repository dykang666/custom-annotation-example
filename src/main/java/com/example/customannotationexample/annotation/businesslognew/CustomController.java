package com.example.customannotationexample.annotation.businesslognew;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/1 22:33
 */
public class CustomController {
    @BussinessLogNew
    @RequestMapping
    public Object index(ModelAndView modelAndView, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer size, HttpSession session) {
        return modelAndView;
    }
}
