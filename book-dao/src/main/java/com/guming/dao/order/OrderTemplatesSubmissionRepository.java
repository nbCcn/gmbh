package com.guming.dao.order;


import com.guming.common.base.repository.BaseRepository;
import com.guming.order.entity.OrderTemplatesSubmission;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
public interface OrderTemplatesSubmissionRepository extends BaseRepository<OrderTemplatesSubmission,Long> {

    void deleteAllByProductId(Long productId);

    void deleteByOrderIdAndIsValid(Long orderId, Boolean isValid);

    @Query("select count (o) from OrderTemplatesSubmission o where o.isValid=true and o.orderId=?1")
    Long findCartProductAmount(Long cartId);
}
