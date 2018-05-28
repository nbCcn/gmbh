package com.guming.tagrank.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 10:51
 */
@ApiModel(value = "TagRankUpdateDto", description = "等级更新实体")
public class TagRankUpdateDto extends TagRankAddDto {
    @ApiModelProperty(value = "等级ID", required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
