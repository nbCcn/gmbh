package com.guming.authority.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "RoleQuery",description = "角色查询参数实体类")
public class RoleQuery extends PageQuery {

    @ApiModelProperty(value = "角色名")
    public String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
