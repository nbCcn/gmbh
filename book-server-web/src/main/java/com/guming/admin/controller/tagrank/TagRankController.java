package com.guming.admin.controller.tagrank;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.tagrank.TagRankService;
import com.guming.tagrank.dto.TagRankAddDto;
import com.guming.tagrank.dto.TagRankUpdateDto;
import com.guming.tagrank.dto.query.TagRankQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Ccn
 * @Description:加盟等级入口
 * @Date: 2018/4/13 21:19
 */
@Api(tags = "加盟等级模块",description = "加盟等级模块")
@RestController
@RequestMapping("/setupsTagrank")
public class TagRankController {

    @Autowired
    private TagRankService tagRankService;

    @ApiOperation(value = "查询加盟等级")
    @ApiImplicitParam(name = "setupsTagrankDto", required = true, dataType = "TagRankQuery")
    @PostMapping("/find")
    @ResponseBody
    public ResponseParam<?> findTagrank(@RequestBody TagRankQuery setupsTagrankDto) {
        return tagRankService.findTagrank(setupsTagrankDto);
    }

    @ApiOperation(value = "查询加盟等级")
    @ApiImplicitParam(name = "setupsTagrankDto", required = true, dataType = "TagRankQuery")
    @PostMapping("/findQuery")
    @MenuOperateAuthority(belongMenuCode = "005002", operationType = OperationType.LOOK)
    @ResponseBody
    public ResponseParam<?> findTagrankQuery(@RequestBody TagRankQuery setupsTagrankDto) {
        return tagRankService.findTagrank(setupsTagrankDto);
    }

    @ApiOperation(value = "删除加盟等级")
    @ApiImplicitParam(name = "idsDto", required = true, dataType = "IdsDto")
    @PostMapping("/delete")
    @MenuOperateAuthority(belongMenuCode = "005002", operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam<?> deleteTagrank(@RequestBody IdsDto idsDto) {
        return tagRankService.deleteTagrank(idsDto.getIds());
    }

    @ApiOperation(value = "新增加盟等级")
    @ApiImplicitParam(name = "tagRankAddDto", required = true, dataType = "TagRankAddDto")
    @PostMapping("/add")
    @MenuOperateAuthority(belongMenuCode = "005002", operationType = OperationType.ADD)
    public @ResponseBody
    ResponseParam<?> addTagrank(@RequestBody TagRankAddDto tagRankAddDto) {
        return tagRankService.addTagrank(tagRankAddDto);
    }

    @ApiOperation(value = "更新加盟等级")
    @ApiImplicitParam(name = "tagRankUpdateDto", required = true, dataType = "TagRankUpdateDto")
    @PostMapping("/update")
    @MenuOperateAuthority(belongMenuCode = "005002", operationType = OperationType.EDIT)
    public @ResponseBody
    ResponseParam<?> updateTagrank(@RequestBody TagRankUpdateDto tagRankUpdateDto) {
        return tagRankService.updateTagrank(tagRankUpdateDto);
    }


}
