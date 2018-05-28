package com.guming.service.order;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderAuditing;
import com.guming.order.vo.OrderVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:  已审核和已发货的订单数据
 * @Date: 2018/4/28
 */
public interface OrderAuditingService extends BaseService {

    ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery);

    OrderVo findById(Long id);

    /**
     * 订单发货
     * @param id  订单id
     * @return
     */
    void orderDelivery(Long id);

    /**
     * 订单完成
     * @param id 订单id
     */
    void orderFinish(Long id);

    OrderVo findShopTemplateOrdersInSendTime(Long shopId, Long tempId, Date sendTime, Integer status);

    OrderVo nextOrder(OrderAuditQuery orderAuditQuery);

    OrderAuditing nextOrderAuditing(OrderAuditQuery orderAuditQuery);
}
