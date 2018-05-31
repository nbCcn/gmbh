package com.guming.MsgPushed;//package com.gumingnc.book.common.MsgPushed;
//
//import com.gm.mq.annotation.MQConsumer;
//import com.gm.mq.base.AbstractMQPushConsumer;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * @Auther: Ccn
// * @Description:
// * @Date: 2018/5/23 10:45
// */
//@Component
//@MQConsumer(topic = "OrderTopic_before", consumerGroup = "OrderTopic_before")
//public class OrderTopicBeforeConsumer extends AbstractMQPushConsumer {
//
//    @Override
//    public boolean process(Object message, Map extMap) {
//        // extMap 中包含messageExt中的属性和message.properties中的属性
//        System.out.println(message);
//        return true;
//    }
//
//}
