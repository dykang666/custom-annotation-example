package com.example.customannotationexample.logannotation;

import com.alibaba.fastjson.JSON;
import com.example.customannotationexample.annotation.secret.util.AESUtils;
import com.example.customannotationexample.annotation.secret.vo.DeptVO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/3 16:16
 */
public class DemoSecretTests {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Test
    public void contextLoads() {
        try {
            String json = JSON.toJSONString(new DeptVO(123,"研发部",""));
            System.out.println(AESUtils.encrypt(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
