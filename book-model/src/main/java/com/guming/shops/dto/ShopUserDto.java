package com.guming.shops.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/18 13:27
 */
@Data
@ApiModel(value = "ShopUserDto", description = "店铺添加实体")
public class ShopUserDto {

    @ApiModelProperty(value = "账号id")
    private Long userId;

    @ApiModelProperty(value = "账号密码")
    private String userPass;

    @ApiModelProperty(value = "用户电话", required = true)
    private String phone;

    @ApiModelProperty(value = "是否冻结", required = true)
    private Boolean isActive = true;

    @ApiModelProperty(value = "对应角色ID")
    private Long roleId;

}
