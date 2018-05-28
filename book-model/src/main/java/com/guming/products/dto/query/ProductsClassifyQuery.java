package com.guming.products.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "ProductsClassifyQuery",description = "商品分类分页查询实体")
public class ProductsClassifyQuery extends PageQuery {

    @ApiModelProperty(value = "商品分类名")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
