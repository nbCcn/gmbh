package com.guming.service.order;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.vo.OrderVo;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
public interface ShoppingCartService extends BaseService {

    /**
     * 获取购物车
     * @param shopId    商店id
     * @param tempId    模板id
     * @return
     */
    ResponseParam<OrderVo> getOrderCart(Long shopId, Long tempId);

    /**
     * 获取购物车
     * @param shopId    商店id
     * @param tempId    模板id
     * @return
     */
    OrderVo orderCart(Long shopId, Long tempId);

    /**
     * 获取购物车
     * @param cartId    购物车id
     * @return
     */
    OrderVo orderCart(Long cartId);

    /**
     * 更新购物车的商品数量
     * @param cartId
     * @param productId
     * @param amount
     * @return
     */
    ResponseParam updateCartProductAmount(Long cartId, Long productId, Integer amount);

    /**
     * 删除购物车中的商品
     * @param ids
     */
    ResponseParam deleteCartProducts(List<Long> ids);
}
