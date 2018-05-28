package com.guming.admin.controller.tagline;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.tagline.TagLineService;
import com.guming.tagline.dto.TagLineAddDto;
import com.guming.tagline.dto.TagLineUpdateDto;
import com.guming.tagline.dto.query.TagLineQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:送货路线入口
 * @Date: 2018/4/20 10:43
 */
@Api(description = "送货路线",tags = "送货路线")
@RequestMapping("tagline")
@RestController
public class TagLineController {

    @Autowired
    private TagLineService tagLineService;

    @ApiOperation(value = "查询送货路线")
    @ApiImplicitParam(name = "tagLineQuery", required = true, dataType = "TagLineQuery")
    @PostMapping("/find")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.LOOK)
    public @ResponseBody
    ResponseParam<?> find(@RequestBody TagLineQuery tagLineQuery) {
        return tagLineService.find(tagLineQuery);
    }

    @ApiOperation(value = "新增送货路线")
    @ApiImplicitParam(name = "tagLineAddDto", required = true, dataType = "TagLineAddDto")
    @PostMapping("/add")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.ADD)
    public @ResponseBody
    ResponseParam<?> add(@RequestBody TagLineAddDto tagLineAddDto) {
        return tagLineService.add(tagLineAddDto);
    }

    @ApiOperation(value = "删除送货路线")
    @ApiImplicitParam(name = "idsDto", required = true, dataType = "IdsDto")
    @PostMapping("/delete")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam<?> delete(@RequestBody IdsDto idsDto) {
        return tagLineService.delete(idsDto.getIds());
    }

    @ApiOperation(value = "更新送货路线")
    @ApiImplicitParam(name = "tagLineUpdateDto", required = true, dataType = "TagLineUpdateDto")
    @PostMapping("/update")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.EDIT)
    public @ResponseBody
    ResponseParam<?> update(@RequestBody TagLineUpdateDto tagLineUpdateDto) {
        return tagLineService.update(tagLineUpdateDto);
    }

    @ApiOperation(value = "通过仓库id查询仓库下的路线")
    @ApiImplicitParam(name = "tagLineQuery", required = true, dataType = "TagLineQuery")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.LOOK)
    @PostMapping("/findTagLingByWarehouseId")
    public @ResponseBody
    ResponseParam<List<MapVo>> findTagLingByWarehouseId(@RequestBody TagLineQuery tagLineQuery) {
        return tagLineService.findByWarehouseId(tagLineQuery.getTagwareHouseId());
    }

    @ApiOperation(value = "通过仓库Id查询详情")
    @ApiImplicitParam(name = "idDto", required = true, dataType = "IdDto")
    @MenuOperateAuthority(belongMenuCode = "002004", operationType = OperationType.LOOK)
    @PostMapping("/edit")
    public @ResponseBody
    ResponseParam<?> edit(@RequestBody IdDto idDto) {
        return tagLineService.findById(idDto.getId());
    }


}
