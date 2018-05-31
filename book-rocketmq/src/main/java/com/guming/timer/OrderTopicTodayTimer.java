package com.guming.timer;

import com.guming.base.MyDefaultMq;
import com.guming.base.MessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/23 13:35
 */
@Component
@Slf4j
@EnableScheduling
public class OrderTopicTodayTimer {


    @Autowired
    private MyDefaultMq myDefaultMq;

    public void msgStatus() {
        log.info("==============================OrderTopicTodayTimer生产者启动================================");

        myDefaultMq.syncSend(MessageBuilder.of("消息测试11111").topic("OrderTopic_today").build());
        System.out.println(1);
    }

}
