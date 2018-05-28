package com.guming.client.controller.login;

import com.guming.authority.dto.LoginDto;
import com.guming.base.BaseController;
import com.guming.common.base.dto.SingleStringDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.dingtalk.vo.DingSignVo;
import com.guming.service.authority.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Override
    public BaseService getService() {
        return this.loginService;
    }

    @ApiOperation(value = "获取钉钉配置参数")
    @PostMapping("/getDingConfig")
    @ResponseBody
    public ResponseParam<DingSignVo> getDingConfig(){
        return loginService.config(getRequest());
    }


    @ApiOperation(value = "获取钉钉配置参数")
    @ApiImplicitParam(name = "singleStringDto",value = "获取到的code",required = true,dataType = "SingleStringDto")
    @PostMapping("/validateLogin")
    @ResponseBody
    public ResponseParam<String> validateLogin(@RequestBody SingleStringDto singleStringDto, HttpServletRequest request, HttpServletResponse response){
        return loginService.validateLoginForDingWithLogin(singleStringDto.getCode(),request,response);
    }


    /**
     *  获取登录token，防止csrf
     */
    @ApiOperation(value = "获取登录token")
    @ApiImplicitParam(name = "loginDto",value = "只需要userName参数",required = true,dataType = "LoginDto")
    @PostMapping("/getClientToken")
    @ResponseBody
    public ResponseParam<String> getToken(@RequestBody LoginDto loginDto, HttpServletRequest request){
        return loginService.getToken(request,loginDto.getUserName());
    }

    @ApiOperation(value = "登录页登录")
    @ApiImplicitParam(name = "singleStringDto",value = "获取到的code",required = true,dataType = "SingleStringDto")
    @PostMapping("/validateClientLogin")
    @ResponseBody
    public ResponseParam<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){
        return loginService.loginClient(loginDto.getTokenPassInfo(),request,response);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/loginOutClient")
    @ResponseBody
    public ResponseParam loginOut(HttpServletRequest request, HttpServletResponse response){
        return loginService.loginOutClient(request,response);
    }


}
