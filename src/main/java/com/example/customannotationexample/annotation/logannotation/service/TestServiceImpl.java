package com.example.customannotationexample.annotation.logannotation.service;

import com.example.customannotationexample.annotation.logannotation.annotation.LogAnnotation;
import com.example.customannotationexample.annotation.logannotation.util.LogHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/3 15:45
 */
@Component
public class TestServiceImpl implements TestService {
    @Override
    public String testMethod2(String str) {
        LogHelper.info("111");
        LogHelper.warn("222{}",222);
        LogHelper.error("333{}",str);
        return "success";
    }

    @Override
    @LogAnnotation(logTitle = "测试方法报错")
    public void testMethod3(String str) {
        int a = 2/0;
        //return "testSuccess";
    }

    @Override
    @LogAnnotation(logTitle = "map数据保存")
    public Map testMethod4(Map str) {
        LogHelper.info("开始");
        Map result = new HashMap();
        result.put("test",233);
        LogHelper.info("结束{}",233);
        try {
            int i = 1 / 0;
        }catch (Exception e){
            LogHelper.warn("test error", e);
        }

        return result;
    }
}
