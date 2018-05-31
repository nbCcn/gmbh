package com.guming.annotation;

import java.lang.annotation.*;

/**
 * @Auther: Ccn
 * @Description:用来标识作为消息key的字段
 * @Date: 2018/5/30 11:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQKey {
    String prefix() default "";
}
