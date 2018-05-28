package com.guming.order.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/25
 */
@Data
@ApiModel(value = "OrderCartDto",description = "订单添加实体")
public class OrderCartDto extends BaseDto {

    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    @ApiModelProperty(value = "下单商店id",required = true)
    private Long shopId;

    @ApiModelProperty(value = "模板id",required = true)
    private Long tempId;

    @ApiModelProperty(value = "商品信息",required = true)
    private List<OrderTemplateAddDto> orderTemplateAddDtoList;
}
