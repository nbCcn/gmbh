package com.guming.dao.order;

import com.guming.common.base.repository.BaseRepository;
import com.guming.order.entity.OrderFinish;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
public interface OrderFinishRepository extends BaseRepository<OrderFinish,Long> {

    @Query("select o from OrderFinish o where o.isValid=true and o.shopId=?1 and o.tempId=?2 and o.sendTime=?3 and o.status=?4")
    OrderFinish findByShopIdAndTempIdAndSendTimeAndStatus(Long shopId, Long tempId, Date sendTime, Integer status);
}
