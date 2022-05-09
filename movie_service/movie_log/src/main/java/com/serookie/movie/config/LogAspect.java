package com.serookie.movie.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.serookie.entity.Result;
import com.serookie.movie.CustomAnnotation.LogAnnotation;
import com.serookie.entity.Log;
import com.serookie.movie.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/1/1
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private LogMapper logMapper;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.serookie.movie.CustomAnnotation.LogAnnotation)")
    public void LogPointcut() {
    }

    @AfterReturning(returning  /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result  方法返回值
     * @throws Throwable
     */ = "result", value = "LogPointcut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            String jsonString = JSON.toJSONString(result);
            System.out.println(jsonString);
            Result result1 = JSON.parseObject(jsonString, Result.class);
            Log log = new Log();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取注解操作
            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
            if (annotation != null) {
                log.setModel(annotation.operModul());
                log.setType(annotation.operType());
                log.setDescription(annotation.operDesc());
            }
            //操作时间
            log.setCreateTime(Timestamp.valueOf(sdf.format(new Date())));
            //操作用户
             log.setUserCode(request.getHeader("userCode"));
            //操作IP
            log.setIp(request.getRemoteAddr());
            //返回值信息
            log.setResult(result1.getMessage());
            //保存日志
            logMapper.insert(log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
