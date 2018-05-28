package com.guming.authority.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description: 菜单传入参数
 * @Date: 15:23 2018/4/10/010
 */
@ApiModel(value = "AuthorityDto",description = "菜单权限实体")
@Data
public class AuthorityDto extends BaseDto {
    @ApiModelProperty(value = "菜单code",example = "001")
    private String menuCode;

    @ApiModelProperty(value = "操作 （add、edit、delete、look、import、export）",example = "add")
    private String operation;
}
