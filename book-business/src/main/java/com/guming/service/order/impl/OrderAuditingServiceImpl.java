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
import com.guming.common.utils.CovertUtil;
import com.guming.dao.order.OrderAuditingRepository;
import com.guming.dao.order.OrderFinishRepository;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderAuditing;
import com.guming.order.entity.OrderFinish;
import com.guming.order.entity.OrderTemplatesAuditing;
import com.guming.order.entity.OrderTemplatesFinish;
import com.guming.order.vo.OrderTemplateVo;
import com.guming.order.vo.OrderVo;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.service.order.OrderAuditingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/28
 */
@Service
public class OrderAuditingServiceImpl extends BaseServiceImpl implements OrderAuditingService {

    @Autowired
    private OrderAuditingRepository orderAuditingRepository;

    @Autowired
    private OrderFinishRepository orderFinishRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.orderAuditingRepository;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery){
        Page<OrderAuditing> pageResult = findByPage(orderQuery,null);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());

        List<OrderAuditing> orderAuditingList = pageResult.getContent();
        List<OrderVo> result = new ArrayList<OrderVo>();
        if(orderAuditingList!=null && !orderAuditingList.isEmpty()){
            for (OrderAuditing orderAuditing:orderAuditingList){
                result.add(covertOrderAuditingToOrderVo(orderAuditing));
            }
        }
        return ResponseParam.success(result,pagination);
    }

    public Page<OrderAuditing> findByPage(OrderQuery orderQuery, Long orderId){
        Specification<OrderAuditing> specification = new Specification<OrderAuditing>(){

            @Override
            public Predicate toPredicate(Root<OrderAuditing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));
                predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class),orderQuery.getStatus()));

                if (!StringUtils.isEmpty(orderQuery.getCode())){
                    predicates.add(criteriaBuilder.equal(root.get("code").as(String.class),orderQuery.getCode().trim()));
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
                if (!StringUtils.isEmpty(orderQuery.getShopName())){
                    predicates.add(criteriaBuilder.like(root.get("shopName"),"%"+orderQuery.getShopName()+"%"));
                }
                if (orderQuery.getTaglineId()==null && orderQuery.getWareHouseId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("warehouseId"),orderQuery.getWareHouseId()));
                }
                if (orderQuery.getTaglineId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("lineId"),orderQuery.getTaglineId()));
                }

                //orderId 是为了取顶值使用
                if (orderId != null){
                    predicates.add(criteriaBuilder.lessThan(root.get("id").as(Long.class),orderId));
                }

                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(criteriaBuilder.desc(root.get("id").as(Long.class))).getRestriction();
            }
        };

        Pageable pageable = new PageRequest(orderQuery.getPage(),orderQuery.getPageSize());
        Page<OrderAuditing> pageResult = orderAuditingRepository.findAll(specification,pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        OrderAuditing orderAuditing = orderAuditingRepository.findOne(id);
        return covertOrderAuditingToOrderVoForAll(orderAuditing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderDelivery(Long id) {
        OrderAuditing orderAuditing =  orderAuditingRepository.findOne(id);
        if (orderAuditing == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }
        if (!orderAuditing.getStatus().equals(OrderStatus.AUDITED.getCode())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_FLOW);
        }
        orderAuditing.setUpdateTime(new Date());
        orderAuditing.setStatus(OrderStatus.DELIVERED.getCode());
        orderAuditingRepository.save(orderAuditing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderFinish(Long id){
        OrderAuditing orderAuditing =  orderAuditingRepository.findOne(id);
        if (orderAuditing == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_NOT_EXISTS);
        }
        if (!orderAuditing.getStatus().equals(OrderStatus.DELIVERED.getCode())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ORDER_STATUS_FLOW);
        }
        orderAuditing.setUpdateTime(new Date());
        orderAuditing.setStatus(OrderStatus.COMPLETE.getCode());

        //订单完成后，需要删除当前表中的数据，并将数据移植到完成表中
        //订单下商品的下单数据
        List<OrderTemplatesAuditing> orderTemplatesAuditingList = orderAuditing.getOrderTemplatesAuditingList();
        OrderFinish orderFinish = new OrderFinish();
        BeanUtils.copyProperties(orderAuditing,orderFinish,"id");
        List<OrderTemplatesFinish> orderTemplatesFinishList = CovertUtil.copyList(orderTemplatesAuditingList,OrderTemplatesFinish.class,"id");
        orderFinish.setOrderTemplatesFinishList(orderTemplatesFinishList);
        orderFinishRepository.save(orderFinish);
        orderAuditingRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findShopTemplateOrdersInSendTime(Long shopId, Long tempId, Date sendTime, Integer status) {
        OrderAuditing orderAuditing = orderAuditingRepository.findByShopIdAndTempIdAndSendTimeAndStatus(shopId,tempId,sendTime,status);
        return covertOrderAuditingToOrderVo(orderAuditing);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo nextOrder(OrderAuditQuery orderAuditQuery) {
        return covertOrderAuditingToOrderVoForAll(nextOrderAuditing(orderAuditQuery));
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderAuditing nextOrderAuditing(OrderAuditQuery orderAuditQuery) {
        orderAuditQuery.setPage(0);
        orderAuditQuery.setPageSize(1);
        List<OrderAuditing> orderAuditingList = findByPage(orderAuditQuery,orderAuditQuery.getOrderId()).getContent();
        if (orderAuditingList != null && !orderAuditingList.isEmpty()){
            return orderAuditingList.get(0);
        }
        return null;
    }

    /**
     * 统一使用的bean转换器
     * @param orderAuditing
     * @return
     */
    private OrderVo covertOrderAuditingToOrderVo(OrderAuditing orderAuditing){
        OrderVo orderVo = null;
        if (orderAuditing != null) {
            orderVo = new OrderVo();
            BeanUtils.copyProperties(orderAuditing, orderVo);

            //订单状态
            orderVo.setStatusMapVo(new MapVo(orderAuditing.getStatus().longValue(), i18nHandler(OrderStatus.getOrderStatus(orderAuditing.getStatus()).getI18N())));

            //模板类型
            TemplatesType templatesType = orderAuditing.getTemplatesType();
            orderVo.setTempTypeMapVo(new MapVo(templatesType.getId(), templatesType.getName()));
        }
        return orderVo;
    }

    /**
     * @param orderAuditing
     * @return
     */
    private OrderVo covertOrderAuditingToOrderVoForAll(OrderAuditing orderAuditing){
        OrderVo orderVo = covertOrderAuditingToOrderVo(orderAuditing);

        orderVo.setTagLineMapVo(new MapVo(orderAuditing.getLineId(),orderAuditing.getLineName()));
        orderVo.setTagWarehouseMapVo(new MapVo(orderAuditing.getWarehouseId(),orderAuditing.getWarehouseName()));
        if (orderAuditing.getDistributionType() != null) {
            orderVo.setShippingMapVo(new MapVo(orderAuditing.getDistributionType().longValue(), i18nHandler(LogisticsStatus.getLogisticsStatus(orderAuditing.getDistributionType()).getI18N())));
        }

        //将商品数据塞入
        List<OrderTemplatesAuditing> orderTemplatesAuditingList = orderAuditing.getOrderTemplatesAuditingList();
        orderVo.setOrderTemplateVoList(CovertUtil.copyList(orderTemplatesAuditingList,OrderTemplateVo.class));
        return orderVo;
    }

}
