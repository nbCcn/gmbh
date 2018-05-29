package com.guming.dao.order;

import com.guming.common.base.repository.BaseRepository;
import com.guming.order.entity.OrderSubmission;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
public interface OrderSubmissionRepository extends BaseRepository<OrderSubmission,Long> {

    @Query("select o from OrderSubmission o where o.isValid=true and o.tempId=?1 and o.status=?2")
    List<OrderSubmission> findAllByTempIdAndStatus(Long tempId, Integer status);

    void deleteAllByTempIdAndStatus(Long tempId, Integer status);

    @Query("select o from OrderSubmission o where o.isValid=true and o.shopId=?1 and o.tempId=?2 and o.sendTime=?3 and o.status=?4")
    OrderSubmission findByShopIdAndTempIdAndStatus(Long shopId, Long tempId, Date sendTime, Integer status);

    @Query("select o from OrderSubmission o where o.isValid=true and o.status=1 and o.shopId=?1 and o.tempId=?2")
    OrderSubmission findCartByShopIdAndTempId(Long shopId, Long tempId);

    @Query("select o from OrderSubmission o where o.isValid=true and o.id=?1 and o.status=?2")
    OrderSubmission findByIdAndStatus(Long id, Integer status);

    @Query("select o from  OrderSubmission o where o.sendTime < ?1 and o.status=?2")
    List<OrderSubmission> findExpireUnAuditOrder(Date date,Integer status);
}
