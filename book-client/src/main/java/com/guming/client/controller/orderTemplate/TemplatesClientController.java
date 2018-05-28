package com.guming.client.controller.orderTemplate;

import com.guming.common.base.dto.IdDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.orderTemplate.vo.TemplatesVo;
import com.guming.service.orderTemplate.TemplatesService;
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
 * @Date: 2018/4/20
 */
@Api(description = "客户端订单模板接口",tags = "客户端订单模板接口")
@Controller
@RequestMapping("client/templates")
public class TemplatesClientController {

    @Autowired
    private TemplatesService templatesService;

    @ApiOperation(value ="查询店铺所拥有的模板")
    @ApiImplicitParam(dataType = "IdDto",defaultValue = "店铺id",name = "idDto", required = true)
    @PostMapping("findByShop")
    @ResponseBody
    public ResponseParam<List<TemplatesVo>> findByShop(@RequestBody IdDto idDto){
        return templatesService.findByShop(idDto.getId());
    }



}
