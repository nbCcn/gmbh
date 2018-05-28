package com.guming.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
@Data
@ApiModel(value = "OrderUpdateDto",description = "订单更新实体")
public class OrderUpdateDto extends OrderCartDto {

    @ApiModelProperty(value = "订单id")
    private Long id;
}
