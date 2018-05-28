package com.guming.dao.order;


import com.guming.common.base.repository.BaseRepository;
import com.guming.order.entity.OrderTemplatesSubmission;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
public interface OrderTemplatesSubmissionRepository extends BaseRepository<OrderTemplatesSubmission,Long> {

    void deleteAllByProductId(Long productId);

    void deleteByOrderIdAndIsValid(Long orderId, Boolean isValid);
}
