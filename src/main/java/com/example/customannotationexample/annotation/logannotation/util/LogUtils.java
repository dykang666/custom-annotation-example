package com.example.customannotationexample.annotation.logannotation.util;

import com.alibaba.fastjson.JSONObject;
import com.example.customannotationexample.annotation.logannotation.constant.LogConstant;
import com.example.customannotationexample.annotation.logannotation.entity.LogEntity;
import com.example.customannotationexample.annotation.logannotation.entity.LogResult;
import org.apache.commons.collections.CollectionUtils;



import org.slf4j.helpers.MessageFormatter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:  日志数据格式转换
 * @date 2024/9/3 15:47
 */
public class LogUtils {
    public static LogResult logEntityToLogResult(LogEntity entity) {
        LogResult result = new LogResult();
        result.setTitle(entity.getTitle());
        result.setItemList(logEntityItemsToLogResultItems(entity.getItemList()));
        result.setLogLevel(entity.getLogLevel().name());
        result.setArgs(entity.getArgs());
        result.setResult(entity.getResult());
        if(entity.getThrowable() != null) {
            if(entity.getParent() == null){
                result.setException(ExceptionUtils.getStackTrace(entity.getThrowable()));
            }else {
                result.setException(entity.getThrowable().toString());
            }
        }
        if(CollectionUtils.isNotEmpty(entity.getLogEntityList())){
            List<LogResult> logResultList = new ArrayList<>();
            for (LogEntity logEntity : entity.getLogEntityList()) {
                logResultList.add(logEntityToLogResult(logEntity));
            }
            result.setLogEntityList(logResultList);
        }

        return result;
    }

    private static List<LogResult.Item> logEntityItemsToLogResultItems(List<LogEntity.Item> items){
        if(items == null){
            return null;
        }
        List<LogResult.Item> result = new ArrayList<>();
        for (LogEntity.Item item : items) {
            LogResult.Item resultItem = new LogResult.Item();
            if(item.getLevel() == null){
                resultItem.setLogLevel(LogConstant.LogLevel.INFO.name());
            }else {
                resultItem.setLogLevel(item.getLevel().name());
            }

            if(item.getArgs() != null){
                resultItem.setLogStr(MessageFormatter.arrayFormat(item.getLogStr(), item.getArgs()).getMessage());
            } else{
                resultItem.setLogStr(item.getLogStr());
            }
            if(item.getThrowable() != null){
                resultItem.setException(ExceptionUtils.getStackTrace(item.getThrowable()));
            }
            result.add(resultItem);
        }

        return result;
    }

    public static String logEntityToTextString(LogEntity entity) {
        StringBuilder log = new StringBuilder("\n");
        logEntityToTextString(log, entity, "");
        return log.toString();
    }

    private static void logEntityToTextString(StringBuilder log, LogEntity entity, String space) {
        //StringBuilder log = new StringBuilder();
        Object[] args = entity.getArgs();
        List<LogEntity.Item> itemList = entity.getItemList();
        Object result = entity.getResult();
        Throwable throwable = entity.getThrowable();
        List<LogEntity> logEntityList = entity.getLogEntityList();

        log.append(space).append("标题:").append(entity.getTitle()).append(";执行是否成功:").append(throwable == null).append("\n");
        if(args != null){
            log.append(space).append("参数:").append(argsToString(args)).append("\n");
        }
        if(CollectionUtils.isNotEmpty(itemList)){
            log.append(space).append("日志:\n");
            for (LogEntity.Item item : itemList) {
                if(item.getArgs() != null){
                    log.append(space).append(MessageFormatter.arrayFormat(item.getLogStr(), item.getArgs()).getMessage()).append("\n");
                } else{
                    log.append(space).append(item.getLogStr()).append("\n");
                }
                if(item.getThrowable() != null){
                    log.append(space).append(ExceptionUtils.getStackTrace(item.getThrowable())).append("\n");
                }
            }
        }
        if(result != null){
            log.append(space).append("返回值:").append(JSONObject.toJSONString(result)).append("\n");
        }
        if(throwable != null){
            if(entity.getParent() == null){
                log.append(space).append("异常:").append(ExceptionUtils.getStackTrace(throwable)).append("\n");
            }else {
                log.append(space).append("异常:").append(throwable.toString()).append("\n");
            }
        }
        if(CollectionUtils.isNotEmpty(logEntityList)){
            log.append(space).append("方法栈:\n");
            String nextSpace = space + "\t";
            for (int i = 0; i < logEntityList.size(); i++) {
                LogEntity logEntity = logEntityList.get(i);
                logEntityToTextString(log, logEntity, nextSpace);
                if(i < logEntityList.size() - 1){
                    log.append("\n");
                }
            }
        }
    }

    private static String argsToString(Object args[]){
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < args.length; i++) {
            if(i != 0){
                result.append(",");
            }
            Object arg = args[i];
            if(arg == null) {
                result.append("null");
            }else {
                result.append(JSONObject.toJSONString(arg));
				/*if (arg instanceof AbstractMap) {
					Map map = (Map) arg;
					result.append(new JSONObject(map).toJSONString());
				} else {
					result.append(arg.toString());
				}*/
            }
        }
        result.append("]");
        return result.toString();
    }
}
