package com.guming.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/28
 */
@Data
@ApiModel(value = "OrderStatusDto",description = "订单状态变更实体")
public class OrderStatusDto {

    @ApiModelProperty(value = "订单id")
    private Long id;

    @ApiModelProperty(value = "收货店铺id")
    private Long sendShopId;

    @ApiModelProperty(value = "订单状态")
    private Integer status;
}
