package com.guming.orderTemplate.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/23
 */
@ApiModel(value = "TemplatesActiveDto",description = "订单模板类有效操作实体")
public class TemplatesActiveDto {

    @ApiModelProperty(value = "模板id")
    private Long id;

    @ApiModelProperty(value = "是否有效")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
