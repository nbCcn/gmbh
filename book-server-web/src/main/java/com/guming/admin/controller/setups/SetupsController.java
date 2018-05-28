package com.guming.admin.controller.setups;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.setups.SetupsService;
import com.guming.setups.dto.BasicConfigDto;
import com.guming.setups.vo.BasicConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
@Api(description = "配置",tags = "配置")
@RestController
@RequestMapping("setups")
public class SetupsController {

    @Autowired
    private SetupsService setupsService;

    @ApiOperation(value = "查询基础配置参数")
    @PostMapping("basicConfig/find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006004",operationType = OperationType.LOOK)
    private ResponseParam<BasicConfigVo> findBasicConfig(){
        return setupsService.findBasicConfig();
    }

    @ApiOperation(value = "更新基础配置参数")
    @ApiImplicitParam(dataType = "BasicConfigDto", name = "basicConfigDto", required = true)
    @PostMapping("basicConfig/update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "006004",operationType = OperationType.EDIT)
    private ResponseParam updateBasicConfig(@RequestBody BasicConfigDto basicConfigDto){
        return setupsService.updateBasicConfig(basicConfigDto);
    }
}
