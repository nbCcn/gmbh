package com.guming.kingdee.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
public class InventoryProductResponseParam {

    @ApiModelProperty(value = "商品id")
    @JSONField(name = "id")
    private Long productId;

    @ApiModelProperty(value = "商品code")
    @JSONField(name = "gid")
    private String productCode;

    @ApiModelProperty(value = "商品库存")
    @JSONField(name = "kucun")
    private Long kucun;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getKucun() {
        return kucun;
    }

    public void setKucun(Long kucun) {
        this.kucun = kucun;
    }
}
