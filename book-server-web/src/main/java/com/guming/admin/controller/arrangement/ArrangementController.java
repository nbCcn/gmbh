package com.guming.admin.controller.arrangement;


import com.guming.arrangement.dto.ArrangementMoveDto;
import com.guming.arrangement.dto.ArrangementReMoveDto;
import com.guming.arrangement.dto.query.ArrangementQueryDto;
import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.arrangement.ArrangementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:10
 */
@Api(description = "路线安排",tags = "路线安排")
@RestController
@RequestMapping("/arrangement")
public class ArrangementController {

    @Autowired
    private ArrangementService arrangementService;

    @ApiOperation(value = "路线安排查询",tags = "路线安排")
    @ApiImplicitParam(name = "arrangementQueryDto", required = true, dataType = "ArrangementQueryDto")
    @PostMapping("/find")
    @MenuOperateAuthority(belongMenuCode = "002001",operationType = OperationType.LOOK)
    public @ResponseBody
    ResponseParam find(@RequestBody ArrangementQueryDto arrangementQueryDto) {
        return arrangementService.find(arrangementQueryDto);
    }

    @ApiOperation(value = "路线安排移入",tags = "路线安排")
    @ApiImplicitParam(name = "arrangementMoveDto", required = true, dataType = "ArrangementMoveDto")
    @PostMapping("/move")
    @MenuOperateAuthority(belongMenuCode = "002001",operationType = OperationType.ADD)
    public @ResponseBody
    ResponseParam move(@RequestBody ArrangementMoveDto arrangementMoveDto) {
        return arrangementService.move(arrangementMoveDto);
    }

    @ApiOperation(value = "路线安排移出",tags = "路线安排")
    @ApiImplicitParam(name = "arrangementReMoveDto", required = true, dataType = "ArrangementReMoveDto")
    @PostMapping("/remove")
    @MenuOperateAuthority(belongMenuCode = "002001",operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam remove(@RequestBody ArrangementReMoveDto arrangementReMoveDto) {
        return arrangementService.remove(arrangementReMoveDto);
    }

    @ApiOperation(value = "路线安排导出",tags = "路线安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dayStr",value = "时间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "tagwareHouseId",value = "仓库id",required = true,dataType = "Long")
    })
    @MenuOperateAuthority(belongMenuCode = "002001", operationType = OperationType.EXPORT)
    @GetMapping("/export")
    public synchronized void exportExcel(@RequestParam(value = "dayStr", required = true) String dayStr, @RequestParam(value = "tagwareHouseId", required = true) Long tagwareHouseId, HttpServletResponse response) throws Exception {
        arrangementService.export(dayStr, tagwareHouseId, response);
    }

}
