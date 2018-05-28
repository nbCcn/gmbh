package com.guming.authority.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
@ApiModel(value = "LoginDto",description = "登录用实体类")
@Data
public class LoginDto extends BaseDto {
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "加密后的用户信息参数")
    private String tokenPassInfo;

}
