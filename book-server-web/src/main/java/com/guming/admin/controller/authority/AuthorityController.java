package com.guming.admin.controller.authority;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.base.vo.TreeVo;
import com.guming.common.constants.business.OperationType;
import com.guming.service.authority.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 权限接口（权限模块与角色模块的权限操作同步）
 * @Date: 2018/4/13
 */
@Api(description = "菜单权限",tags = "菜单权限")
@RestController
@RequestMapping("authority")
public class AuthorityController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查询当前用户的菜单权限")
    @PostMapping("findCurrentUserMenu")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.LOOK)
    public ResponseParam<List<TreeVo>> findCurrentUserMenu(HttpServletRequest request, HttpServletResponse response){
        return menuService.findCurrentUserMenu(request,response);
    }

    @ApiOperation(value = "查询角色拥有的菜单权限")
    @ApiImplicitParam(name = "idDto", value = "角色id", required = true, dataType = "IdDto")
    @PostMapping("findAllMenusRoleHas")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.LOOK)
    public ResponseParam<List<TreeVo>> findAllMenusRoleHas(@RequestBody IdDto idDto){
        return menuService.findAllMenusRoleHas(idDto.getId());
    }

    @ApiOperation(value = "查询所有的菜单权限")
    @PostMapping("findAllMenu")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006002",operationType = OperationType.LOOK)
    public ResponseParam<List<TreeVo>> findAllMenu(){
        return menuService.findAllMenus();
    }

}
