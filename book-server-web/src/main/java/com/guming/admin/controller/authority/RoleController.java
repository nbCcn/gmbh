package com.guming.admin.controller.authority;

import com.guming.authority.dto.RoleAddDto;
import com.guming.authority.dto.RoleUpdateDto;
import com.guming.authority.dto.query.RoleQuery;
import com.guming.authority.vo.RoleVo;
import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.authority.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 角色控制器
 * @Date: 2018/4/15
 */
@Api(description = "角色",tags = "角色")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "添加角色",tags = "角色")
    @ApiImplicitParam(name = "roleAddDto",value = "角色更新实体",required = true,dataType = "RoleAddDto")
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.ADD)
    public ResponseParam addRole(@RequestBody RoleAddDto roleAddDto){
        return roleService.addRole(roleAddDto);
    }

    @ApiOperation(value = "更新角色",tags = "角色")
    @ApiImplicitParam(name = "roleUpdateDto",value = "更新角色实体",required = true,dataType = "RoleUpdateDto")
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.EDIT)
    public ResponseParam updateRole(@RequestBody RoleUpdateDto roleUpdateDto){
        return roleService.updateRole(roleUpdateDto);
    }

    @ApiOperation(value = "删除角色",tags = "角色")
    @ApiImplicitParam(name = "idsDto",value = "角色id组",required = true,dataType = "IdsDto")
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.DELETE)
    public ResponseParam deleteRole(@RequestBody IdsDto idsDto){
        return roleService.deleteRole(idsDto.getIds());
    }

    @ApiOperation(value = "分页查询角色",tags = "角色")
    @ApiImplicitParam(name = "idsDto",value = "角色id组实体",required = true,dataType = "IdsDto")
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.LOOK)
    public ResponseParam<List<RoleVo>> findRole(@RequestBody RoleQuery roleQuery){
        return roleService.findRole(roleQuery);
    }

    @ApiOperation(value = "通过角色id查询角色",tags = "角色")
    @ApiImplicitParam(name = "idDto",value = "角色id实体",required = true,dataType = "IdDto")
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.EDIT)
    public ResponseParam editRole(@RequestBody IdDto IdDto){
        return roleService.edit(IdDto);
    }

    @ApiOperation(value = "查询所有的角色",tags = {"角色","下拉框"})
    @PostMapping("findAllRole")
    @ResponseBody
    public ResponseParam<List<RoleVo>> findAllRole(){
        return roleService.findAllRole();
    }

}
