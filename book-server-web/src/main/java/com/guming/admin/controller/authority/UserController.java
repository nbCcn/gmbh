package com.guming.admin.controller.authority;

import com.guming.authority.dto.ChangePassDto;
import com.guming.authority.dto.UserAddDto;
import com.guming.authority.dto.UserUpdateDto;
import com.guming.authority.dto.query.UserQuery;
import com.guming.authority.vo.UserVo;
import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.authority.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@Api(description = "用户",tags = "用户")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加用户",tags = "用户")
    @ApiImplicitParam(name = "userAddDto",required = true,dataType = "UserAddDto")
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006001",operationType = OperationType.ADD)
    public ResponseParam addUser(@RequestBody UserAddDto userAddDto){
        return userService.addUser(userAddDto);
    }

    @ApiOperation(value = "更新用户",tags = "用户")
    @ApiImplicitParam(name = "userUpdateDto",required = true,dataType = "UserUpdateDto")
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006001",operationType = OperationType.EDIT)
    public ResponseParam updateUser(@RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUser(userUpdateDto);
    }

    @ApiOperation(value = "删除用户",tags = "用户")
    @ApiImplicitParam(name = "idsDto",value = "用户id组实体",required = true,dataType = "IdsDto")
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006001",operationType = OperationType.DELETE)
    public ResponseParam deleteUser(@RequestBody IdsDto idsDto){
        return userService.deleteUser(idsDto.getIds());
    }

    @ApiOperation(value = "分页查询用户",tags = "用户")
    @ApiImplicitParam(name = "userQuery",required = true,dataType = "UserQuery")
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006001",operationType = OperationType.LOOK)
    public ResponseParam<List<UserVo>> findUserByPage(@RequestBody UserQuery userQuery){
        return userService.findUserByPage(userQuery);
    }

    @ApiOperation(value = "通过用户id查询用户",tags = "用户")
    @ApiImplicitParam(name = "idDto",value = "用户id实体",required = true,dataType = "IdDto")
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006001",operationType = OperationType.LOOK)
    public ResponseParam<UserVo> editUser(@RequestBody IdDto idDto){
        return userService.findById(idDto.getId());
    }

    @ApiOperation(value = "更改密码",tags = "用户")
    @ApiImplicitParam(name = "changePassDto",required = true,dataType = "ChangePassDto")
    @PostMapping("changePass")
    @ResponseBody
    public ResponseParam changePass(@RequestBody ChangePassDto changePassDto){
        return userService.changePass(changePassDto);
    }

}
