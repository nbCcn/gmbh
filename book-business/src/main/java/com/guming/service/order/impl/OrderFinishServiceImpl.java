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
import com.guming.dao.order.OrderFinishRepository;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.entity.OrderFinish;
import com.guming.order.entity.OrderTemplatesFinish;
import com.guming.order.vo.OrderTemplateVo;
import com.guming.order.vo.OrderVo;
import com.guming.service.order.OrderFinishService;
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
public class OrderFinishServiceImpl extends BaseServiceImpl implements OrderFinishService {

    @Autowired
    private OrderFinishRepository orderFinishRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.orderFinishRepository;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<List<OrderVo>> findByPage(OrderQuery orderQuery){
        Page<OrderFinish> pageResult = findByPage(orderQuery,null);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());

        List<OrderFinish> orderFinishList = pageResult.getContent();
        List<OrderVo> result = new ArrayList<OrderVo>();
        if(orderFinishList!=null && !orderFinishList.isEmpty()){
            for (OrderFinish orderFinish:orderFinishList){
                result.add(covertOrderFinishToOrderVo(orderFinish));
            }
        }
        return ResponseParam.success(result,pagination);
    }

    public Page<OrderFinish> findByPage(OrderQuery orderQuery, Long orderId){
        Specification<OrderFinish> specification = new Specification<OrderFinish>(){

            @Override
            public Predicate toPredicate(Root<OrderFinish> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                predicates.add(criteriaBuilder.equal(root.get("isValid").as(Boolean.class),true));

                if (orderQuery.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class), orderQuery.getStatus()));
                }

                if (!StringUtils.isEmpty(orderQuery.getCode())){
                    predicates.add(criteriaBuilder.equal(root.get("code").as(String.class),orderQuery.getCode().trim()));
                }
                if (orderQuery.getUserId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("makerId").as(Long.class),orderQuery.getUserId()));
                }
                if (orderQuery.getTempTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("tempTypeId").as(Integer.class),orderQuery.getTempTypeId()));
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
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Pageable pageable = new PageRequest(orderQuery.getPage(),orderQuery.getPageSize());
        Page<OrderFinish> pageResult = orderFinishRepository.findAll(specification,pageable);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findById(Long id) {
        if (id == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }

        OrderFinish orderFinish = orderFinishRepository.findOne(id);
        return covertOrderFinishToOrderVoForAll(orderFinish);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo findShopTemplateOrdersInSendTime(Long shopId, Long tempId, Date sendTime, Integer status) {
        OrderFinish orderFinish = orderFinishRepository.findByShopIdAndTempIdAndSendTimeAndStatus(shopId,tempId,sendTime,status);
        return covertOrderFinishToOrderVo(orderFinish);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderVo nextOrder(OrderAuditQuery orderAuditQuery) {
        return covertOrderFinishToOrderVoForAll(nextOrderFinish(orderAuditQuery));
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public OrderFinish nextOrderFinish(OrderAuditQuery orderAuditQuery) {
        orderAuditQuery.setPageSize(1);
        orderAuditQuery.setPage(0);
        List<OrderFinish> orderFinishList = findByPage(orderAuditQuery,orderAuditQuery.getOrderId()).getContent();
        if (orderFinishList != null && !orderFinishList.isEmpty()){
            return orderFinishList.get(0);
        }
        return null;
    }


    /**
     * 统一使用的bean转换器
     * @param orderFinish
     * @return
     */
    private OrderVo covertOrderFinishToOrderVo(OrderFinish orderFinish){
        OrderVo orderVo = null;
        if (orderFinish != null) {
            orderVo = new OrderVo();
            BeanUtils.copyProperties(orderFinish, orderVo);

            orderVo.setStatusMapVo(new MapVo(orderFinish.getStatus().longValue(), i18nHandler(OrderStatus.getOrderStatus(orderFinish.getStatus()).getI18N())));

            MapVo tempTypeMap = new MapVo();
            BeanUtils.copyProperties(orderFinish.getTemplatesType(), tempTypeMap);
            orderVo.setTempTypeMapVo(tempTypeMap);
        }
        return orderVo;
    }

    /**
     * @param orderFinish
     * @return
     */
    private OrderVo covertOrderFinishToOrderVoForAll(OrderFinish orderFinish){
        OrderVo orderVo = covertOrderFinishToOrderVo(orderFinish);
        orderVo.setIsFinish(true);

        orderVo.setTagLineMapVo(new MapVo(orderFinish.getLineId(),orderFinish.getLineName()));
        orderVo.setTagWarehouseMapVo(new MapVo(orderFinish.getWarehouseId(),orderFinish.getWarehouseName()));
        if (orderFinish.getDistributionType() != null) {
            orderVo.setShippingMapVo(new MapVo(orderFinish.getDistributionType().longValue(), i18nHandler(LogisticsStatus.getLogisticsStatus(orderFinish.getDistributionType()).getI18N())));
        }

        //将商品数据塞入
        List<OrderTemplatesFinish> orderTemplatesAuditingList = orderFinish.getOrderTemplatesFinishList();
        orderVo.setOrderTemplateVoList(CovertUtil.copyList(orderTemplatesAuditingList,OrderTemplateVo.class));
        return orderVo;
    }

}
