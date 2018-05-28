package com.guming.products.dto;

import com.guming.orderTemplate.dto.TemplatesProductsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/23
 */
@ApiModel(value = "ProductsUpdateDto",description = "商品更新用实体")
public class ProductsUpdateDto extends ProductsAddDto{
    @ApiModelProperty(value = "商品id",required = true)
    private Long id;

    @ApiModelProperty(value = "所在模板数据",required = true)
    private List<TemplatesProductsDto> templatesProductsDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TemplatesProductsDto> getTemplatesProductsDtoList() {
        return templatesProductsDtoList;
    }

    public void setTemplatesProductsDtoList(List<TemplatesProductsDto> templatesProductsDtoList) {
        this.templatesProductsDtoList = templatesProductsDtoList;
    }

}
