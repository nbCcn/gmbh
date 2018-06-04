package com.guming.client.controller.order;

import com.guming.common.base.dto.IdDto;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.OrderCartUpdateDto;
import com.guming.order.dto.OrderDto;
import com.guming.order.vo.OrderVo;
import com.guming.service.order.ShoppingCartService;
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
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
@Api(description = "客户端购物车接口",tags = "客户端购物车接口")
@Controller
@RequestMapping("client/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation(value = "获取商店模板下的获取购物车")
    @ApiImplicitParam(name = "orderDto",required = true,dataType = "OrderDto")
    @PostMapping("getOrderCart")
    @ResponseBody
    public ResponseParam<OrderVo> getOrderCart(@RequestBody OrderDto orderDto){
        return shoppingCartService.getOrderCart(orderDto.getShopId(),orderDto.getTempId());
    }

    @ApiOperation(value = "更新购物车商品数据")
    @ApiImplicitParam(name = "orderCartUpdateDto",required = true,dataType = "OrderCartUpdateDto")
    @PostMapping("updateCartProductAmount")
    @ResponseBody
    public ResponseParam<Long> updateCartProductAmount(@RequestBody OrderCartUpdateDto orderCartUpdateDto){
        return ResponseParam.success(shoppingCartService.updateCartProductAmount(orderCartUpdateDto.getCartId(),orderCartUpdateDto.getProductId(),orderCartUpdateDto.getAmount()));
    }

    @ApiOperation(value = "删除购物车中的商品")
    @ApiImplicitParam(name = "idsDto",value = "关联数据id组",required = true,dataType = "IdsDto")
    @PostMapping("deleteCartProducts")
    @ResponseBody
    public ResponseParam deleteCartProducts(@RequestBody IdsDto idsDto){
        return shoppingCartService.deleteCartProducts(idsDto.getIds());
    }

    @ApiOperation(value = "删除购物车中的商品")
    @ApiImplicitParam(name = "idDto",value = "购物车id",required = true,dataType = "IdDto")
    @PostMapping("findCartProductAmount")
    @ResponseBody
    public ResponseParam<Long> findCartProductAmount(@RequestBody IdDto idDto){
        return ResponseParam.success(shoppingCartService.findCartProductAmount(idDto.getId()));
    }

}
