package com.example.customannotationexample.annotation.logannotation.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.customannotationexample.annotation.logannotation.annotation.LogAnnotation;
import com.example.customannotationexample.annotation.logannotation.constant.LogConstant;
import com.example.customannotationexample.annotation.logannotation.entity.LogEntity;
import com.example.customannotationexample.annotation.logannotation.entity.LogResult;
import com.example.customannotationexample.annotation.logannotation.util.LogUtils;
import lombok.Getter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/9/3 16:06
 */
@Aspect
@Component
public class LogAspect {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    /**
     * 当前方法ThreadLocal
     * 该对象会随着代码运行而动态变化
     */
    @Getter
    private static ThreadLocal<LogEntity> currentThreadLocal = new ThreadLocal<>();

    /**
     * 根方法ThreadLocal
     */
    @Getter
    private static ThreadLocal<LogEntity> rootThreadLocal = new ThreadLocal<>();


    @Around("@annotation(logAnnotation)")
    public Object aroundLog(ProceedingJoinPoint joinpoint, LogAnnotation logAnnotation) throws Throwable {
        //初始化方法标识
        recordMethod(joinpoint, logAnnotation);

        Object result = null;
        Throwable e = null;

        try {
            result = joinpoint.proceed();
            return result;
        }catch (Throwable tempe){
            e = tempe;
            throw e;
        }finally {
            setLogEntityData(logAnnotation, joinpoint, result, e);
            //根方法执行完成后打印日志以及清除ThreadLocal缓存
            if(isRootMethod(joinpoint)) {
                finallyLog(logAnnotation);
                removeThreadLocal();
            }
        }
    }



    private void setLogEntityData(LogAnnotation logAnnotation, ProceedingJoinPoint joinpoint, Object result, Throwable e)  {
        try {
            LogEntity entity = currentThreadLocal.get();
            if(logAnnotation.argsLog()) {
                entity.setArgs(joinpoint.getArgs());
            }
            if(logAnnotation.returnLog()) {
                entity.setResult(result);
            }
            entity.setTitle(logAnnotation.logTitle());
            entity.setThrowable(e);
            entity.setMethod(getMethodStr(joinpoint));
            if(entity.getParent() != null) {
                currentThreadLocal.set(entity.getParent());
            }
        }catch (Exception ee){
            LOGGER.error("LogAnnotation error", ee);
        }
/*        LogEntity entity = currentThreadLocal.get();
        if(entity.getParent() != null) {
            currentThreadLocal.set(entity.getParent());
        }*/
    }

    private boolean isRootMethod(ProceedingJoinPoint joinpoint) throws NoSuchMethodException {
        String methodStr = getMethodStr(joinpoint);
        String rootMethod = rootThreadLocal.get().getMethod();
        return methodStr.equals(rootMethod);
    }

    private void removeThreadLocal() {
        currentThreadLocal.remove();
        rootThreadLocal.remove();
    }


    private String mapLog(Map map){
        JSONObject json = new JSONObject(map);
        return json.toJSONString();
    }

    /**
     * 选择日志输出格式
     * @param logAnnotation
     */
    private void finallyLog(LogAnnotation logAnnotation) {
        LogConstant.LogType logType = logAnnotation.logType();
        String log;
        switch (logType) {
            case JSON:{
                log = finallyLogJSON(logAnnotation);
                break;
            }
            case TEXT:{
                log = finallyLogTEXT(logAnnotation);
                break;
            }
            default: {
                log = finallyLogTEXT(logAnnotation);
                break;
            }
        }
        LogEntity entity = rootThreadLocal.get();
        LogConstant.LogLevel logLevel = entity.getLogLevel();
        if(entity.getThrowable() != null){
            logLevel = LogConstant.LogLevel.ERROR;
        }
        switch (logLevel) {
            case DEBUG: {
                LOGGER.debug(log);
                break;
            }
            case INFO: {
                LOGGER.info(log);
                break;
            }
            case WARN: {
                LOGGER.warn(log);
                break;
            }
            case ERROR: {
                LOGGER.error(log);
                break;
            }
            default: {
                LOGGER.info(log);
                break;
            }

        }

    }

    private String finallyLogTEXT(LogAnnotation logAnnotation) {
        LogEntity entity = rootThreadLocal.get();
        //组装日志格式
        String log = LogUtils.logEntityToTextString(entity);
        return log;
    }

    private String finallyLogJSON(LogAnnotation logAnnotation){
        LogEntity entity = rootThreadLocal.get();
        LogResult result = LogUtils.logEntityToLogResult(entity);
        String log = JSONObject.toJSONString(result);
        return log;
    }

    private static LogResult getLogResult(){
        LogEntity entity = getLogEntity();
        LogResult result = LogUtils.logEntityToLogResult(entity);
        return result;
    }

    private static LogEntity getLogEntity(){
        LogEntity result = rootThreadLocal.get();
        return result;
    }

    public static void log(LogConstant.LogLevel logLevel, String logStr, Object[] args, Throwable throwable){
        LogEntity entity = currentThreadLocal.get();
        LogConstant.LogLevel existLogLevel=null ;
        if(null!=entity){
            entity.getLogLevel();
        }else{
            entity = new LogEntity();
        }
        //日志级别升级
        if(existLogLevel == null || logLevel.compareTo(existLogLevel) > 0){
            entity.setLogLevel(logLevel);
            //父级日志级别升级
            LogEntity parent = entity.getParent();
            for (;;){
                if(parent == null){
                    break;
                }else {
                    parent.setLogLevel(logLevel);
                    parent = parent.getParent();
                }
            }
        }
        LogEntity.Item item = new LogEntity.Item(logLevel, logStr, args, throwable);
        List<LogEntity.Item> items = entity.getItemList();
        if(items == null){
            items = new ArrayList<>();
            entity.setItemList(items);
        }
        items.add(item);
    }

    private void recordMethod(ProceedingJoinPoint joinpoint, LogAnnotation logAnnotation) {
        LogEntity currentEntity = new LogEntity();
        LogEntity rootEntity = rootThreadLocal.get();
        LogEntity parentEntity = currentThreadLocal.get();

        currentEntity.setLogLevel(logAnnotation.defaultLogLevel());
        if(rootEntity == null){
            rootThreadLocal.set(currentEntity);
            currentThreadLocal.set(currentEntity);
        }else {
            currentEntity.setParent(parentEntity);
        }
        if(parentEntity != null) {
            List<LogEntity> logEntityList = parentEntity.getLogEntityList();
            if (logEntityList == null) {
                logEntityList = new ArrayList<>();
                parentEntity.setLogEntityList(logEntityList);
            }
            logEntityList.add(currentEntity);
        }
        currentThreadLocal.set(currentEntity);
    }

    private static String getMethodStr(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodStr = currentMethod.toString();
        return methodStr;
    }

}
