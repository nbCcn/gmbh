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
public class OrderTopicTodayConsumer extends AbstractMQPushConsumer {

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private DingTalkConfig dingTalkConfig;

    @Override
    public boolean process(Object message, Map extMap) {
        log.info("==============================OrderTopicTodayTimer消费者启动================================");


        String str = "181615503924048783";
        PersMsgPush msgPush = new PersMsgPush();
        msgPush.setTouser(str);

        Oa oa = new Oa();

        oa.setMessageUrl("www.baidu.com");
        PerBody body = new PerBody();
        body.setTitle("个人消息测试哈哈哈哈哈哈哈个人消息测试哈哈哈哈哈哈哈个人消息测试哈哈哈哈哈哈哈");
        Form form = new Form();
        form.setKey("个人消息测试哈哈哈哈哈哈哈");
        form.setValue("test");
        List formList = new ArrayList();
        formList.add(form);
        body.setFormList(formList);
        Rich rich = new Rich();
        rich.setNum("998");
        rich.setUnit("元");
        body.setRich(rich);
        body.setContent("个人消息推送+中间件测试！");
        body.setAuthor("Ccn");
        body.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527772329855&di=c095101e1d57d215e1ef14a17c295c69&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F111202%2F58010-1112020ZJ645.jpg");
        oa.setPerBody(body);
        msgPush.setOa(oa);

        if ((Boolean) message) {
            log.info("----------------------is true----------------------");
            try {
                dingTalkService.userMsgPush(msgPush);
            } catch (Exception e) {
                log.info("", e);
            }
        } else {
            log.info("----------------------is flase----------------------");
        }
        return true;
    }
}
