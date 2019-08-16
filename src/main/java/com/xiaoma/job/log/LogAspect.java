package com.xiaoma.job.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LogAspect {


    // 配置织入点
    @Pointcut("execution(* com.xiaoma.job.controller..*.*(..))")
    public void logPointCut() {}


    /**
     * 前置通知 用于拦截操作，在方法返回后执行
     * @param joinPoint 切点
     */
    @AfterReturning(returning = "obj",pointcut = "logPointCut()")
    public Object doBefore(JoinPoint joinPoint,Object obj) {
        handleLog(joinPoint,obj,null);
        return obj;
    }

    /**
     * 拦截异常操作，有异常时执行
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint,null,e);
    }

    private void handleLog(JoinPoint joinPoint, Object obj,Exception e) {
        try {
            // 获得注解
            MyLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 获得方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String action = controllerLog.action();
            String title = controllerLog.title();
            List<Object> args = Arrays.asList(joinPoint.getArgs());



            //打印日志，如有需要还可以存入数据库
            log.info(">>>>>>>>>>>>>模块名称：{}",title);
            log.info(">>>>>>>>>>>>>操作名称：{}",action);
            log.info(">>>>>>>>>>>>>类名：{}",className);
            log.info(">>>>>>>>>>>>>方法名：{}",methodName);
            log.info(">>>>>>>>>>>>>方法参数：{}",args);
            log.info(">>>>>>>>>>>>>返回值:{}",obj);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:",exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private static MyLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            MyLog annotation = method.getAnnotation(MyLog.class);
            return annotation;
        }
        return null;
    }


}
