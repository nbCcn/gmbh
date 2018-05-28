package com.guming.admin.controller.products;

import com.guming.base.BaseController;
import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.products.dto.ProductsAddDto;
import com.guming.products.dto.ProductsUpdateDto;
import com.guming.products.dto.UndercarriageDto;
import com.guming.products.dto.query.ProductsQuery;
import com.guming.products.vo.ProductsVo;
import com.guming.service.products.ProductsService;
import com.guming.shops.dto.ShopLineProductsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
@Api(description = "商品",tags = "商品")
@RestController
@RequestMapping("products")
public class ProductsController extends BaseController {

    @Autowired
    private ProductsService productsService;

    @Override
    public BaseService getService() {
        return this.productsService;
    }

    @ApiOperation(value = "分页查询商品")
    @ApiImplicitParam(name = "productsQuery",required = true,dataType = "ProductsQuery")
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.LOOK)
    public ResponseParam<List<ProductsVo>> findByPage(@RequestBody ProductsQuery productsQuery){
        return productsService.findByPage(productsQuery);
    }

    @ApiOperation(value = "通过id查询单个商品的详情信息")
    @ApiImplicitParam(name = "idDto",value = "商品id",required = true,dataType = "IdDto")
    @PostMapping("view")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.LOOK)
    public ResponseParam findAllMessageById(@RequestBody IdDto idDto){
        return productsService.findAllMessageById(idDto.getId());
    }

    @ApiOperation(value = "通过id查询单个商品编辑信息")
    @ApiImplicitParam(name = "idDto",value = "商品id",required = true,dataType = "IdDto")
    @PostMapping("edit")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.LOOK)
    public ResponseParam<ProductsVo> findById(@RequestBody IdDto idDto){
        return productsService.findById(idDto.getId());
    }

    @ApiOperation(value = "商品上下架")
    @ApiImplicitParam(name = "undercarriageDto",required = true,dataType = "UndercarriageDto")
    @PostMapping("undercarriage")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.UNDERCARRIAGE)
    public ResponseParam undercarriage(@RequestBody UndercarriageDto undercarriageDto){
        return productsService.undercarriage(undercarriageDto);
    }

    @ApiOperation(value = "添加")
    @ApiImplicitParam(name = "productsAddDto",required = true,dataType = "ProductsAddDto")
    @PostMapping("add")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.ADD)
    public ResponseParam add(@RequestBody ProductsAddDto productsAddDto){
        return productsService.add(productsAddDto);
    }

    @ApiOperation(value = "更新")
    @ApiImplicitParam(name = "productsUpdateDto",required = true,dataType = "ProductsUpdateDto")
    @PostMapping("update")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.EDIT)
    public ResponseParam update(@RequestBody ProductsUpdateDto productsUpdateDto){
        return productsService.update(productsUpdateDto);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "idsDto",required = true,dataType = "IdsDto")
    @PostMapping("delete")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "003001",operationType = OperationType.DELETE)
    public ResponseParam delete(@RequestBody IdsDto idsDto){
        return productsService.delete(idsDto.getIds());
    }

    @ApiOperation(value ="查询店铺模板对应路线下的商品")
    @ApiImplicitParam(dataType = "ShopLineProductsQuery",name = "shopLineProductsQuery", required = true)
    @PostMapping("findTagLineTemplateProducesByShopId")
    @ResponseBody
    public ResponseParam<List<TemplateProductsVo>> findTagLineTemplateProducesByShopId(@RequestBody ShopLineProductsQuery shopLineProductsQuery){
        return productsService.findTagLineTemplateProducesByShopId(shopLineProductsQuery);
    }

    @ApiOperation(value ="导出excel")
    @ApiImplicitParam(dataType = "ProductsQuery",name = "productsQuery", required = true)
    @PostMapping("exportExcel")
    @ResponseBody
    public ResponseParam exportExcel(@RequestBody ProductsQuery productsQuery, HttpServletResponse response){
        return getSuccessExportResult();
    }
}
