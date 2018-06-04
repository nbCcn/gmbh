package com.guming.service.order.impl;

import com.guming.arrangement.entity.PlansArrangement;
import com.guming.authority.entity.User;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.OrderStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.OrderUtil;
import com.guming.dao.order.OrderSubmissionRepository;
import com.guming.dao.orderTemplate.TemplatesRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dingtalk.DingTalkService;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.order.dto.OrderDto;
import com.guming.order.dto.RecopientDto;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderDelete;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.order.vo.OrderAuditingVo;
import com.guming.order.vo.OrderQueryVo;
import com.guming.order.vo.OrderVo;
import com.guming.orderTemplate.entity.TemplateProducts;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.products.entity.ProductsStatus;
import com.guming.service.arrangement.ArrangementService;
import com.guming.service.order.*;
import com.guming.service.shops.ShopService;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.shops.vo.ShopVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    @Autowired
    private OrderAuditingService orderAuditingService;

    @Autowired
    private OrderFinishService orderFinishService;

    @Autowired
    private OrderSubmissionService orderSubmissionService;

    @Autowired
    private OrderDeleteService orderDeleteService;

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @Autowired
    private ArrangementService arrangementService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private ShopRepository shopRepository;

        @Override
        protected BaseRepository getRepository() {
            return null;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery) {
            Integer status = orderQuery.getStatus();
        if (status == null) {
            OrderQuery tmp =new OrderQuery();
            BeanUtils.copyProperties(orderQuery,tmp);
            tmp.setPageSize(Integer.MAX_VALUE);
            tmp.setPage(1);

            List<OrderVo> orderVoList = new ArrayList<>();
            tmp.setStatus(OrderStatus.SUBMITTED.getCode());
            orderVoList.addAll(orderSubmissionService.findByPage(tmp).getResult());

            tmp.setStatus(null);
            orderVoList.addAll(orderAuditingService.findByPage(tmp).getResult());
            orderVoList.addAll(orderFinishService.findByPage(tmp).getResult());

            orderVoList.sort(new Comparator<OrderVo>() {
                @Override
                public int compare(OrderVo o1, OrderVo o2) {
                    if (o1.getCreateTime().getTime()<o2.getCreateTime().getTime()){
                        return 1;
                    }
                    return -1;
                }
            });

            Pagination pagination = new Pagination((long) orderVoList.size(),orderQuery.getPage(),orderQuery.getPageSize());
            Integer maxIndex = (orderQuery.getPage()+1)*orderQuery.getPageSize();
            if(orderVoList.size() < maxIndex){
                maxIndex = orderVoList.size();
            }
            if (!orderVoList.isEmpty()) {
                orderVoList = orderVoList.subList(orderQuery.getPage() * orderQuery.getPageSize(), maxIndex);
            }
            return ResponseParam.success(orderVoList,pagination);
        }else if (status.equals(OrderStatus.UNSUBMITTED.getCode()) || status.equals(OrderStatus.SUBMITTED.getCode())){
            return orderSubmissionService.findByPage(orderQuery);
        }else if (status.equals(OrderStatus.AUDITED.getCode()) || status.equals(OrderStatus.DELIVERED.getCode())){
            return orderAuditingService.findByPage(orderQuery);
        }else if (status.equals(OrderStatus.COMPLETE.getCode())){
            return orderFinishService.findByPage(orderQuery);
        }else if (status.equals(OrderStatus.CLOSED.getCode())){
            return orderDeleteService.findByPage(orderQuery);
        }
        throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
    }

    @Override
    public OrderQueryVo findByIdWithQuery(OrderAuditQuery orderAuditQuery){
        OrderQueryVo orderQueryVo = new OrderQueryVo();
        orderQueryVo.setOrderQuery(orderAuditQuery);
        orderQueryVo.setOrderVo(findById(orderAuditQuery.getOrderId(),orderAuditQuery.getStatus()));
        return orderQueryVo;
    }

    @Override
    public OrderVo findById(Long orderId,Integer status) {
        if (status.equals(OrderStatus.UNSUBMITTED.getCode()) || status.equals(OrderStatus.SUBMITTED.getCode())){
            return orderSubmissionService.findById(orderId);
        }else if (status.equals(OrderStatus.AUDITED.getCode()) || status.equals(OrderStatus.DELIVERED.getCode())){
            return orderAuditingService.findById(orderId);
        }else if (status.equals(OrderStatus.COMPLETE.getCode())){
            return orderFinishService.findById(orderId);
        }else if (status.equals(OrderStatus.CLOSED.getCode())){
            return orderDeleteService.findById(orderId);
        }else {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
        }
    }

    @Override
    public ResponseParam<List<MapVo>> getOrderStatusList() {
        List<MapVo> mapVoList = new ArrayList<>();
        for (OrderStatus orderStatus:OrderStatus.values()){
            if(orderStatus != OrderStatus.UNSUBMITTED) {
                mapVoList.add(new MapVo(orderStatus.getCode().longValue(), i18nHandler(orderStatus.getI18N())));
            }
        }
        return ResponseParam.success(mapVoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<Long> checkCart(OrderDto orderDto) {
        //由于收货店铺和下单店铺在同一路线下，在未提交时只需校验下单店铺是否在可订单时间范围即可
        PlansArrangement plansArrangement = arrangementService.getSendTimePlansArrangement(orderDto.getShopId());
        if (plansArrangement == null || plansArrangement.getDay() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_TIME_OK);
        }

        //先判断原来购物车是否存在，不存在根据模板创建一个购物车
        OrderVo orderVo = orderSubmissionService.findByShopIdAndTempId(orderDto.getShopId(),orderDto.getTempId());
        if (orderVo == null) {
            //判断该发货时间内是否有已提交之后的订单(同一店铺同一模板下同一发货时间只会有一组数据，因此只需在此处判断)
            orderVo = findShopTemplateOrdersInSendTime(orderDto.getShopId(), orderDto.getTempId(), plansArrangement.getDay());
            if (orderVo != null) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SENDTIMEDUR_EXISTS);
            }

            //创建一个新的购物车
            OrderSubmission orderSubmission = generateOrderCart(orderDto.getShopId(),orderDto.getTempId());
            return ResponseParam.success(orderSubmission.getId());
        }
        return ResponseParam.success(orderVo.getId());
    }

    /**
     * 检测店铺某个订单下的某个送货时间的订单
     * @param shopId        店铺id
     * @param tempId        模板id
     * @param sendTime      送货时间
     * @return      是否有订单
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findShopTemplateOrdersInSendTime(Long shopId,Long tempId,Date sendTime){
        OrderVo orderVo = null;
        orderVo = orderSubmissionService.findShopTemplateOrdersInSendTime(shopId,tempId,sendTime,OrderStatus.SUBMITTED.getCode());
        if (orderVo ==null){
            orderVo = orderAuditingService.findShopTemplateOrdersInSendTime(shopId,tempId,sendTime,OrderStatus.AUDITED.getCode());
            if (orderVo ==null){
                orderVo = orderAuditingService.findShopTemplateOrdersInSendTime(shopId,tempId,sendTime,OrderStatus.DELIVERED.getCode());
                if (orderVo == null){
                    orderVo = orderFinishService.findShopTemplateOrdersInSendTime(shopId,tempId,sendTime,OrderStatus.COMPLETE.getCode());
                }
            }
        }
        return orderVo;
    }

    @Override
    public ResponseParam<List<InventoryProductResponseParam>> checkStock(Long id) {
        return ResponseParam.success(orderSubmissionService.checkInverntory(id));
    }

    /**
     *  生成订单购物车
     * @return
     */
    private OrderSubmission generateOrderCart(Long shopId,Long tempId) {
        OrderSubmission orderSubmission = new OrderSubmission();
        orderSubmission.setStatus(OrderStatus.UNSUBMITTED.getCode());
        orderSubmission.setIsValid(true);
        orderSubmission.setCreateTime(new Date());
        orderSubmission.setUpdateTime(new Date());
        orderSubmission.setCode(OrderUtil.orderCodeGenerate());

        ShopsShop shopsShop = shopRepository.findOne(shopId);
        Templates templates = templatesRepository.findByIdAndIsActive(tempId,true);
        if (shopsShop == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SHOP_NOT_EXISTS);
        }
        if (templates == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_TEMPLATE_NOT_EXISTS);
        }

        orderSubmission.setShopsShop(shopsShop);
        orderSubmission.setTemplates(templates);
        orderSubmission.setTemplatesType(templates.getTemplatesType());

        TemplatesType templatesType = templates.getTemplatesType();
        if (templatesType == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATE_TYPE_NOT_EXISTS);
        }

        //该模板下的商品数据
        List<TemplateProducts> templateProductsList = templates.getTemplateProductsList();
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = new ArrayList<>();
        if (templateProductsList != null && !templateProductsList.isEmpty()){
            //店铺状态
            Integer shopStatus = shopsShop.getStatus();
            for (TemplateProducts templateProducts:templateProductsList){
                List<ProductsStatus> productsStatusList =templateProducts.getProducts().getProductsStatusList();
                if (productsStatusList != null && !productsStatusList.isEmpty()){
                    for (ProductsStatus productsStatus:productsStatusList){
                        if (productsStatus.getShopStatus().equals(shopStatus) && templateProducts.getProductCount()>0){
                            OrderTemplatesSubmission orderTemplatesSubmission = new OrderTemplatesSubmission();
                            orderTemplatesSubmission.setCode(OrderUtil.versionGenerate());
                            orderTemplatesSubmission.setProductId(templateProducts.getProductId());
                            orderTemplatesSubmission.setAmount(templateProducts.getProductCount());
                            orderTemplatesSubmission.setIsValid(true);
                            orderTemplatesSubmissionList.add(orderTemplatesSubmission);
                        }
                    }
                }
            }
        }

        orderSubmission.setOrderTemplatesSubmissionList(orderTemplatesSubmissionList);
        orderSubmission =orderSubmissionRepository.save(orderSubmission);
        return orderSubmission;
    }


    @Override
    public ResponseParam<List<ShopVo>> getRecipientShop(RecopientDto recopientDto) {
        return ResponseParam.success(shopService.getRecipientShopMapVoList(recopientDto.getUserId(),recopientDto.getShopId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> orderSubmit(Long orderId, Long sendShopId, User user) {
        //判断收货店的下单时间是否在时间范围
        PlansArrangement plansArrangement = arrangementService.getSendTimePlansArrangement(sendShopId);
        if (plansArrangement == null || plansArrangement.getDay() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_TIME_OK);
        }
        String code = orderSubmissionService.orderSubmit(orderId,sendShopId,user);
        return ResponseParam.success(code);
    }

    @Override
    public ResponseParam<OrderAuditingVo> orderAudit(OrderAuditQuery orderAuditQuery) {
        OrderAuditingVo orderAuditingVo = orderSubmissionService.orderAudit(orderAuditQuery);
        return ResponseParam.success(orderAuditingVo);
    }

    @Override
    public ResponseParam orderDelivery(Long id) {
        orderAuditingService.orderDelivery(id);
        return getSuccessOperationResult();
    }

    @Override
    public ResponseParam orderFinish(Long id) {
        orderAuditingService.orderFinish(id);
        return getSuccessOperationResult();
    }

    @Override
    public ResponseParam revokeOrder(Long id) {
        orderSubmissionService.revokeOrder(id);
        return getSuccessOperationResult();
    }

    @Override
    public List<MapVo> getOrderStatusMapVoList(){
        List<MapVo> mapVoList = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()){
            if(orderStatus.equals(OrderStatus.UNSUBMITTED)){
                continue;
            }
            mapVoList.add(new MapVo(orderStatus.getCode().longValue(),i18nHandler(orderStatus.getI18N())));
        }
        return mapVoList;
    }

    @Override
    public OrderVo nextOrder(OrderAuditQuery orderAuditQuery) {
        Integer status = orderAuditQuery.getStatus();
        if (status == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
        }
        if (status.equals(OrderStatus.UNSUBMITTED.getCode()) || status.equals(OrderStatus.SUBMITTED.getCode())){
            return orderSubmissionService.nextOrder(orderAuditQuery);
        }else if (status.equals(OrderStatus.AUDITED.getCode()) || status.equals(OrderStatus.DELIVERED.getCode())){
            return orderAuditingService.nextOrder(orderAuditQuery);
        }else if (status.equals(OrderStatus.COMPLETE.getCode())){
            return orderFinishService.nextOrder(orderAuditQuery);
        }else if (status.equals(OrderStatus.CLOSED.getCode())){
            return orderDeleteService.nextOrder(orderAuditQuery);
        }else {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_ERROR);
        }
    }

    @Override
    public OrderSubmission validateOrderSubmit(Long orderId){
       OrderSubmission orderSubmission = orderSubmissionRepository.findByIdAndStatus(orderId,OrderStatus.SUBMITTED.getCode());
       if (orderSubmission == null){
           throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
       }
       return orderSubmission;
    }
}

