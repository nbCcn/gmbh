package com.guming.shops.dto;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
@ApiModel(value = "ShopLineProductsQuery")
public class ShopLineProductsQuery extends PageQuery {

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "模板id")
    private Long tempId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品分类id")
    private Long productClassId;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(Long productClassId) {
        this.productClassId = productClassId;
    }
}
