package com.guming.authority.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "UserUpdateDto",description = "用户更新用实体类")
@Data
public class UserUpdateDto extends UserAddDto{

    @ApiModelProperty(value = "用户id",required = true)
    private Long id;
}
