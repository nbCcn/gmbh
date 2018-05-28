package com.guming.orderTemplate.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/23
 */
public class TemplatesProductsDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商品数目",required = true)
    private Integer amount;

    @ApiModelProperty(value = "排序码")
    private Long sort;

    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
