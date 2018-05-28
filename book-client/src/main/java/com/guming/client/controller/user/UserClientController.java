package com.guming.client.controller.user;

import com.guming.authority.dto.ChangePassDto;
import com.guming.base.BaseController;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.service.authority.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/16
 */
@Api(description = "客户端用户接口",tags = "客户端用户接口")
@Controller
@RequestMapping("client/user")
public class UserClientController extends BaseController {

    @Autowired
    private UserService userService;

    @Override
    public BaseService getService() {
        return this.userService;
    }

    @ApiOperation(value = "获取当前用户")
    @PostMapping("getCurrentUser")
    @ResponseBody
    public ResponseParam<String> getCurrentUser(){
        return ResponseParam.success(getCurrentClientUser().getUserName());
    }

    @ApiOperation(value = "更改密码")
    @ApiImplicitParam(name = "changePassDto",required = true,dataType = "ChangePassDto")
    @PostMapping("changePass")
    @ResponseBody
    public ResponseParam changePass(@RequestBody ChangePassDto changePassDto){
        return userService.changePass(changePassDto);
    }


}
