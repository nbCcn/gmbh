package com.guming.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
@Data
public class OrderTemplateUpdateDto extends OrderTemplateAddDto{
    @ApiModelProperty("订单对应商品的id")
    private Long id;
}
