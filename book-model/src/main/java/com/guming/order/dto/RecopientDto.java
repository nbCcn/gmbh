package com.guming.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/2
 */
@Data
@ApiModel(value = "RecopientDto",description = "查询收货店铺的实体")
public class RecopientDto {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;
}
