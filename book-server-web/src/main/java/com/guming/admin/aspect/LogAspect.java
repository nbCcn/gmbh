package com.guming.admin.aspect;

import com.alibaba.fastjson.JSON;
import com.guming.common.logger.YygLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/22
 */
@Aspect
@Component
public class LogAspect {

    private YygLogger logger = new YygLogger(LogAspect.class);

    @Pointcut("execution(public * com.guming.admin.controller.*.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        if(joinPoint == null){
            return;
        }
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder msg = new StringBuilder("\n");
        msg.append("请求url::").append(request.getRequestURI()).append("\n");

        //参数
        Object[] args = joinPoint.getArgs();
        if (args !=null && args.length>0){
            for (Object arg: args){
                if (arg instanceof HttpServletRequest
                        || arg instanceof HttpServletResponse){
                    continue;
                }
                msg.append("请求参数：：").append(JSON.toJSONString(arg));
            }
        }
        logger.operate(msg.toString());
    }
}
