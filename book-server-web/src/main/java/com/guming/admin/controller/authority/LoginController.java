package com.guming.admin.controller.authority;

import com.guming.authority.dto.LoginDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.service.authority.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: PengCheng
 * @Description: 登录接口
 * @Date: 2018/4/11
 */
@Api(description = "登录相关",tags = "登录相关")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录验证")
    @ApiImplicitParam(name = "loginDto",value = "只需要tokenPassInfo参数",required = true,dataType = "LoginDto")
    @PostMapping("/validateLogin")
    @ResponseBody
    public ResponseParam validateLogin(@RequestBody LoginDto loginDto,HttpSession httpSession){
        return loginService.validateLogin(httpSession,loginDto.getTokenPassInfo());
    }

    /**
     * 获取登录token，防止csrf
     */
    @ApiOperation(value = "获取登录token")
    @ApiImplicitParam(name = "loginDto",value = "只需要userName参数",required = true,dataType = "LoginDto")
    @PostMapping("/getToken")
    @ResponseBody
    public ResponseParam<String> getToken(@RequestBody LoginDto loginDto, HttpSession httpSession){
        return loginService.getToken(httpSession,loginDto.getUserName());
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/loginOut")
    @ResponseBody
    public ResponseParam loginOut(HttpSession httpSession){
        return loginService.loginOut(httpSession);
    }
}
