package com.guming.service.order;


import com.guming.authority.entity.User;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.order.dto.OrderDto;
import com.guming.order.dto.RecopientDto;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.vo.OrderQueryVo;
import com.guming.order.vo.OrderVo;
import com.guming.shops.vo.ShopVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
public interface OrderService extends BaseService {
    ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery);

    /**
     * 通过id查询订单并携带当前的查询参数
     * @param orderAuditQuery
     * @return
     */
    OrderQueryVo findByIdWithQuery(OrderAuditQuery orderAuditQuery);

    /**
     * 通过id查找订单
     * @param orderId   订单id
     * @param status    订单状态
     * @return
     */
    OrderVo findById(Long orderId, Integer status);


    /**
     * 通过商店id获取相应的收货店铺
     * @param recopientDto
     * @return
     */
    ResponseParam<List<ShopVo>> getRecipientShop(RecopientDto recopientDto);

    /**
     * 订单提交
     * @param orderId       订单id
     * @param sendShopId    收货店铺id
     * @param user           当前用户
     * @return
     */
    ResponseParam orderSubmit(Long orderId, Long sendShopId, User user);

    /**
     * 订单审核
     * @param orderAuditQuery  订单审核实体
     * @return
     */
    ResponseParam orderAudit(OrderAuditQuery orderAuditQuery);

    /**
     * 订单发货
     * @param id  订单id
     * @return
     */
    ResponseParam orderDelivery(Long id);

    /**
     * 订单完成
     * @param id 订单id
     */
    ResponseParam orderFinish(Long id);

    /**
     * 撤销已提交的定单
     * @param id        订单id
     * @return
     */
    ResponseParam revokeOrder(Long id);



    ResponseParam<List<MapVo>> getOrderStatusList();

    /**
     * 检测订单模板是否能够使用，确定能使用后同时生成购物车
     * @param orderDto
     * @return
     */
    ResponseParam<Long> checkCart(OrderDto orderDto);

    /**
     * 检测店铺某个订单下的某个送货时间的订单
     * @param shopId        店铺id
     * @param tempId        模板id
     * @param sendTime      送货时间
     * @return
     */
    OrderVo findShopTemplateOrdersInSendTime(Long shopId, Long tempId, Date sendTime);


    /**
     * 检测订单商品库存
     * @param id    订单id
     * @return
     */
    ResponseParam<List<InventoryProductResponseParam>> checkStock(Long id);


    /**
     * 获取订单状态数据
     * @return
     */
    List<MapVo> getOrderStatusMapVoList();

    /**
     * 根据查询条件查找出下一条订单数据
     * @param orderAuditQuery
     * @return      OrderVo
     */
    OrderVo nextOrder(OrderAuditQuery orderAuditQuery);

    /**
     * 验证已提交的订单是否存在
     * @param orderId   订单id
     */
    OrderSubmission validateOrderSubmit(Long orderId);

}
