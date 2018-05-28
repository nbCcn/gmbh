package com.guming.authority.vo;

import com.guming.common.base.vo.TreeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@ApiModel(value = "roleVo",description = "返回的角色实体")
@Data
public class RoleVo {

    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "是否可删除")
    private Boolean isDelete = false;

    @ApiModelProperty(value = "角色拥有的菜单权限")
    private List<TreeVo> treeVoList;
}
