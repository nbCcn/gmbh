package com.guming.authority.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@ApiModel(value = "RoleUpdateDto",description = "角色更新用实体类")
@Data
public class RoleUpdateDto extends RoleAddDto {

    @ApiModelProperty(value = "角色id",required = true)
    private Long id;
}
