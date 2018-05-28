package com.guming.products.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "ProductsClassifyUpdateDto",description = "商品类型更新实体")
public class ProductsClassifyUpdateDto extends ProductsClassifyAddDto{

    @ApiModelProperty(value = "商品类型id",required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
