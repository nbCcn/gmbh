package com.guming.timer;

import com.guming.base.MyDefaultMq;
import com.guming.base.MessageBuilder;
import com.guming.dingtalk.DingTalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
    private DingTalkService dingTalkService;

    @Autowired
    private MyDefaultMq myDefaultMq;

    @Scheduled(cron = "0/10 * * * * ?")
    public void msgStatus() {
        log.info("==============================OrderTopicTodayTimer生产者启动================================");

        myDefaultMq.syncSend(MessageBuilder.of(true).topic("OrderTopic_today").build());
    }

}
