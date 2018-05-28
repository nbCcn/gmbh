package com.guming.admin.controller.order;

import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.business.OperationType;
import com.guming.kingdee.response.InventoryProductResponseParam;
import com.guming.order.dto.OrderStatusDto;
import com.guming.order.dto.query.OrderAuditQuery;
import com.guming.order.dto.query.OrderQuery;
import com.guming.order.vo.OrderAuditingVo;
import com.guming.order.vo.OrderVo;
import com.guming.service.order.OrderService;
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
@Api(description = "订单模块",tags = "订单模块")
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "分页查询订单")
    @ApiImplicitParam(name = "orderQuery",value = "分页查询实体",required = true,dataType = "OrderQuery")
    @PostMapping("find")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "001",operationType = OperationType.LOOK)
    public ResponseParam<List<OrderVo>> findByPage(@RequestBody OrderQuery orderQuery){
        return orderService.findByPage(orderQuery);
    }

    @ApiOperation(value = "通过id查询")
    @ApiImplicitParam(name = "orderStatusDto",value="{id,status}",required = true,dataType = "OrderStatusDto")
    @PostMapping("findById")
    @ResponseBody
    @MenuOperateAuthority(belongMenuCode = "001",operationType = OperationType.LOOK)
    public ResponseParam<OrderVo> findById(@RequestBody OrderStatusDto orderStatusDto){
        return ResponseParam.success(orderService.findById(orderStatusDto.getId(),orderStatusDto.getStatus()));
    }

    @ApiOperation(value = "审核订单")
    @ApiImplicitParam(name = "idDto",required = true,defaultValue = "订单id",dataType = "IdDto")
    @PostMapping("orderAudit")
    @ResponseBody
    public ResponseParam<OrderAuditingVo> orderAudit(@RequestBody OrderAuditQuery orderAuditQuery){
        return orderService.orderAudit(orderAuditQuery);
    }

    @ApiOperation(value = "订单发货")
    @ApiImplicitParam(name = "idDto",required = true,defaultValue = "订单id",dataType = "IdDto")
    @PostMapping("orderDelivery")
    @ResponseBody
    public ResponseParam orderDelivery(@RequestBody IdDto idDto){
        return orderService.orderDelivery(idDto.getId());
    }

    @ApiOperation(value = "订单完成")
    @ApiImplicitParam(name = "idDto",required = true,defaultValue = "订单id",dataType = "IdDto")
    @PostMapping("orderFinish")
    @ResponseBody
    public ResponseParam orderFinish(@RequestBody IdDto idDto){
        return orderService.orderFinish(idDto.getId());
    }

    @ApiOperation(value = "获取订单状态下拉框")
    @PostMapping("getOrderStatusList")
    @ResponseBody
    public ResponseParam<List<MapVo>> getOrderStatusList(){
        return orderService.getOrderStatusList();
    }

    @ApiOperation(value = "检测订单库存")
    @ApiImplicitParam(name = "idDto",required = true,defaultValue = "订单id",dataType = "IdDto")
    @PostMapping("checkStock")
    @ResponseBody
    public ResponseParam<List<InventoryProductResponseParam>> checkStock(@RequestBody IdDto idDto){
        return orderService.checkStock(idDto.getId());
    }

    @ApiOperation(value = "下一条订单")
    @ApiImplicitParam(name = "orderAuditQuery",required = true,dataType = "OrderAuditQuery")
    @PostMapping("nextOrder")
    @ResponseBody
    public ResponseParam<OrderVo> nextOrder(@RequestBody OrderAuditQuery orderAuditQuery){
        return ResponseParam.success(orderService.nextOrder(orderAuditQuery));
    }

}
