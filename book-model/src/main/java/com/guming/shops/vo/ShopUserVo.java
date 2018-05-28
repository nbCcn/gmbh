package com.guming.shops.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/21 10:31
 */
@Data
@ApiModel(value = "ShopUserVo", description = "用户信息显示实体")
public class ShopUserVo {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "账号密码")
    private String userPass;

    @ApiModelProperty(value = "用户电话", required = true)
    private String phone;

    @ApiModelProperty(value = "对应角色ID")
    private Long roleId;

    @ApiModelProperty(value = "对应角色名称")
    private String roleName;

}
