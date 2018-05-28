package com.guming.tagline.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 10:37
 */
@ApiModel(value = "TagLineUpdateDto", description = "路线更新实体")
public class TagLineUpdateDto extends TagLineAddDto {

    @ApiModelProperty(value = "路线Id", required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
