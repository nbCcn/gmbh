package com.guming.client.controller.login;

import com.guming.authority.dto.ChangePassDto;
import com.guming.authority.dto.LoginDto;
import com.guming.authority.entity.User;
import com.guming.base.BaseController;
import com.guming.common.base.dto.SingleStringDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.SessionConstants;
import com.guming.dingtalk.response.DingUserInfoResponseParam;
import com.guming.dingtalk.vo.DingSignVo;
import com.guming.service.authority.LoginService;
import com.guming.service.authority.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Api(description = "客户端登陆",tags = "客户端登陆")
@RestController
@RequestMapping("/client/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Override
    public BaseService getService() {
        return this.loginService;
    }

    @ApiOperation(value = "获取钉钉配置参数")
    @ApiImplicitParam(name = "singleStringDto",value = "获取到的code",required = true,dataType = "SingleStringDto")
    @PostMapping("/getDingConfig")
    @ResponseBody
    public ResponseParam<DingSignVo> getDingConfig(@RequestBody SingleStringDto singleStringDto){
        return loginService.config(singleStringDto.getCode());
    }

    @ApiOperation(value = "获取钉钉配置参数")
    @ApiImplicitParam(name = "singleStringDto",value = "获取到的code",required = true,dataType = "SingleStringDto")
    @PostMapping("/validateLogin")
    @ResponseBody
    public ResponseParam<String> validateLogin(@RequestBody SingleStringDto singleStringDto, HttpSession httpSession){
        return loginService.validateLoginForDing(singleStringDto.getCode(),httpSession);
    }

    /**
     *  获取登录token，防止csrf
     */
    @ApiOperation(value = "获取登录token")
    @ApiImplicitParam(name = "loginDto",value = "只需要userName参数",required = true,dataType = "LoginDto")
    @PostMapping("/getClientToken")
    @ResponseBody
    public ResponseParam<String> getToken(@RequestBody LoginDto loginDto,HttpSession httpSession){
        return loginService.getToken(httpSession,loginDto.getUserName());
    }

    @ApiOperation(value = "登录页登录")
    @ApiImplicitParam(name = "singleStringDto",value = "获取到的code",required = true,dataType = "SingleStringDto")
    @PostMapping("/validateClientLogin")
    @ResponseBody
    public ResponseParam<String> login(@RequestBody LoginDto loginDto, HttpSession httpSession){
        return loginService.loginClient(loginDto.getTokenPassInfo(),httpSession);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/loginOutClient")
    @ResponseBody
    public ResponseParam loginOut(HttpSession httpSession){
        return loginService.loginOut(httpSession);
    }

    @ApiOperation(value = "更改密码")
    @ApiImplicitParam(name = "changePassDto",required = true,dataType = "ChangePassDto")
    @PostMapping("changePass")
    @ResponseBody
    public ResponseParam changePass(@RequestBody ChangePassDto changePassDto,HttpSession httpSession){
        return userService.changePass(changePassDto);
    }

}
