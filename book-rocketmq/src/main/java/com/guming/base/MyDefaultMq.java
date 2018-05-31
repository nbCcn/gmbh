package com.guming.base;

import com.guming.annotation.MQProducer;
import com.guming.base.AbstractMQProducer;
import org.springframework.stereotype.Component;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/29 18:31
 */
@Component
@MQProducer
public class MyDefaultMq extends AbstractMQProducer {
}
