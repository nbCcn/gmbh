package com.guming.tagwareHouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 11:16
 */
@ApiModel(value = "TagwareHouseUpdateDto", description = "仓库更新实体")
public class TagwareHouseUpdateDto extends TagwareHouseAddDto {
    @ApiModelProperty(value = "店铺ID", required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
