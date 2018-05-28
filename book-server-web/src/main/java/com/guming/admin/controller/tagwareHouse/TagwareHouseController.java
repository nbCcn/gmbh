package com.guming.admin.controller.tagwareHouse;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.tagwareHouse.TagwareHouseService;
import com.guming.tagwareHouse.dto.TagwareHouseAddDto;
import com.guming.tagwareHouse.dto.TagwareHouseUpdateDto;
import com.guming.tagwareHouse.dto.query.TagwareHouseQuery;
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
 * @Auther: Ccn
 * @Description:仓库入口
 * @Date: 2018/4/11 12:48
 */
@Api(description = "仓库模块",tags = "仓库模块")
@Controller
@RequestMapping("tagwareHouse")
public class TagwareHouseController {

    @Autowired
    private TagwareHouseService tagwareHouseService;

    @ApiOperation(value = "查询仓库")
    @ApiImplicitParam(name = "tagwareHouseDto", required = true, dataType = "TagwareHouseQuery")
    @PostMapping("/find")
    @MenuOperateAuthority(belongMenuCode = "006004", operationType = OperationType.LOOK)
    public @ResponseBody
    ResponseParam<?> findTagwareHouse(@RequestBody TagwareHouseQuery tagwareHouseDto) {
        return tagwareHouseService.find(tagwareHouseDto);
    }

    @ApiOperation(value = "新增仓库")
    @ApiImplicitParam(name = "tagwareHouseAddDto", required = true, dataType = "TagwareHouseAddDto")
    @PostMapping("/add")
    @MenuOperateAuthority(belongMenuCode = "006004", operationType = OperationType.ADD)
    public @ResponseBody
    ResponseParam<?> addTagwareHouse(@RequestBody TagwareHouseAddDto tagwareHouseAddDto) {
        return tagwareHouseService.add(tagwareHouseAddDto);
    }

    @ApiOperation(value = "更新仓库")
    @ApiImplicitParam(name = "tagwareHouseUpdateDto", required = true, dataType = "TagwareHouseUpdateDto")
    @PostMapping("/update")
    @MenuOperateAuthority(belongMenuCode = "006004", operationType = OperationType.EDIT)
    public @ResponseBody
    ResponseParam<?> updateTagwareHouse(@RequestBody TagwareHouseUpdateDto tagwareHouseUpdateDto) {
        return tagwareHouseService.update(tagwareHouseUpdateDto);
    }

    @ApiOperation(value = "删除仓库")
    @ApiImplicitParam(name = "idsDto", required = true, dataType = "IdsDto")
    @PostMapping("/delete")
    @MenuOperateAuthority(belongMenuCode = "006004", operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam<?> deleteTagwareHouse(@RequestBody IdsDto idsDto) {
        return tagwareHouseService.delete(idsDto.getIds());
    }

    @ApiOperation(value = "查询仓库详情")
    @ApiImplicitParam(name = "idDto", required = true, dataType = "IdDto")
    @PostMapping("/edit")
    @MenuOperateAuthority(belongMenuCode = "006004", operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam<?> editTagwareHouse(@RequestBody IdDto idDto) {
        return tagwareHouseService.findById(idDto.getId());
    }

}
