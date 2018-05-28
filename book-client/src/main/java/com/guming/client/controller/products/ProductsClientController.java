package com.guming.client.controller.products;

import com.guming.common.base.vo.ResponseParam;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.service.products.ProductsService;
import com.guming.shops.dto.ShopLineProductsQuery;
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
 * @Date: 2018/5/8
 */
@Api(description = "客户端商品模板接口",tags = "客户端商品模板接口")
@Controller
@RequestMapping("client/products")
public class ProductsClientController {

    @Autowired
    private ProductsService productsService;

    @ApiOperation(value ="查询店铺模板下的商品")
    @ApiImplicitParam(value = "不需要输入分页参数，不支持分页",name = "shopLineProductsQuery",dataType = "ShopLineProductsQuery", required = true)
    @PostMapping("findTemplateProducesByTempIdAndShopId")
    @ResponseBody
    public ResponseParam<List<TemplateProductsVo>> findTemplateProducesByTempIdAndShopId(@RequestBody ShopLineProductsQuery shopLineProductsQuery){
        return productsService.findProducesByTempIdAndShopId(shopLineProductsQuery);
    }

    @ApiOperation(value ="查询店铺模板对应路线下的商品")
    @ApiImplicitParam(dataType = "ShopLineProductsQuery",name = "shopLineProductsQuery", required = true)
    @PostMapping("findTagLineTemplateProducesByShopId")
    @ResponseBody
    public ResponseParam<List<TemplateProductsVo>> findTagLineTemplateProducesByShopId(@RequestBody ShopLineProductsQuery shopLineProductsQuery){
        return productsService.findTagLineTemplateProducesByShopId(shopLineProductsQuery);
    }
}
