package com.guming.order.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
@ApiModel(value = "OrderDto")
public class OrderDto extends BaseDto {
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "模板id")
    private Long tempId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }
}
