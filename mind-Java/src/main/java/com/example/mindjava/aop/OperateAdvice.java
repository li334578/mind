package com.example.mindjava.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.example.mindjava.entity.User;
import com.example.mindjava.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2023-08-10 14:18
 * @Description 日志切面
 * @Version 1.0.0
 * @Author liwenbo
 */
@Component
@Aspect
@Slf4j
public class OperateAdvice {
    @Pointcut("execution(* com.example.mindjava.controller.*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        // 获取路径
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //IP地址
        String ip = IPUtils.getRemoteHost(request);
        String url = request.getRequestURL().toString();
        Object object = proceedingJoinPoint.proceed();
        // 请求人
        User user = (User) StpUtil.getSession().get("user");
        log.info("用户 {} IP地址：{}，请求路径：{}，请求参数：{}，返回结果：{}", user == null ? null : user.getUsername(),
                ip, url, args, object);
        return object;
    }
}
