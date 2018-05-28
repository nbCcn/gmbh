package com.guming.client.controller.products;

import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.service.products.ProductsClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
@Api(description = "客户端商品分类模板接口",tags = "客户端商品分类模板接口")
@Controller
@RequestMapping("client/productsClass")
public class ProductsClassClientController {

    @Autowired
    private ProductsClassifyService productsClassifyService;

    @ApiOperation(value = "查询所有的商品类型",tags = "客戶端下拉框")
    @PostMapping("findAllProductsClassify")
    @ResponseBody
    public ResponseParam<List<MapVo>> findAllProductsClassify(){
        return productsClassifyService.findAll();
    }

}
