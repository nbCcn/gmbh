package com.guming.MsgPushed;


import com.guming.annotation.MQConsumer;
import com.guming.base.AbstractMQPushConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/23 10:45
 */
@Component
@Slf4j
@MQConsumer(topic = "OrderTopic_today", consumerGroup = "OrderTopic_today")
public class OrderTopicTodayConsumer extends AbstractMQPushConsumer {

    @Override
    public boolean process(Object message, Map extMap) {
        log.info("==============================OrderTopicTodayTimer消费者启动================================");
        System.out.println(message);
        return true;
    }
}
