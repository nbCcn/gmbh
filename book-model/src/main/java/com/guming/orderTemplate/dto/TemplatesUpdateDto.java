package com.guming.orderTemplate.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
public class TemplatesUpdateDto extends TemplatesAddDto{
    @ApiModelProperty(value = "模板id",required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
