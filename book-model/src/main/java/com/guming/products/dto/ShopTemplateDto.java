package com.guming.products.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/8
 */
@ApiModel(value = "ShopTemplateDto")
public class ShopTemplateDto {
    @ApiModelProperty(value = "商店id")
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
