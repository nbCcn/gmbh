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
import com.guming.dao.orderTemplate.TemplateProductsRepository;
import com.guming.dao.products.ProductsRepository;
import com.guming.order.dto.OrderTemplateAddDto;
import com.guming.order.dto.OrderTemplateProductAddDto;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.orderTemplate.entity.TemplateProducts;
import com.guming.products.entity.Products;
import com.guming.service.order.OrderService;
import com.guming.service.order.OrderTemplateService;
import com.guming.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
@Service
public class OrderTemplateServiceImpl extends BaseServiceImpl implements OrderTemplateService {

    @Autowired
    private OrderTemplatesSubmissionRepository orderTemplatesSubmissionRepository;

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private TemplateProductsRepository templateProductsRepository;

    @Autowired
    private OrderService orderService;

    @Override
    protected BaseRepository getRepository() {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateAmountById(Long id, Integer amount, Long orderId) {
        if (id==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        if (amount == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_AMOUNT_EMPTY);
        }

        orderService.validateOrderSubmit(orderId);

        OrderTemplatesSubmission orderTemplatesSubmission = orderTemplatesSubmissionRepository.findOne(id);
        if(orderTemplatesSubmission == null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_NOT_EXISTS);
        }

        Integer previouslyAmount = orderTemplatesSubmission.getAmount();

        if (!previouslyAmount.equals(amount)){
            orderTemplatesSubmission.setAmount(amount);

            //检测是否在商品限购内
            Products products = orderTemplatesSubmission.getProducts();
            if (products != null) {
                productsService.checkProductsLimit(products, amount);
            }


            orderTemplatesSubmissionRepository.save(orderTemplatesSubmission);
        }
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam add(OrderTemplateAddDto orderTemplateAddDto) {
        if (orderTemplateAddDto.getOrderId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_ID_EMPTY);
        }
        if (orderTemplateAddDto.getProductId() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_ID_EMPTY);
        }

        //查看该商品是否存在
        Products products = productsRepository.findOne(orderTemplateAddDto.getProductId());
        if (products == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_PRODUCTS_NOT_EXISTS);
        }

        //查看所属的订单是否存在
        OrderSubmission orderSubmission = orderSubmissionRepository.findOne(orderTemplateAddDto.getOrderId());
        if (orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }

        //查看该商品是否属于关联的订单模板下
        List<TemplateProducts> templateProductsList= templateProductsRepository.findAllByProductId(products.getId());
        Boolean flag = false;
        if (templateProductsList != null && !templateProductsList.isEmpty()){
            for (TemplateProducts templateProducts: templateProductsList){
                if (templateProducts.getTemplateId().equals(orderSubmission.getTempId())){
                    flag = true;
                    break;
                }
            }
        }
        if (!flag){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATES_PRODUCT_NOT_EXISTS);
        }


        OrderTemplatesSubmission orderTemplatesSubmission = new OrderTemplatesSubmission();
        orderTemplatesSubmission.setAmount(orderTemplateAddDto.getAmount());
        orderTemplatesSubmission.setIsValid(true);
        orderTemplatesSubmission.setOrderId(orderSubmission.getId());
        orderTemplatesSubmission.setPreEdited(0);
        orderTemplatesSubmission.setProductId(products.getId());


        //编号从过去的关联数据中查询统一，没有则生成一个新的编号
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()){
            orderTemplatesSubmission.setCode(orderTemplatesSubmissionList.get(0).getCode());
        }else{
            orderTemplatesSubmission.setCode(OrderUtil.versionGenerate());
        }

        //关联数据更新
        orderTemplatesSubmissionRepository.save(orderTemplatesSubmission);

        return getSuccessOperationResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if (ids==null || ids.isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderTemplatesSubmissionRepository.findAll(ids);
        if (orderTemplatesSubmissionList!=null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission:orderTemplatesSubmissionList){
                OrderSubmission orderSubmission = orderSubmissionRepository.findOne(orderTemplatesSubmission.getOrderId());
                //不是已提交状态模板不能删除
                if (orderSubmission != null &&  !orderSubmission.getStatus().equals(OrderStatus.SUBMITTED.getCode())){
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
                }
            }
            orderTemplatesSubmissionRepository.delete(orderTemplatesSubmissionList);
        }
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIsValid(List<Long> ids,Boolean isValid){
        if (ids==null || ids.isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        //需要更新的OrderTemplatesSubmission
        List<OrderTemplatesSubmission> needUpdateOrderTemplatesSubmissionList = new ArrayList<>();

        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderTemplatesSubmissionRepository.findAll(ids);
        if (orderTemplatesSubmissionList!=null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission:orderTemplatesSubmissionList){
                //先判断状态是否改变，再去查订单状态是否ok
                if (orderTemplatesSubmission.getIsValid().equals(isValid)){
                    continue;
                }
                OrderSubmission orderSubmission = orderSubmissionRepository.findOne(orderTemplatesSubmission.getOrderId());
                if (orderSubmission != null &&  !orderSubmission.getStatus().equals(OrderStatus.SUBMITTED.getCode())){
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
                }
                orderTemplatesSubmission.setIsValid(isValid);
                needUpdateOrderTemplatesSubmissionList.add(orderTemplatesSubmission);
            }
            if(needUpdateOrderTemplatesSubmissionList!=null && !needUpdateOrderTemplatesSubmissionList.isEmpty()){
                orderTemplatesSubmissionRepository.save(needUpdateOrderTemplatesSubmissionList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderProducts(OrderTemplateProductAddDto orderTemplateProductAddDto) {
        List<Long> productIdList = orderTemplateProductAddDto.getProductIdList();
        if (productIdList != null && !productIdList.isEmpty()){
            //查看所属的已提交的订单是否存在
            OrderSubmission orderSubmission=orderService.validateOrderSubmit(orderTemplateProductAddDto.getOrderId());

            List<OrderTemplatesSubmission> orderTemplatesSubmissionList = new ArrayList<>();
            List<Products> productsList = productsRepository.findAll(productIdList);
            if (productsList != null && !productsList.isEmpty()){
                for (Products products: productsList){
                    OrderTemplatesSubmission orderTemplatesSubmission = new OrderTemplatesSubmission();
                    orderTemplatesSubmission.setIsValid(true);
                    orderTemplatesSubmission.setProductId(products.getId());
                    orderTemplatesSubmission.setOrderId(orderSubmission.getId());
                    orderTemplatesSubmission.setAmount(0);
                    orderTemplatesSubmission.setCode(OrderUtil.versionGenerate());
                    orderTemplatesSubmissionList.add(orderTemplatesSubmission);
                }
                orderTemplatesSubmissionRepository.save(orderTemplatesSubmissionList);
            }

        }
    }
}
