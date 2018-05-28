package com.guming.admin.controller.orderTemplate;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.orderTemplate.dto.TemplatesActiveDto;
import com.guming.orderTemplate.dto.TemplatesAddDto;
import com.guming.orderTemplate.dto.TemplatesUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesQuery;
import com.guming.orderTemplate.vo.TemplatesVo;
import com.guming.service.orderTemplate.TemplatesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Api(description = "订单模板",tags = "订单模板")
@Controller
@RequestMapping("templates")
public class TemplatesController {

    @Autowired
    private TemplatesService templatesService;

    @ApiOperation(value ="添加")
    @ApiImplicitParam(dataType = "TemplatesAddDto", name = "templatesAddDto", required = true)
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "005003",operationType = OperationType.ADD)
    public ResponseParam add(@RequestBody TemplatesAddDto templatesAddDto){
        return templatesService.add(templatesAddDto);
    }

    @ApiOperation(value ="更新")
    @ApiImplicitParam(dataType = "TemplatesUpdateDto", name = "templatesUpdateDto", required = true)
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003004",operationType = OperationType.EDIT)
    public ResponseParam update(@RequestBody TemplatesUpdateDto templatesUpdateDto){
        return templatesService.update(templatesUpdateDto);
    }

    @ApiOperation(value ="编辑")
    @ApiImplicitParam(dataType = "IdDto", name = "idDto", required = true)
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "005003",operationType = OperationType.LOOK)
    public ResponseParam<TemplatesVo> edit(@RequestBody IdDto idDto){
        return templatesService.findById(idDto.getId());
    }

    @ApiOperation(value ="删除")
    @ApiImplicitParam(dataType = "IdsDto", name = "idsDto", required = true)
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003004",operationType = OperationType.DELETE)
    public ResponseParam delete(@RequestBody IdsDto idsDto){
        return templatesService.delete(idsDto.getIds());
    }

    @ApiOperation(value ="分页查询")
    @ApiImplicitParam(dataType = "TemplatesQuery", name = "templatesQuery", required = true)
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003004",operationType = OperationType.LOOK)
    public ResponseParam<List<TemplatesVo>> findByPage(@RequestBody TemplatesQuery templatesQuery){
        return templatesService.findByPage(templatesQuery);
    }

    @ApiOperation(value = "有效状态更新")
    @ApiImplicitParam(name = "templatesActiveDtoList",required = true,dataType = "List<TemplatesActiveDto>")
    @PostMapping("updateActive")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003004",operationType = OperationType.LOOK)
    public ResponseParam updateActive(@RequestBody List<TemplatesActiveDto> templatesActiveDtoList){
        return templatesService.updateActive(templatesActiveDtoList);
    }
}
