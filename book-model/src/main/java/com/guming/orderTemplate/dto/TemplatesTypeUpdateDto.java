package com.guming.orderTemplate.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "TemplatesTypeUpdateDto",description = "订单模板类型更新实体")
public class TemplatesTypeUpdateDto extends TemplatesTypeAddDto{
    @ApiModelProperty(value = "模板类型id",required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
