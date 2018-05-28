package com.guming.client.controller.order;

import com.guming.base.BaseController;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.OrderDto;
import com.guming.order.dto.OrderStatusDto;
import com.guming.order.dto.RecopientDto;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.vo.OrderVo;
import com.guming.service.order.OrderService;
import com.guming.shops.vo.ShopVo;
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
 * @Date: 2018/4/25
 */
@Api(description = "客户端订单接口",tags = "客户端订单接口")
@Controller
@RequestMapping("client/order")
public class OrderClientController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Override
    public BaseService getService() {
        return this.orderService;
    }

    @ApiOperation(value = "通过当前商铺id获取它可获得的收货商店数据")
    @ApiImplicitParam(name = "recopientDto",defaultValue = "当前商店id",required = true,dataType = "RecopientDto")
    @PostMapping("getRecipientShop")
    @ResponseBody
    public ResponseParam<List<ShopVo>> getRecipientShop(@RequestBody RecopientDto recopientDto){
        recopientDto.setUserId(getCurrentClientUser().getId());
        return orderService.getRecipientShop(recopientDto);
    }

    @ApiOperation(value = "提交订单")
    @ApiImplicitParam(name = "orderStatusDto",required = true,dataType = "OrderStatusDto")
    @PostMapping("orderSubmit")
    @ResponseBody
    public ResponseParam orderSubmit(@RequestBody OrderStatusDto orderStatusDto){
        return orderService.orderSubmit(orderStatusDto.getId(),orderStatusDto.getSendShopId(),getCurrentClientUser());
    }

    @ApiOperation(value = "检测该模板是否在送货时间内和是否在此期间有已提交订单(该方法会生成初始的购物车数据)")
    @ApiImplicitParam(name = "orderDto",required = true,dataType = "OrderDto")
    @PostMapping("checkCart")
    @ResponseBody
    public ResponseParam<Long> checkCart(@RequestBody OrderDto orderDto){
        return orderService.checkCart(orderDto);
    }


    @ApiOperation(value = "撤销订单")
    @ApiImplicitParam(name = "idDto",value = "订单id",required = true,dataType = "IdDto")
    @PostMapping("revokeOrder")
    @ResponseBody
    public ResponseParam revokeOrder(@RequestBody IdDto idDto){
        return orderService.revokeOrder(idDto.getId());
    }

    @ApiOperation(value = "查询用户下的订单")
    @ApiImplicitParam(name = "orderQuery",value = "分页查询条件只有订单状态有效",required = true,dataType = "OrderQuery")
    @PostMapping("findUserOrderByPage")
    @ResponseBody
    public ResponseParam<List<OrderVo>> findUserOrderByPage(@RequestBody OrderQuery orderQuery){
        return orderService.findByPage(orderQuery);
    }

    @ApiOperation(value = "通过id查询订单详情")
    @ApiImplicitParam(name = "orderStatusDto",value="{id,status}",required = true,dataType = "OrderStatusDto")
    @PostMapping("findById")
    @ResponseBody
    public ResponseParam<OrderVo> findById(@RequestBody OrderStatusDto orderStatusDto){
        return ResponseParam.success(orderService.findById(orderStatusDto.getId(),orderStatusDto.getStatus()));
    }

    @ApiOperation(value = "获取订单状态",tags = {"客戶端下拉框"})
    @PostMapping("getOrderStatusList")
    @ResponseBody
    public ResponseParam<List<MapVo>> getOrderStatusList(){
        return ResponseParam.success(orderService.getOrderStatusMapVoList());
    }


}
