package com.guming.MsgPushed;


import com.guming.annotation.MQConsumer;
import com.guming.base.AbstractMQPushConsumer;
import com.guming.common.utils.StringToListUtils;
import com.guming.dingtalk.DingTalkService;
import com.guming.dingtalk.request.personmsg.Body;
import com.guming.dingtalk.request.personmsg.Form;
import com.guming.dingtalk.request.personmsg.PersonMsgPush;
import com.guming.dingtalk.request.personmsg.Rich;
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

    @Override
    public boolean process(Object message, Map extMap) {
        log.info("==============================OrderTopicTodayTimer消费者启动================================");


        DingUserInfoResponseParam userInfoByUserId = dingTalkService.getUserInfoByUserId("181615503924048783");





        List<String> list = new ArrayList<>();
        list.add("181615503924048783");
        String str = StringToListUtils.parseString(list);
        PersonMsgPush msgPush = new PersonMsgPush();
        msgPush.setMessageUrl("www.baidu.com");
        Body body = new Body();
        body.setTitle("个人推送测试");
        Form form = new Form();
        form.setKey("程程");
        form.setValue("test");
        List formList = new ArrayList();
        formList.add(form);
        body.setFormList(formList);
        Rich rich = new Rich();
        rich.setNum("998");
        rich.setUnit("元");
        body.setRich(rich);
        msgPush.setBody(body);
        msgPush.setContent("个人消息推送+中间件测试！");
        msgPush.setAuthor("Ccn");
        msgPush.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527772329855&di=c095101e1d57d215e1ef14a17c295c69&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F111202%2F58010-1112020ZJ645.jpg");
        if ((Boolean) message) {
            log.info("----------------------is true----------------------");
            try {
                dingTalkService.userMsgPush(str, msgPush);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.info("----------------------is flase----------------------");
        }
        return true;
    }
}
