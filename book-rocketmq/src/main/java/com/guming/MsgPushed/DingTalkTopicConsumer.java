package com.guming.MsgPushed;


import com.guming.annotation.MQConsumer;
import com.guming.base.AbstractMQPushConsumer;
import com.guming.common.utils.StringToListUtils;
import com.guming.config.DingTalkConfig;
import com.guming.dingtalk.DingTalkService;
import com.guming.dingtalk.request.personmsg.*;
import com.guming.dingtalk.response.DingUserInfoResponseParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/23 10:45
 */
@Component
@Slf4j
@MQConsumer(topic = "OrderTopic_today", consumerGroup = "OrderTopic_today")
public class DingTalkTopicConsumer extends AbstractMQPushConsumer {

    @Autowired
    private DingTalkService dingTalkService;

    @Override
    public boolean process(Object message, Map extMap) {
        log.info("==============================OrderTopicTodayTimer消费者启动================================");
        try {
            if (message != null) {
                PersMsgPush msgPush = (PersMsgPush) message;
                dingTalkService.userMsgPush(msgPush);
            }
        }catch (Exception e){
            log.error("",e);
            return false;
        }
        return true;
    }
}
