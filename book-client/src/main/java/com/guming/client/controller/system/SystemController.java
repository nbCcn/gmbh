package com.guming.client.controller.system;

import com.guming.service.system.SystemService;
import com.guming.common.base.vo.ResponseParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/10
 */
@Api(description = "客户端系统参数接口",tags = "客户端系统参数接口")
@Controller
@RequestMapping("client/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "获取系统版本")
    @PostMapping("getVersion")
    @ResponseBody
    public ResponseParam<String> getVersion(){
        return ResponseParam.success(systemService.getVersion());
    }

    @ApiOperation(value = "获取系统联系电话")
    @PostMapping("getContactNumber")
    @ResponseBody
    public ResponseParam<String> getContactNumber(){
        return ResponseParam.success(systemService.getContactNumber());
    }
}
