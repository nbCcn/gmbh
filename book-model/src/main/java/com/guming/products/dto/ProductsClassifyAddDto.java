package com.guming.products.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "ProductsClassifyAddDto",description = "商品类型添加实体")
public class ProductsClassifyAddDto extends BaseDto {
    @ApiModelProperty(value = "商品类型名",required = true)
    private String name;

    @ApiModelProperty(value = "商品类型排序码")
    private Long order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
