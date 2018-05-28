package com.guming.service.order.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.OrderStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.OrderUtil;
import com.guming.dao.order.OrderSubmissionRepository;
import com.guming.dao.order.OrderTemplatesSubmissionRepository;
import com.guming.dao.products.ProductsRepository;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.order.vo.OrderVo;
import com.guming.products.entity.Products;
import com.guming.service.order.OrderSubmissionService;
import com.guming.service.order.ShoppingCartService;
import com.guming.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
@Service
public class ShoppingCartServiceImpl extends BaseServiceImpl implements ShoppingCartService {

    @Autowired
    private OrderSubmissionService orderSubmissionService;

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private OrderTemplatesSubmissionRepository orderTemplatesSubmissionRepository;

    @Override
    protected BaseRepository getRepository() {
        return null;
    }

    @Override
    public ResponseParam<OrderVo> getOrderCart(Long shopId, Long tempId){
        if (tempId == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_ID_EMPTY);
        }
        if (shopId == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SHOP_ID_EMPTY);
        }
        OrderVo orderVo =  orderCart(shopId,tempId);
        if(orderVo == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_CART_NOT_EXISTS);
        }
        return ResponseParam.success(orderVo);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo orderCart(Long shopId,Long tempId){
        return orderSubmissionService.findCartByShopIdAndTempId(shopId,tempId);
    }

    @Override
    public OrderVo orderCart(Long cartId) {
        return orderSubmissionService.findByTempIdAndStatus(cartId,OrderStatus.UNSUBMITTED.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateCartProductAmount(Long cartId, Long productId, Integer amount) {
        OrderSubmission orderSubmission = orderSubmissionRepository.findByIdAndStatus(cartId,OrderStatus.UNSUBMITTED.getCode());
        if (orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_CART_NOT_EXISTS);
        }

        //商品限购认证
        Products products = productsRepository.findOne(productId);
        productsService.checkProductsLimit(products,amount);

        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();

        //该flag用于判断原来购物车中的商品是否有传入的商品
        Boolean flag = false;
        List<OrderTemplatesSubmission> needToDeleteOrderTemplatesSubmission = new ArrayList<OrderTemplatesSubmission>();
        List<OrderTemplatesSubmission> needToUpdateOrderTemplatesSubmission = new ArrayList<OrderTemplatesSubmission>();
        if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList){
                if (orderTemplatesSubmission.getProductId().equals(productId)){
                    if (amount >0) {
                        //是原有商品就直接更新
                        orderTemplatesSubmission.setAmount(amount);
                        needToUpdateOrderTemplatesSubmission.add(orderTemplatesSubmission);
                    }else{
                        needToDeleteOrderTemplatesSubmission.add(orderTemplatesSubmission);
                    }
                    flag = true;
                    break;
                }
            }
        }

        if (!flag){
            OrderTemplatesSubmission orderTemplatesSubmission  = new OrderTemplatesSubmission();
            orderTemplatesSubmission.setIsValid(true);
            orderTemplatesSubmission.setProductId(productId);
            orderTemplatesSubmission.setOrderId(cartId);
            orderTemplatesSubmission.setCode(OrderUtil.versionGenerate());
            orderTemplatesSubmission.setAmount(amount);
            orderTemplatesSubmissionList.add(orderTemplatesSubmission);
            orderSubmissionRepository.save(orderSubmission);
        }else{
            if (!needToDeleteOrderTemplatesSubmission.isEmpty()){
                orderTemplatesSubmissionRepository.deleteInBatch(needToDeleteOrderTemplatesSubmission);
            }
            if (!needToUpdateOrderTemplatesSubmission.isEmpty()){
                orderTemplatesSubmissionRepository.save(needToUpdateOrderTemplatesSubmission);
            }
        }
        return getSuccessOperationResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam deleteCartProducts(List<Long> ids) {
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList= orderTemplatesSubmissionRepository.findAll(ids);
        if (orderTemplatesSubmissionList != null) {
            orderTemplatesSubmissionRepository.delete(orderTemplatesSubmissionList);
        }
        return getSuccessDeleteResult();
    }
}
