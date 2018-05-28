package com.guming.admin.controller.order;

import com.guming.base.BaseController;
import com.guming.common.base.dto.IdsDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.order.dto.OrderTemplateAddDto;
import com.guming.order.dto.OrderTemplateProductAddDto;
import com.guming.order.dto.OrderTemplateUpdateDto;
import com.guming.service.order.OrderTemplateService;
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
 * @Date: 2018/4/26
 */
@Api(description = "订单商品模板",tags = "订单商品模板")
@Controller
@RequestMapping("orderTemplate")
public class OrderTemplateController extends BaseController {

    @Autowired
    private OrderTemplateService orderTemplateService;

    @ApiOperation(value = "根据关联id更改商品订单拥有的数量")
    @ApiImplicitParam(name = "orderTemplateUpdateDto",value = "{id,amount}",required = true,dataType = "OrderTemplateUpdateDto")
    @PostMapping("updateAmountById")
    @ResponseBody
    public ResponseParam updateAmountById(@RequestBody OrderTemplateUpdateDto orderTemplateUpdateDto){
        return orderTemplateService.updateAmountById(orderTemplateUpdateDto.getId(),orderTemplateUpdateDto.getAmount(),orderTemplateUpdateDto.getOrderId());
    }

    @ApiOperation(value = "根据id删除关联数据（仅用于已提交模板下）")
    @ApiImplicitParam(name = "idsDto",value = "id组",required = true,dataType = "IdsDto")
    @PostMapping("delete")
    @ResponseBody
    public ResponseParam delete(@RequestBody IdsDto idsDto){
        orderTemplateService.updateIsValid(idsDto.getIds(),false);
        return getSuccessDeleteResult();
    }

    @ApiOperation(value = "根据关联数据id撤销删除（仅用于已提交模板下）")
    @ApiImplicitParam(name = "idsDto",value = "id组",required = true,dataType = "IdsDto")
    @PostMapping("revokeDelete")
    @ResponseBody
    public ResponseParam revokeDelete(@RequestBody IdsDto idsDto){
        orderTemplateService.updateIsValid(idsDto.getIds(),true);
        return getSuccessDeleteResult();
    }

    @ApiOperation(value = "添加关联商品")
    @ApiImplicitParam(name = "orderTemplateAddDto",required = true,dataType = "OrderTemplateAddDto")
    @PostMapping("add")
    @ResponseBody
    public ResponseParam add(@RequestBody OrderTemplateAddDto orderTemplateAddDto){
        return orderTemplateService.add(orderTemplateAddDto);
    }

    @ApiOperation(value = "添加关联商品")
    @ApiImplicitParam(name = "orderTemplateProductAddDto",required = true,dataType = "OrderTemplateProductAddDto")
    @PostMapping("addOrderProducts")
    @ResponseBody
    public ResponseParam addOrderProducts(@RequestBody OrderTemplateProductAddDto orderTemplateProductAddDto){
         orderTemplateService.addOrderProducts(orderTemplateProductAddDto);
         return getSuccessAddResult();
    }

    @Override
    public BaseService getService() {
        return orderTemplateService;
    }
}
