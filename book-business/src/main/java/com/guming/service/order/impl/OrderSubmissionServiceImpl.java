package com.guming.service.order.impl;

import com.guming.arrangement.entity.PlansArrangement;
import com.guming.authority.entity.User;
import com.guming.authority.entity.UserDing;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.LogisticsStatus;
import com.guming.common.constants.business.OrderStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.common.utils.OrderUtil;
import com.guming.dao.order.OrderAuditingRepository;
import com.guming.dao.order.OrderDeleteRepository;
import com.guming.dao.order.OrderSubmissionRepository;
import com.guming.dao.order.OrderTemplatesSubmissionRepository;
import com.guming.dao.shops.ShopRepository;
import com.guming.dingtalk.DingTalkService;
import com.guming.dingtalk.request.personmsg.PersonMsgPush;
import com.guming.kingdee.KingdeeService;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.*;
import com.guming.order.vo.OrderAuditingVo;
import com.guming.order.vo.OrderTemplateVo;
import com.guming.order.vo.OrderVo;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.plans.entity.Pathshop;
import com.guming.plans.entity.PlansPath;
import com.guming.products.entity.Products;
import com.guming.service.arrangement.ArrangementService;
import com.guming.service.order.OrderAuditingService;
import com.guming.service.order.OrderSubmissionService;
import com.guming.service.shops.ShopService;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.shops.vo.ShopVo;
import com.guming.tagline.entity.TagLine;
import com.guming.tagwareHouse.entity.TagwareHouse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 购物车和已提交订单
 * @Date: 2018/4/28
 */
@Service
public class OrderSubmissionServiceImpl extends BaseServiceImpl implements OrderSubmissionService {

    @Autowired
    private OrderSubmissionRepository orderSubmissionRepository;

    @Autowired
    private OrderTemplatesSubmissionRepository orderTemplatesSubmissionRepository;

    @Autowired
    private OrderAuditingRepository orderAuditingRepository;

    @Autowired
    private OrderDeleteRepository orderDeleteRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ArrangementService arrangementService;

    @Autowired
    private KingdeeService kingdeeService;

    @Autowired
    private DingTalkService dingTalkService;

    @Override
    protected BaseRepository getRepository() {
        return this.orderSubmissionRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id){
        OrderSubmission orderSubmission = orderSubmissionRepository.findOne(id);
        OrderDelete orderDelete = new OrderDelete();
        BeanUtils.copyProperties(orderSubmission,orderDelete,"id");
        orderDelete.setIsValid(false);

        List<OrderTemplatesSubmission> templatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        List<OrderTemplatesDelete> orderTemplatesDeleteList =  CovertUtil.copyList(templatesSubmissionList,OrderTemplatesDelete.class,"id");
        orderDelete.setOrderTemplatesDeleteList(orderTemplatesDeleteList);
        orderDeleteRepository.save(orderDelete);
        orderSubmissionRepository.delete(orderSubmission);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<InventoryProductResponseParam> checkInverntory(Long id){
        OrderSubmission orderSubmission = orderSubmissionRepository.findByIdAndStatus(id,OrderStatus.SUBMITTED.getCode());
        if(orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }
        return kingdeeService.orderInventory(orderSubmission).getProductList();
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery){
        Page<OrderSubmission> pageResult = findOrderSubmissionByPage(orderQuery,null);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());
        List<OrderSubmission> orderSubmissionList = pageResult.getContent();
        List<OrderVo> result = new ArrayList<OrderVo>();
        if(orderSubmissionList!=null && !orderSubmissionList.isEmpty()){
            for (OrderSubmission orderSubmission:orderSubmissionList){
                OrderVo orderVo = covertOrderSubmissionToOrderVo(orderSubmission);
                if (orderSubmission.getStatus().equals(OrderStatus.SUBMITTED.getCode())){
                    orderVo.setIsRevoke(true);
                }
                result.add(orderVo);
            }
        }
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Page<OrderSubmission> findOrderSubmissionByPage(OrderQuery orderQuery, Long orderId){
        Specification<OrderSubmission> specification = new Specification<OrderSubmission>(){

            @Override
            public Predicate toPredicate(Root<OrderSubmission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                if (orderQuery.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class), orderQuery.getStatus()));
                }
                if (!StringUtils.isEmpty(orderQuery.getCode())){
                    predicates.add(criteriaBuilder.like(root.get("code").as(String.class),"%"+orderQuery.getCode()+"%"));
                }

                if (orderQuery.getTempTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("tempTypeId").as(Integer.class),orderQuery.getTempTypeId()));
                }
                if (orderQuery.getUserId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("makerId").as(Long.class),orderQuery.getUserId()));
                }
                if(orderQuery.getShopId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("shopId").as(Long.class),orderQuery.getShopId()));
                }
                //订单id存在，是为了获取下一条订单id
                if (orderId != null){
                    predicates.add(criteriaBuilder.lessThan(root.get("id").as(Long.class),orderId));
                }

                if (!StringUtils.isEmpty(orderQuery.getShopName())){
                    predicates.add(criteriaBuilder.like(root.join("shopsShop",JoinType.LEFT).get("name"),"%"+orderQuery.getShopName()+"%"));
                }
                if (orderQuery.getTaglineId()==null && orderQuery.getWareHouseId() != null){
                    Join<TagLine,OrderSubmission> join=  root.join("plansPath",JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("tagLine").get("tagWarehouseId"),orderQuery.getWareHouseId()));
                }
                if (orderQuery.getTaglineId()!=null){
                    Join<PlansPath,OrderSubmission> join=  root.join("plansPath",JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("tagLineId"),orderQuery.getTaglineId()));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(criteriaBuilder.desc(root.get("id").as(Long.class))).getRestriction();
            }
        };
        Pageable pageable = new PageRequest(orderQuery.getPage(),orderQuery.getPageSize());
        Page<OrderSubmission> pageResult = orderSubmissionRepository.findAll(specification,pageable);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderSubmission nextOrderSubmission(OrderAuditQuery orderAuditQuery){
        orderAuditQuery.setPage(1);
        orderAuditQuery.setPageSize(1);
        List<OrderSubmission> orderSubmissionList = findOrderSubmissionByPage(orderAuditQuery,orderAuditQuery.getOrderId()).getContent();
        if (orderSubmissionList != null && !orderSubmissionList.isEmpty()){
            return orderSubmissionList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo nextOrder(OrderAuditQuery orderAuditQuery){
        return covertOrderSubmissionToOrderVoAll(nextOrderSubmission(orderAuditQuery));
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<OrderSubmission> findExpireUnAuditOrder() {
        return orderSubmissionRepository.findExpireUnAuditOrder(new Date(),OrderStatus.SUBMITTED.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(List<OrderSubmission> orderSubmissionList){
        if (orderSubmissionList != null && !orderSubmissionList.isEmpty()){
            List<OrderDelete> orderDeleteList = new ArrayList<>();
            for (OrderSubmission orderSubmission : orderSubmissionList){
                OrderDelete orderDelete = new OrderDelete();
                BeanUtils.copyProperties(orderSubmission,orderDelete);
                List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
                List<OrderTemplatesDelete> orderTemplatesDeleteList = CovertUtil.copyList(orderTemplatesSubmissionList,OrderTemplatesDelete.class);
                orderDelete.setOrderTemplatesDeleteList(orderTemplatesDeleteList);
                orderDeleteList.add(orderDelete);
            }
            orderDeleteRepository.save(orderDeleteList);
            orderSubmissionRepository.delete(orderSubmissionList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String orderSubmit(Long orderId, Long sendShopId, User user){
        if (orderId == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_SHOP_ID_EMPTY);
        }

        OrderSubmission orderSubmission = orderSubmissionRepository.findOne(orderId);
        if (orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }
        if (!orderSubmission.getStatus().equals(OrderStatus.UNSUBMITTED.getCode())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_FLOW);
        }
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        if (orderTemplatesSubmissionList==null && orderTemplatesSubmissionList.isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_PRODUCTS_NOT_EXISTS);
        }

        //最终留存订单提交时的时间作为创建时间
        orderSubmission.setCreateTime(new Date());
        orderSubmission.setUpdateTime(new Date());
        orderSubmission.setStatus(OrderStatus.SUBMITTED.getCode());
        //提交后生成正式编号
        orderSubmission.setCode(OrderUtil.orderCodeGenerate());
        orderSubmission.setUser(user);

        //判断收货店铺是否存在
        ShopsShop sendShop = shopRepository.findOne(sendShopId);
        if (sendShop == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_SHOP_NOT_MATCH);
        }
        //判断下单店要与收货店铺属于同一商家同一路线下
        ShopsShop shopsShop = orderSubmission.getShopsShop();
        if(!validateOrderShopAndRecipientShop(user.getId(),shopsShop.getId(),sendShopId)){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_SHOP_NOT_MATCH);
        }
        orderSubmission.setSendShop(sendShop);

        Pathshop pathshop = sendShop.getPathshop();
        if (pathshop == null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OEDER_SEND_SHOP_PATH_NOT_EXISTS);
        }
        orderSubmission.setPlansPath(pathshop.getPlansPath());

        //获取相应的送货安排,设置送货时间
        PlansArrangement plansArrangement = arrangementService.getSendTimePlansArrangement(sendShopId);
        if (plansArrangement == null || plansArrangement.getDay() == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_SEND_TIME_OK);
        }
        orderSubmission.setSendTime(plansArrangement.getDay());
        orderSubmission = orderSubmissionRepository.save(orderSubmission);
        return orderSubmission.getCode();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderAuditingVo orderAudit(OrderAuditQuery orderAuditQuery) {
        //当前的订单
        OrderSubmission orderSubmission = orderSubmissionRepository.findOne(orderAuditQuery.getOrderId());
        if (orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }
        if (!orderSubmission.getStatus().equals(OrderStatus.SUBMITTED.getCode())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_FLOW);
        }

        //获取当前订单的下一条订单
        OrderSubmission nextOrderSubmission = nextOrderSubmission(orderAuditQuery);

        orderSubmission.setUpdateTime(new Date());
        orderSubmission.setStatus(OrderStatus.AUDITED.getCode());

        //订单审核后，需要数据存入审核表
        OrderAuditing orderAuditing = new OrderAuditing();
        BeanUtils.copyProperties(orderSubmission, orderAuditing);

        //已审核之后数据将永久储存
        //所属店铺
        ShopsShop shopsShop = orderSubmission.getShopsShop();
        if (shopsShop != null) {
            orderAuditing.setShopName(shopsShop.getName());
            orderAuditing.setShopCode(shopsShop.getCode());
        }

        //所属模板
        Templates templates = orderSubmission.getTemplates();
        if (templates != null) {
            orderAuditing.setTempName(templates.getName());
        }

        //所属路线安排
        PlansPath plansPath = orderSubmission.getPlansPath();
        if (plansPath != null) {
            TagLine tagLine = plansPath.getTagLine();
            if (tagLine != null) {
                TagwareHouse tagwareHouse = tagLine.getTagwareHouse();
                if (tagwareHouse != null) {
                    orderAuditing.setWarehouseName(tagwareHouse.getName());
                }
                orderAuditing.setLineName(tagLine.getName());
                orderAuditing.setWarehouseId(tagLine.getTagWarehouseId());
                orderAuditing.setLineId(tagLine.getId());
                orderAuditing.setDistributionType(tagLine.getFtype());
            }
        }

        //下单人
        User user = orderSubmission.getUser();
        if (user != null) {
            orderAuditing.setUserName(user.getUserName());
            orderAuditing.setFirstName(user.getFirstName());
            orderAuditing.setPhone(user.getPhone());
        }

        //收货地址信息
        ShopsShop sendShop = orderSubmission.getSendShop();
        if (sendShop != null) {
            orderAuditing.setProvince(sendShop.getProvince());
            orderAuditing.setCity(sendShop.getCity());
            orderAuditing.setDistrict(sendShop.getDistrict());
            orderAuditing.setAddress(sendShop.getAddress());
        }

        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        List<OrderTemplatesAuditing> orderTemplatesAuditingList = new ArrayList<OrderTemplatesAuditing>();
        BigDecimal totalPrice = new BigDecimal(0);
        if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()) {
            for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList) {
                OrderTemplatesAuditing orderTemplatesAuditing = new OrderTemplatesAuditing();
                BeanUtils.copyProperties(orderTemplatesSubmission, orderTemplatesAuditing);
                //下单总价
                Products products = orderTemplatesSubmission.getProducts();
                if (products != null) {
                    if (products.getPrice() != null && orderTemplatesSubmission.getAmount() != null && orderTemplatesSubmission.getIsValid()) {
                        totalPrice = totalPrice.add(products.getPrice().multiply(new BigDecimal(orderTemplatesSubmission.getAmount())));
                    }

                    orderTemplatesAuditing.setProductName(products.getName());
                    orderTemplatesAuditing.setProductPrice(products.getPrice());
                    orderTemplatesAuditing.setSpec(products.getSpec());
                    orderTemplatesAuditing.setStock(products.getStock());
                    orderTemplatesAuditing.setUnit(products.getUnit());
                    orderTemplatesAuditing.setStockUnit(products.getStockUnit());
                }
                orderTemplatesAuditingList.add(orderTemplatesAuditing);
            }
        }
        orderAuditing.setTotalPrice(totalPrice);
        orderAuditing.setOrderTemplatesAuditingList(orderTemplatesAuditingList);

        //金蝶同步订单数据成功后再移植订单数据
        kingdeeService.syncOrder(orderSubmission);

        //审核订单后，数据移动
        orderAuditing = orderAuditingRepository.save(orderAuditing);
        orderSubmissionRepository.delete(orderSubmission.getId());

        //订单审核后钉钉通知
        auditNotify(user,orderAuditing);

        //审核成功后返回订单id和status给前台做刷新展示
        if (nextOrderSubmission != null) {
            OrderAuditingVo orderAuditingVo = new OrderAuditingVo();
            orderAuditingVo.setOrderId(nextOrderSubmission.getId());
            orderAuditingVo.setStatus(nextOrderSubmission.getStatus());
            return orderAuditingVo;
        }
        return null;
    }

    private void auditNotify(User user,OrderAuditing orderAuditing){
        List<UserDing> userDingList = user.getUserDingList();
        String userIdsStr = "";
        if (userDingList != null && !userDingList.isEmpty()){
            for (UserDing userDing : userDingList){
                userIdsStr+=userDing.getDingUser()+",";
            }
            if (!StringUtils.isEmpty(userIdsStr)){
                userIdsStr = userIdsStr.substring(0,userIdsStr.length()-1);

                PersonMsgPush personMsgPush = new PersonMsgPush();

                dingTalkService.userMsgPush(userIdsStr,personMsgPush);
            }

        }

    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        OrderSubmission orderSubmission = orderSubmissionRepository.findOne(id);
        OrderVo orderVo = covertOrderSubmissionToOrderVoAll(orderSubmission);
        return orderVo;
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findByShopIdAndTempId(Long shopId,Long tempId) {
        OrderSubmission orderSubmission = orderSubmissionRepository.findCartByShopIdAndTempId(shopId,tempId);
        if (orderSubmission == null){
            return null;
        }
        OrderVo orderVo = covertOrderSubmissionToOrderVo(orderSubmission);

        PlansPath plansPath = orderSubmission.getPlansPath();
        if (plansPath != null){
            TagLine tagLine = plansPath.getTagLine();
            if (tagLine != null){
                orderVo.setTagLineMapVo(new MapVo(tagLine.getId(),tagLine.getName()));
                orderVo.setShippingMapVo(new MapVo(tagLine.getFtype().longValue(),i18nHandler(LogisticsStatus.getLogisticsStatus(tagLine.getFtype()).getI18N())));
            }
        }

        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        List<OrderTemplateVo> orderTemplateVoList = new ArrayList<OrderTemplateVo>();
        if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission:orderTemplatesSubmissionList ){
                if (orderTemplatesSubmission.getIsValid()) {
                    orderTemplateVoList.add(covertOrderTemplatesSubmissionToOrderTemplateVo(orderTemplatesSubmission));
                }
            }
        }

        orderVo.setOrderTemplateVoList(orderTemplateVoList);
        return orderVo;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findCartByShopIdAndTempId(Long shopId, Long tempId) {
        OrderSubmission orderSubmission = orderSubmissionRepository.findCartByShopIdAndTempId(shopId,tempId);
        List<OrderTemplateVo> orderTemplateVoList = new ArrayList<OrderTemplateVo>();
        OrderVo orderVo = covertOrderSubmissionToOrderVo(orderSubmission);
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        if (orderTemplatesSubmissionList!= null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList){
                orderTemplateVoList.add(covertOrderTemplatesSubmissionToOrderTemplateVo(orderTemplatesSubmission));
            }
        }
        orderVo.setOrderTemplateVoList(orderTemplateVoList);
        return orderVo;
    }


    /**
     * 转换器
     * @param orderSubmission
     * @return
     */
    private OrderVo covertOrderSubmissionToOrderVoAll(OrderSubmission orderSubmission){
        if (orderSubmission != null) {
            OrderVo orderVo = covertOrderSubmissionToOrderVo(orderSubmission);

            PlansPath plansPath = orderSubmission.getPlansPath();
            if (plansPath != null) {
                TagLine tagLine = plansPath.getTagLine();
                if (tagLine != null) {
                    orderVo.setTagLineMapVo(new MapVo(tagLine.getId(), tagLine.getName()));
                    orderVo.setShippingMapVo(new MapVo(tagLine.getFtype().longValue(), i18nHandler(LogisticsStatus.getLogisticsStatus(tagLine.getFtype()).getI18N())));
                    orderVo.setShippingPeople(tagLine.getManager());
                    //仓库
                    TagwareHouse tagwareHouse = tagLine.getTagwareHouse();
                    if (tagwareHouse != null) {
                        orderVo.setTagWarehouseMapVo(new MapVo(tagwareHouse.getId(), tagwareHouse.getName()));
                    }
                }
            }
            List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
            List<OrderTemplateVo> orderTemplateVoList = new ArrayList<>();
            if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()) {
                for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList) {
                    orderTemplateVoList.add(covertOrderTemplatesSubmissionToOrderTemplateVo(orderTemplatesSubmission));
                }
            }
            orderVo.setOrderTemplateVoList(orderTemplateVoList);
            return orderVo;
        }
        return null;
    }

    /**
     * 转换器
     * @param orderTemplatesSubmission
     * @return
     */
    private OrderTemplateVo covertOrderTemplatesSubmissionToOrderTemplateVo(OrderTemplatesSubmission orderTemplatesSubmission){
        OrderTemplateVo orderTemplateVo = new OrderTemplateVo();
        BeanUtils.copyProperties(orderTemplatesSubmission,orderTemplateVo);
        Products products = orderTemplatesSubmission.getProducts();
        orderTemplateVo.setProductName(products.getName());
        orderTemplateVo.setProductPrice(products.getPrice());
        orderTemplateVo.setSpec(products.getSpec());
        orderTemplateVo.setUnit(products.getUnit());
        orderTemplateVo.setStep(products.getStep());
        orderTemplateVo.setStockUnit(products.getStockUnit());
        orderTemplateVo.setStock(products.getStock());
        return orderTemplateVo;
    }

    /**
     * 转换器
     * @param orderSubmission
     * @return
     */
    private OrderVo covertOrderSubmissionToOrderVo(OrderSubmission orderSubmission){
        OrderVo orderVo = null;
        if (orderSubmission != null) {
            orderVo = new OrderVo();
            BeanUtils.copyProperties(orderSubmission, orderVo);

            //所属商店
            ShopsShop shopsShop = orderSubmission.getShopsShop();
            if (shopsShop != null) {
                orderVo.setShopName(shopsShop.getName());
            }

            //收货商店
            ShopsShop sendShop = orderSubmission.getSendShop();
            if (sendShop != null){
                orderVo.setAddress(sendShop.getAddress());
            }

            Integer status = orderSubmission.getStatus();
            //订单状态
            orderVo.setStatusMapVo(new MapVo(orderSubmission.getStatus().longValue(), i18nHandler(OrderStatus.getOrderStatus(status).getI18N())));
            if (status.equals(OrderStatus.SUBMITTED.getCode())){
                orderVo.setIsRevoke(true);
            }


            //所属模板名
            Templates templates = orderSubmission.getTemplates();
            if (templates != null) {
                orderVo.setTempName(templates.getName());
            }

            //模板类型
            TemplatesType templatesType = orderSubmission.getTemplatesType();
            if (templatesType != null) {
                orderVo.setTempTypeMapVo(new MapVo(templatesType.getId(), templatesType.getName()));
            }

            //总价
            BigDecimal totalPrice = new BigDecimal(0);
            List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
            if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()) {
                for (OrderTemplatesSubmission orderTemplatesSubmission : orderTemplatesSubmissionList) {
                    Products products = orderTemplatesSubmission.getProducts();
                    if (products != null && orderTemplatesSubmission.getAmount() != null
                            && products.getPrice() != null && products.getIsValid() && orderTemplatesSubmission.getIsValid()) {
                        totalPrice = totalPrice.add(products.getPrice().multiply(new BigDecimal(orderTemplatesSubmission.getAmount())));
                    }
                }
            }
            orderVo.setTotalPrice(totalPrice);
        }
        return orderVo;
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findShopTemplateOrdersInSendTime(Long shopId,Long tempId,Date sendTime,Integer status){
        OrderSubmission orderSubmission = orderSubmissionRepository.findByShopIdAndTempIdAndStatus(shopId,tempId,sendTime,status);
        return covertOrderSubmissionToOrderVo(orderSubmission);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findByTempIdAndStatus(Long tempId, Integer status) {
        OrderSubmission orderSubmission = orderSubmissionRepository.findByIdAndStatus(tempId,OrderStatus.UNSUBMITTED.getCode());
        return covertOrderSubmissionToOrderVo(orderSubmission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revokeOrder(Long id) {
        OrderSubmission orderSubmission = orderSubmissionRepository.findByIdAndStatus(id,OrderStatus.SUBMITTED.getCode());
        if (orderSubmission == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_REVOKE_ERROR);
        }
        //撤回订单时，删除管理端标记失效的数据
        orderTemplatesSubmissionRepository.deleteByOrderIdAndIsValid(orderSubmission.getId(),false);
        orderSubmission.setStatus(OrderStatus.UNSUBMITTED.getCode());
        List<OrderTemplatesSubmission> orderTemplatesSubmissionList = orderSubmission.getOrderTemplatesSubmissionList();
        List<OrderTemplatesSubmission> zeroAmountTemplatesProducts = new ArrayList<>();
        if(orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()){
            for (OrderTemplatesSubmission orderTemplatesSubmission:orderTemplatesSubmissionList){
                if (!orderTemplatesSubmission.getIsValid() || orderTemplatesSubmission.getAmount()<=0){
                    zeroAmountTemplatesProducts.add(orderTemplatesSubmission);
                }
            }
        }
        orderTemplatesSubmissionList.removeAll(zeroAmountTemplatesProducts);
        orderSubmissionRepository.save(orderSubmission);
    }

    /**
     * 验证收货店铺是否和下单店铺同属于同一用户，同一路线
     * @param userId                下单人id
     * @param shopId                下单店铺id
     * @param recipientShopId       收货店铺id
     * @return
     */
    private Boolean validateOrderShopAndRecipientShop(Long userId,Long shopId,Long recipientShopId){
        List<ShopVo> shopVoList = shopService.getRecipientShopMapVoList(userId,shopId);
        if (shopVoList != null && !shopVoList.isEmpty()) {
            for (ShopVo shopVo : shopVoList) {
                if (shopVo.getId().equals(recipientShopId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
