package com.guming.tagwareHouse.dto;

import com.guming.common.base.dto.BaseDto;
import com.guming.tagbank.dto.TagBankAddDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/23 11:12
 */
@ApiModel(value = "TagwareHouseAddDto", description = "仓库新增实体")
public @Data class TagwareHouseAddDto extends BaseDto {

    @ApiModelProperty(value = "仓库名称", required = true)
    private String name;
    @ApiModelProperty(value = "分组")
    private Integer groupId;
    @ApiModelProperty(value = "经度")
    private Double lng;
    @ApiModelProperty(value = "纬度")
    private Double lat;
    @ApiModelProperty(value = "webhook")
    private String webhook;
    @ApiModelProperty(value = "tagBankAddDtoList")
    private List<TagBankAddDto> tagBankAddDtoList;

}
