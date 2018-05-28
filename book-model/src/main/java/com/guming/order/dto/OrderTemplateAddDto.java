package com.guming.order.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/26
 */
@Data
public class OrderTemplateAddDto extends BaseDto {

    @ApiModelProperty(value = "所属订单id")
    private Long orderId;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "数量(默认为0)")
    private Integer amount=0;
}
