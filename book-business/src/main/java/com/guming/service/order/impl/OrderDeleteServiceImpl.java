package com.guming.service.order.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.business.LogisticsStatus;
import com.guming.common.constants.business.OrderStatus;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.dao.order.OrderDeleteRepository;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderDelete;
import com.guming.order.entity.OrderSubmission;
import com.guming.order.entity.OrderTemplatesDelete;
import com.guming.order.entity.OrderTemplatesSubmission;
import com.guming.order.vo.OrderTemplateVo;
import com.guming.order.vo.OrderVo;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.plans.entity.PlansPath;
import com.guming.products.entity.Products;
import com.guming.service.order.OrderDeleteService;
import com.guming.shops.entitiy.ShopsShop;
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
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/4
 */
@Service
public class OrderDeleteServiceImpl extends BaseServiceImpl implements OrderDeleteService {

    @Autowired
    private OrderDeleteRepository orderDeleteRepository;


    @Override
    protected BaseRepository getRepository() {
        return this.orderDeleteRepository;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery){
        Page<OrderDelete> pageResult = findOrderDeleteByPage(orderQuery, null);
        Pagination pagination = new Pagination(pageResult.getTotalElements(), pageResult.getNumber(), pageResult.getSize());
        List<OrderDelete> orderDeleteList = pageResult.getContent();
        List<OrderVo> result = new ArrayList<OrderVo>();
        if (orderDeleteList != null && !orderDeleteList.isEmpty()) {
            for (OrderDelete orderDelete : orderDeleteList) {
                OrderVo orderVo = covertOrderDeleteToOrderVo(orderDelete);
                result.add(orderVo);
            }
        }
        return ResponseParam.success(result, pagination);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Page<OrderDelete> findOrderDeleteByPage(OrderQuery orderQuery, Long orderId){
        Specification<OrderDelete> specification = new Specification<OrderDelete>() {
            @Override
            public Predicate toPredicate(Root<OrderDelete> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class),OrderStatus.SUBMITTED.getCode()));
                if (!StringUtils.isEmpty(orderQuery.getCode())){
                    predicates.add(criteriaBuilder.equal(root.get("code").as(String.class),orderQuery.getCode().trim()));
                }
                if (orderQuery.getTempTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("tempTypeId").as(Integer.class), orderQuery.getTempTypeId()));
                }
                if (orderQuery.getUserId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("makerId").as(Long.class), orderQuery.getUserId()));
                }
                if (orderQuery.getShopId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("shopId").as(Long.class), orderQuery.getShopId()));
                }
                //订单id存在，是为了获取下一条订单id
                if (orderId != null) {
                    predicates.add(criteriaBuilder.lessThan(root.get("id").as(Long.class), orderId));
                }
                if (!StringUtils.isEmpty(orderQuery.getShopName())) {
                    predicates.add(criteriaBuilder.like(root.join("shopsShop", JoinType.LEFT).get("name"), "%" + orderQuery.getShopName() + "%"));
                }
                if (orderQuery.getTaglineId() == null && orderQuery.getWareHouseId() != null) {
                    Join<TagLine, OrderSubmission> join = root.join("plansPath", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("tagLine").get("tagWarehouseId"), orderQuery.getWareHouseId()));
                }
                if (orderQuery.getTaglineId() != null) {
                    Join<PlansPath, OrderSubmission> join = root.join("plansPath", JoinType.LEFT);
                    predicates.add(criteriaBuilder.equal(join.get("tagLineId"), orderQuery.getTaglineId()));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(criteriaBuilder.desc(root.get("id").as(Long.class))).getRestriction();
            }
        };
        Pageable pageable = new PageRequest(orderQuery.getPage(), orderQuery.getPageSize());
        Page<OrderDelete> pageResult = orderDeleteRepository.findAll(specification, pageable);
        return pageResult;
    }


    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public OrderVo findById(Long id) {
        if (id == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        OrderDelete orderDelete = orderDeleteRepository.findOne(id);
        OrderVo orderVo = covertOrderDeleteToOrderVoAll(orderDelete);
        return orderVo;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo nextOrder(OrderAuditQuery orderAuditQuery) {
        return covertOrderDeleteToOrderVoAll(nextOrderDelete(orderAuditQuery));
    }

    private OrderDelete nextOrderDelete(OrderAuditQuery orderAuditQuery) {
        orderAuditQuery.setPage(1);
        orderAuditQuery.setPageSize(1);
        List<OrderDelete> orderDeleteList = findOrderDeleteByPage(orderAuditQuery, orderAuditQuery.getOrderId()).getContent();
        if (orderDeleteList != null && !orderDeleteList.isEmpty()) {
            return orderDeleteList.get(0);
        }
        return null;
    }

    private OrderVo covertOrderDeleteToOrderVoAll(OrderDelete orderDelete) {
        if (orderDelete != null) {
            OrderVo orderVo = covertOrderDeleteToOrderVo(orderDelete);

            PlansPath plansPath = orderDelete.getPlansPath();
            if (plansPath != null) {
                TagLine tagLine = plansPath.getTagLine();
                if (tagLine != null) {
                    orderVo.setTagLineMapVo(new MapVo(tagLine.getId(), tagLine.getName()));
                    orderVo.setShippingMapVo(new MapVo(tagLine.getFtype().longValue(), i18nHandler(LogisticsStatus.getLogisticsStatus(tagLine.getFtype()).getI18N())));
                    orderVo.setDistributionPhone(tagLine.getPhone());
                    orderVo.setShippingPeople(tagLine.getManager());

                    //仓库
                    TagwareHouse tagwareHouse = tagLine.getTagwareHouse();
                    if (tagwareHouse != null) {
                        orderVo.setTagWarehouseMapVo(new MapVo(tagwareHouse.getId(), tagwareHouse.getName()));
                    }
                }
            }
            List<OrderTemplatesDelete> orderTemplatesDeleteList = orderDelete.getOrderTemplatesDeleteList();
            List<OrderTemplateVo> orderTemplateVoList = new ArrayList<>();
            if (orderTemplatesDeleteList != null && !orderTemplatesDeleteList.isEmpty()) {
                for (OrderTemplatesDelete orderTemplatesDelete : orderTemplatesDeleteList) {
                    orderTemplateVoList.add(covertOrderTemplatesDeleteToOrderTemplateVo(orderTemplatesDelete));
                }
            }
            orderVo.setOrderTemplateVoList(orderTemplateVoList);
            return orderVo;
        }
        return null;
    }

    private OrderTemplateVo covertOrderTemplatesDeleteToOrderTemplateVo(OrderTemplatesDelete orderTemplatesDelete) {
        OrderTemplateVo orderTemplateVo = new OrderTemplateVo();
        BeanUtils.copyProperties(orderTemplatesDelete, orderTemplateVo);
        Products products = orderTemplatesDelete.getProducts();
        orderTemplateVo.setProductName(products.getName());
        orderTemplateVo.setProductPrice(products.getPrice());
        orderTemplateVo.setSpec(products.getSpec());
        orderTemplateVo.setUnit(products.getUnit());
        orderTemplateVo.setStep(products.getStep());
        orderTemplateVo.setStockUnit(products.getStockUnit());
        orderTemplateVo.setStock(products.getStock());
        return orderTemplateVo;
    }

    private OrderVo covertOrderDeleteToOrderVo(OrderDelete orderDelete) {
        OrderVo orderVo = null;
        if (orderDelete != null) {
            orderVo = new OrderVo();
            BeanUtils.copyProperties(orderDelete, orderVo);

            //所属商店
            ShopsShop shopsShop = orderDelete.getShopsShop();
            if (shopsShop != null) {
                orderVo.setShopName(shopsShop.getName());
            }

            //收货商店
            ShopsShop sendShop = orderDelete.getSendShop();
            if (sendShop != null) {
                orderVo.setAddress(sendShop.getAddress());
            }

            //订单状态
            orderVo.setStatusMapVo(new MapVo(OrderStatus.CLOSED.getCode().longValue(), i18nHandler(OrderStatus.CLOSED.getI18N())));


            //所属模板名
            Templates templates = orderDelete.getTemplates();
            if (templates != null) {
                orderVo.setTempName(templates.getName());
            }

            //模板类型
            TemplatesType templatesType = orderDelete.getTemplatesType();
            if (templatesType != null) {
                orderVo.setTempTypeMapVo(new MapVo(templatesType.getId(), templatesType.getName()));
            }

            //总价
            BigDecimal totalPrice = new BigDecimal(0);
            List<OrderTemplatesDelete> orderTemplatesSubmissionList = orderDelete.getOrderTemplatesDeleteList();
            if (orderTemplatesSubmissionList != null && !orderTemplatesSubmissionList.isEmpty()) {
                for (OrderTemplatesDelete orderTemplatesDelete : orderTemplatesSubmissionList) {
                    Products products = orderTemplatesDelete.getProducts();
                    if (products != null && orderTemplatesDelete.getAmount() != null
                            && products.getPrice() != null && products.getIsValid() && orderTemplatesDelete.getIsValid()) {
                        totalPrice = totalPrice.add(products.getPrice().multiply(new BigDecimal(orderTemplatesDelete.getAmount())));
                    }
                }
            }
            orderVo.setTotalPrice(totalPrice);
        }
        return orderVo;
    }
}
