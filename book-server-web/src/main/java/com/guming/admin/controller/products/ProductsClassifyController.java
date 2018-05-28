package com.guming.admin.controller.products;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.products.dto.ProductsClassifyAddDto;
import com.guming.products.dto.ProductsClassifyUpdateDto;
import com.guming.products.dto.query.ProductsClassifyQuery;
import com.guming.products.vo.ProductsClassifyVo;
import com.guming.service.products.ProductsClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/16
 */
@Api(description = "商品类型",tags = "商品类型")
@RestController
@RequestMapping("productsClassify")
public class ProductsClassifyController {
    @Autowired
    private ProductsClassifyService productsClassifyService;

    @ApiOperation(value = "添加商品类型")
    @ApiImplicitParam(name = "productsClassifyAddDto",required = true,dataType = "ProductsClassifyAddDto")
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003002",operationType = OperationType.ADD)
    public ResponseParam addProductsClassify(@RequestBody ProductsClassifyAddDto productsClassifyAddDto){
        return productsClassifyService.addProductsClassify(productsClassifyAddDto);
    }

    @ApiOperation(value = "更新商品类型")
    @ApiImplicitParam(name = "productsClassifyUpdateDto",required = true,dataType = "ProductsClassifyUpdateDto")
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003002",operationType = OperationType.EDIT)
    public ResponseParam updateProductsClassify(@RequestBody ProductsClassifyUpdateDto productsClassifyUpdateDto){
        return productsClassifyService.updateProductsClassify(productsClassifyUpdateDto);
    }

    @ApiOperation(value = "删除商品类型")
    @ApiImplicitParam(name = "idsDto",value = "商品类型id组",required = true,dataType = "IdsDto")
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003002",operationType = OperationType.DELETE)
    public ResponseParam deleteProductsClassify(@RequestBody IdsDto idsDto){
        return productsClassifyService.deleteProductsClassify(idsDto.getIds());
    }

    @ApiOperation(value = "分页查询商品类型")
    @ApiImplicitParam(name = "productsClassifyQuery",required = true,dataType = "ProductsClassifyQuery")
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003002",operationType = OperationType.LOOK)
    public ResponseParam<List<ProductsClassifyVo>> findProductsClassify(@RequestBody ProductsClassifyQuery productsClassifyQuery){
        return productsClassifyService.findPage(productsClassifyQuery);
    }


    @ApiOperation(value = "通过商品类型id查询")
    @ApiImplicitParam(name = "idDto",value = "商品类型id实体",required = true,dataType = "IdDto")
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003002",operationType = OperationType.LOOK)
    public ResponseParam<ProductsClassifyVo> editProductsClassify(@RequestBody IdDto idDto){
        return productsClassifyService.findById(idDto.getId());
    }

    @ApiOperation(value = "查询所有的商品类型（用于下拉框）")
    @PostMapping("findAllProductsClassify")
    @ResponseBody
    public ResponseParam<List<MapVo>> findAllProductsClassify(){
        return productsClassifyService.findAll();
    }
}
