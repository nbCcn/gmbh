package com.guming.admin.controller.plans;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.plans.dto.PathAddDto;
import com.guming.plans.dto.query.PathQuery;
import com.guming.plans.dto.query.ShopFuzzyQuery;
import com.guming.service.plans.PathService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 09:40
 */
@Api(description = "路线规划",tags = "路线规划")
@RestController
@RequestMapping("/path")
public class PathController {

    @Autowired
    private PathService pathService;


    @ApiOperation(value = "按照仓库查询路线")
    @ApiImplicitParam(name = "pathQuery", required = true, dataType = "PathQuery")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.LOOK)
    @PostMapping("/findTagLine")
    public @ResponseBody
    ResponseParam findTagLine(@RequestBody PathQuery pathQuery) {
        return pathService.findLine(pathQuery);
    }

    @ApiOperation(value = "按照路线查询店铺")
    @ApiImplicitParam(name = "pathQuery", required = true, dataType = "PathQuery")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.LOOK)
    @PostMapping("/findShop")
    public @ResponseBody
    ResponseParam findShop(@RequestBody PathQuery pathQuery) {
        return pathService.findShop(pathQuery);
    }

    @ApiOperation(value = "路线移动")
    @ApiImplicitParam(name = "pathAddDto", required = true, dataType = "PathAddDto")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.EDIT)
    @PostMapping("/move")
    public @ResponseBody
    ResponseParam<?> add(@RequestBody PathAddDto pathAddDto) {
        return pathService.add(pathAddDto);
    }

    @ApiOperation(value = "店铺模糊搜索")
    @ApiImplicitParam(name = "shopFuzzyQuery", required = true, dataType = "ShopFuzzyQuery")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.LOOK)
    @PostMapping("/fuzzyFind")
    public @ResponseBody
    ResponseParam fuzzyFind(@RequestBody ShopFuzzyQuery shopFuzzyQuery) {
        return pathService.fuzzyFind(shopFuzzyQuery);
    }

    @ApiOperation(value = "根据店铺名称搜索店铺")
    @ApiImplicitParam(name = "shopFuzzyQuery", required = true, dataType = "ShopFuzzyQuery")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.LOOK)
    @PostMapping("/findShopByName")
    public @ResponseBody
    ResponseParam findShopByName(@RequestBody ShopFuzzyQuery shopFuzzyQuery) {
        return pathService.findShopbyName(shopFuzzyQuery);
    }


    @ApiOperation(value = "路线规划导出")
    @ApiImplicitParam(name = "tagwareHouseId", required = true, dataType = "Long")
    @MenuOperateAuthority(belongMenuCode = "004003", operationType = OperationType.EXPORT)
    @GetMapping("/export")
    public synchronized void exportPath(@RequestParam(value = "tagwareHouseId", required = false) Long tagwareHouseId, HttpServletResponse response) {
        pathService.export(tagwareHouseId,response);
    }


}
