package com.guming.service.order;


import com.guming.authority.entity.User;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.vo.OrderAuditingVo;
import com.guming.order.vo.OrderVo;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 审核以下的订单状态
 * @Date: 2018/4/28
 */
public interface OrderSubmissionService extends BaseService {

    ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery);

    /**
     * 订单审核
     * @orderId 订单id
     */
    OrderAuditingVo orderAudit(OrderAuditQuery orderAuditQuery);


    /**
     * 订单提交
     * @param orderId       订单id
     * @param sendShopId    收货店铺id
     * @param user           当前用户
     * @return
     */
    String orderSubmit(Long orderId, Long sendShopId, User user);

    OrderVo findById(Long id);

    /**
     * 通过id删除，注意删除请调用该方法，数据问题无法使用触发器实现
     * @param id
     */
    void deleteById(Long id);

    OrderVo findByShopIdAndTempId(Long shopId, Long tempId);

    /**
     * 店铺模板下的购物车
     * @param shopId
     * @param tempId
     * @return
     */
    OrderVo findCartByShopIdAndTempId(Long shopId, Long tempId);

    OrderVo findShopTemplateOrdersInSendTime(Long shopId, Long tempId, Date sendTime, Integer status);

    OrderVo findByTempIdAndStatus(Long tempId, Integer status);

    /**
     * 撤销订单
     * @param id        订单id
     */
    void revokeOrder(Long id);

    /**
     * 检测库存
     * @param id    订单id
     * @return
     */
    List<InventoryProductResponseParam> checkInverntory(Long id);


    Page<OrderSubmission> findOrderSubmissionByPage(OrderQuery orderQuery, Long orderId);

    /**
     * 根据查询条件查找出下一条订单数据
     * @param orderAuditQuery
     * @return      OrderSubmission
     */
    OrderSubmission nextOrderSubmission(OrderAuditQuery orderAuditQuery);

    /**
     * 根据查询条件查找出下一条订单数据
     * @param orderAuditQuery
     * @return      OrderVo
     */
    OrderVo nextOrder(OrderAuditQuery orderAuditQuery);

}
