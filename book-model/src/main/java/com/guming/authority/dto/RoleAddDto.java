package com.guming.authority.dto;

import com.guming.common.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "RoleAddDto",description = "角色添加用实体类")
@Data
public class RoleAddDto extends BaseDto {

    @ApiModelProperty(value = "角色名",required = true)
    private String roleName;

    @ApiModelProperty(value = "该角色拥有的菜单权限",required = true)
    private List<AuthorityDto> authorityDtoList;

}
