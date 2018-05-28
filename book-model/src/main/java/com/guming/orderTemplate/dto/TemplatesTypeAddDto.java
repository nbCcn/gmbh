package com.guming.orderTemplate.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "TemplatesTypeAddDto",description = "订单模板类型添加实体")
public class TemplatesTypeAddDto extends BaseDto {

    @ApiModelProperty(value = "类型名",required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
