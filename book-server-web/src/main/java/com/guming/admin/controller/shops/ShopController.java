package com.guming.admin.controller.shops;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.service.shops.ShopService;
import com.guming.shops.dto.ShopAddDto;
import com.guming.shops.dto.ShopUpdateDto;
import com.guming.shops.dto.query.ShopQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:店铺入口
 * @Date: 2018/4/12 14:51
 */
@Api(description = "店铺模块",tags = "店铺模块")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "查询店铺")
    @ApiImplicitParam(name = "shopDto", required = true, dataType = "ShopQuery")
    @PostMapping("/find")
    @MenuOperateAuthority(belongMenuCode = "005001", operationType = OperationType.LOOK)
    public @ResponseBody
    ResponseParam<?> findShop(@RequestBody ShopQuery shopDto) {
        return shopService.findShops(shopDto);
    }

    @ApiOperation(value = "新增店铺")
    @ApiImplicitParam(name = "addDto", required = true, dataType = "ShopAddDto")
    @PostMapping("/add")
    @MenuOperateAuthority(belongMenuCode = "005001", operationType = OperationType.ADD)
    public @ResponseBody
    ResponseParam<?> addShop(@RequestBody ShopAddDto addDto) {
        return shopService.addShop(addDto);
    }

    @ApiOperation(value = "删除店铺")
    @ApiImplicitParam(name = "idsDto", value = "店铺Id组", required = true, dataType = "IdsDto")
    @PostMapping("/delete")
    @MenuOperateAuthority(belongMenuCode = "005001", operationType = OperationType.DELETE)
    public @ResponseBody
    ResponseParam<?> deleteShop(@RequestBody IdsDto idsDto) {
        return shopService.deleteShop(idsDto.getIds());
    }

    @ApiOperation(value = "修改店铺")
    @ApiImplicitParam(name = "updateDto", required = true, dataType = "ShopUpdateDto")
    @PostMapping("/update")
    @MenuOperateAuthority(belongMenuCode = "005001", operationType = OperationType.EDIT)
    public @ResponseBody
    ResponseParam<?> updateShop(@RequestBody ShopUpdateDto updateDto) {
        return shopService.updateShop(updateDto);
    }

    @ApiOperation(value = "根据Id查询店铺")
    @ApiImplicitParam(name = "idDto", value = "店铺Id", required = true, dataType = "IdDto")
    @PostMapping("/edit")
    @MenuOperateAuthority(belongMenuCode = "005001", operationType = OperationType.LOOK)
    public @ResponseBody
    ResponseParam<?> editShop(@RequestBody IdDto idDto) {
        return shopService.findById(idDto.getId());
    }

    @ApiOperation(value = "获取店铺状态下拉框")
    @PostMapping("/getShopStatusList")
    @ResponseBody
    public ResponseParam<List<MapVo>> getShopStatusList() {
        return shopService.getShopStatusList();
    }

    @ApiOperation(value = "获取店铺等级下拉框")
    @PostMapping("/getShopTagrankList")
    @ResponseBody
    public ResponseParam<List<MapVo>> getShopTagrankList() {
        return shopService.getShopTagrankList();
    }

    @ApiOperation(value = "获取员工角色下拉框")
    @PostMapping("/getShopUserList")
    @ResponseBody
    public ResponseParam<List<MapVo>> getShopUserList() {
        return shopService.getShopUserList();
    }

    @ApiOperation(value = "店铺导入")
    @ApiImplicitParam(name = "uploadFile", required = true, dataType = "MultipartFile")
    @MenuOperateAuthority(belongMenuCode = "005001",operationType = OperationType.IMPORT)
    @PostMapping("/import")
    public @ResponseBody
    ResponseParam importShop(MultipartFile uploadFile) {
        return shopService.importShop(uploadFile);
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "idDto", value = "用户Id", required = true, dataType = "IdDto")
    @MenuOperateAuthority(belongMenuCode = "005001",operationType = OperationType.EDIT)
    @PostMapping("/resetPass")
    public @ResponseBody
    ResponseParam resetPass(@RequestBody IdDto idDto) {
        return shopService.resetPass(idDto);
    }

}
