package com.guming.common.base.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/16
 */
public class IdDto extends BaseDto {

    @ApiModelProperty(value = "id",notes = "新增时不用传，更新时必传")
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
