package com.guming.service.order;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderDelete;
import com.guming.order.vo.OrderVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/28
 */
public interface OrderDeleteService extends BaseService {

    ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery);

    /**
     * 这里的分页只查询已关闭的订单，即关闭时订单状态为2
     * @param orderQuery    订单查询条件
     * @param orderId       订单id
     * @return
     */
    Page<OrderDelete> findOrderDeleteByPage(OrderQuery orderQuery, Long orderId);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVo findById(Long id);

    /**
     * 查看下一个订单
     * @param orderAuditQuery
     * @return
     */
    OrderVo nextOrder(OrderAuditQuery orderAuditQuery);
}
