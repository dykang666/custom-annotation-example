package com.example.customannotationexample.logannotation;

import com.example.customannotationexample.annotation.logannotation.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/3 16:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogAnnotationApplicationTests {
    @Autowired
    TestService service;

    /**
     * 测试正常方法返回，log level随着LogHelper调用的日志级别提升
     */
    @Test
    public void test() {
        service.testMethod2("test start3");
    }

    /**
     * 测试正常方法报错，log level默认error
     */
    @Test
    public void test1() {
        service.testMethod3("test start3");
    }

    /**
     *
     */
    @Test
    public void test2(){
        Map param = new HashMap();
        param.put("paramTest",111);
        System.out.println("..."+service.testMethod4(param));
    }
}
