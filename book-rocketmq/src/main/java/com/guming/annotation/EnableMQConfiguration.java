package com.guming.annotation;

import java.lang.annotation.*;

/**
 * @Auther: Ccn
 * @Description:开启自动装配
 * @Date: 2018/5/30 11:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableMQConfiguration {
}
