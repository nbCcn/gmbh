package com.guming.MsgPushed.timer;//package com.gumingnc.book.common.MsgPushed.timer;
//
//import com.gm.mq.base.MessageBuilder;
//import com.gumingnc.book.common.MsgPushed.MyProducer;
//import org.apache.rocketmq.common.message.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//
///**
// * @Auther: Ccn
// * @Description:
// * @Date: 2018/5/23 13:35
// */
//@Component
//public class OrderTopicTheLastTimer {
//
//    @Autowired
//    private MyProducer myProducer;
//
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void msgStatus() {
//        System.out.println("==============================OrderTopicTheLastTimer生产者启动================================");
//
//        Message message = MessageBuilder.of("OrderTopicTheLastTimer生产者启动222222222222").topic("OrderTopic_before").build();
//
//        myProducer.syncSend(message);
//
//    }
//
//}
