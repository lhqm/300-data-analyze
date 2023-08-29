package com.fox.annotation;

import com.fox.constant.CacheConstant;
import com.fox.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CheckFlagAspect {
    @Autowired
    private RedisUtil redisUtil;
    @Around("@annotation(CheckFlag)")
    public Object checkFlag(ProceedingJoinPoint joinPoint) throws Throwable {
        String flag = redisUtil.get(CacheConstant.PROJECT_ACTIVE_SIGNAL);
        if (flag != null && flag.equals("false")) {
            log.info(joinPoint.getSignature().getName()+"已经阻止执行。");
            return null;
        }
        return joinPoint.proceed();
    }
}