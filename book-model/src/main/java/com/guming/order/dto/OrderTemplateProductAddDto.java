package com.guming.order.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/24
 */
@Data
@ApiModel(value = "OrderTemplateProductAddDto",description = "订单内商品添加实体")
public class OrderTemplateProductAddDto extends BaseDto {

    @ApiModelProperty(value = "所属订单id")
    private Long orderId;

    @ApiModelProperty(value = "訂單商品")
    private List<Long> productIdList;
}
