package com.guming.admin.timer;

import com.guming.common.constants.business.OrderStatus;
import com.guming.common.utils.DateUtil;
import com.guming.dao.order.OrderAuditingRepository;
import com.guming.order.entity.OrderAuditing;
import com.guming.order.entity.OrderSubmission;
import com.guming.service.order.OrderService;
import com.guming.service.order.OrderSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */

@Component
public class OrderStatusTimer {

    private Logger logger = LoggerFactory.getLogger(OrderStatusTimer.class);

    @Autowired
    private OrderAuditingRepository orderAuditingRepository;

    @Autowired
    private OrderSubmissionService orderSubmissionService;

    @Autowired
    private OrderService orderService;

    /**
     * 订单状态定时器
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOrderStatus(){
        logger.info("==============================订单状态定时器启动================================");
        List<OrderAuditing> orderAuditingList = orderAuditingRepository.findAllOrderAuditing();
        if (orderAuditingList!=null && !orderAuditingList.isEmpty()){
           for (OrderAuditing orderAuditing : orderAuditingList){
                if (orderAuditing.getStatus().equals(OrderStatus.DELIVERED.getCode())){
                    orderAuditing.setStatus(OrderStatus.COMPLETE.getCode());
                    orderService.orderFinish(orderAuditing.getId());
                }else if (orderAuditing.getStatus()!= null && orderAuditing.getSendTime()!= null){
                    if (orderAuditing.getStatus().equals(OrderStatus.AUDITED.getCode()) && orderAuditing.getSendTime().getTime()<=DateUtil.getCurrentStartDay().getTime()) {
                        orderAuditing.setStatus(OrderStatus.DELIVERED.getCode());
                        orderAuditingRepository.save(orderAuditing);
                    }
                }
           }
        }
        logger.info("==============================订单状态定时器结束================================");
    }

    /**
     * 删除过期未审核订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteExpireOrder(){
        logger.info("==============================删除过期未审核订单定时器启动================================");
        List<OrderSubmission> orderSubmissionList = orderSubmissionService.findExpireUnAuditOrder();
        orderSubmissionService.deleteOrder(orderSubmissionList);
        logger.info("==============================删除过期未审核订单定时器结束================================");
    }
}
