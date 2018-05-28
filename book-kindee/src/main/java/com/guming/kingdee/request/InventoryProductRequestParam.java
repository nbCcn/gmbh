package com.guming.kingdee.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/14
 */
public class InventoryProductRequestParam {

    /**
     * 商品id
     */
    @JSONField(name = "product_id")
    private Long productId;

    /**
     * 商品code
     */
    @JSONField(name = "product_gid")
    private String productGid;

    /**
     * 总数
     */
    @JSONField(name = "amount")
    private Integer amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductGid() {
        return productGid;
    }

    public void setProductGid(String productGid) {
        this.productGid = productGid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
