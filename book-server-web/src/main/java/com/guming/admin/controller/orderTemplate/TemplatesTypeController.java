package com.guming.admin.controller.orderTemplate;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.orderTemplate.dto.TemplatesTypeAddDto;
import com.guming.orderTemplate.dto.TemplatesTypeUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesTypeQuery;
import com.guming.orderTemplate.vo.TemplatesTypeVo;
import com.guming.service.orderTemplate.TemplatesTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Api(description = "订单模板类型",tags = "订单模板类型")
@RestController
@RequestMapping("templatesType")
public class TemplatesTypeController {

    @Autowired
    private TemplatesTypeService templatesTypeService;

    @ApiOperation(value ="添加")
    @ApiImplicitParam(dataType = "TemplatesTypeAddDto", name = "templatesTypeAddDto", required = true)
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003003",operationType = OperationType.ADD)
    public ResponseParam add(@RequestBody TemplatesTypeAddDto templatesTypeAddDto){
        return templatesTypeService.add(templatesTypeAddDto);
    }

    @ApiOperation(value ="更新")
    @ApiImplicitParam(dataType = "TemplatesTypeUpdateDto", name = "templatesTypeUpdateDto", required = true)
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003003",operationType = OperationType.EDIT)
    public ResponseParam update(@RequestBody TemplatesTypeUpdateDto templatesTypeUpdateDto){
        return templatesTypeService.update(templatesTypeUpdateDto);
    }

    @ApiOperation(value ="编辑")
    @ApiImplicitParam(dataType = "IdDto", name = "idDto", required = true)
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003003",operationType = OperationType.LOOK)
    public ResponseParam<TemplatesTypeVo> edit(@RequestBody IdDto idDto){
        return templatesTypeService.findById(idDto.getId());
    }

    @ApiOperation(value ="删除")
    @ApiImplicitParam(dataType = "IdsDto", name = "idsDto", required = true)
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003003",operationType = OperationType.DELETE)
    public ResponseParam delete(@RequestBody IdsDto idsDto){
        return templatesTypeService.delete(idsDto.getIds());
    }

    @ApiOperation(value ="分页查询")
    @ApiImplicitParam(dataType = "TemplatesTypeQuery", name = "templatesTypeQuery", required = true)
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003003",operationType = OperationType.LOOK)
    public ResponseParam<List<TemplatesTypeVo>> findByPage(@RequestBody TemplatesTypeQuery templatesTypeQuery){
        return templatesTypeService.findByPage(templatesTypeQuery);
    }

    @ApiOperation(value ="查询模板分类下拉框参数",tags = {"下拉框"})
    @PostMapping("findTemplatesTypeList")
    @ResponseBody
    public ResponseParam<List<MapVo>> findTemplatesTypeList(){
        return templatesTypeService.findTemplatesTypeList();
    }
}
