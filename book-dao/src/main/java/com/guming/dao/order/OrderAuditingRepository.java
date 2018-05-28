package com.guming.dao.order;

import com.guming.common.base.repository.BaseRepository;
import com.guming.order.entity.OrderAuditing;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
public interface OrderAuditingRepository extends BaseRepository<OrderAuditing,Long> {

    @Query("select o from OrderAuditing o where o.isValid=true and o.shopId=?1 and o.tempId=?2 and o.sendTime=?3 and o.status=?4")
    OrderAuditing findByShopIdAndTempIdAndSendTimeAndStatus(Long shopId, Long tempId, Date sendTime, Integer status);

    @Query("select o from OrderAuditing o where o.isValid=true ")
    List<OrderAuditing> findAllOrderAuditing();
}
