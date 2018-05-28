package com.guming.service.order;

import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.OrderTemplateAddDto;
import com.guming.order.dto.OrderTemplateProductAddDto;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
public interface OrderTemplateService extends BaseService {
    ResponseParam delete(List<Long> ids);

    ResponseParam updateAmountById(Long id, Integer amount, Long orderId);

    ResponseParam add(OrderTemplateAddDto orderTemplateAddDto);

    /**
     * 更新对象isValid状态
     * @param ids
     * @param isValid
     */
    void updateIsValid(List<Long> ids, Boolean isValid);

    /**
     * 添加訂單中的商品
     * @param orderTemplateProductAddDto
     */
    void addOrderProducts(OrderTemplateProductAddDto orderTemplateProductAddDto);
}
