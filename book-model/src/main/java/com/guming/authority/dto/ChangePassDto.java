package com.guming.authority.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
@ApiModel(value = "ChangePassDto",description = "修改密码用实体类")
@Data
public class ChangePassDto extends BaseDto {

    @ApiModelProperty(value = "账户id")
    private Long id;

    @ApiModelProperty(value = "账户旧密码",required = true)
    private String oldPass;

    @ApiModelProperty(value = "账户新密码",required = true)
    private String newPass;

    @ApiModelProperty(value = "账户新密码确认",required = true)
    private String validatePass;
}
