package com.guming.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Auther: Ccn
 * @Description:生产者自动装配注解
 * @Date: 2018/5/30 11:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MQProducer {
}
