package com.guming.client.controller.arrangement;


import com.guming.arrangement.dto.ShopArrangementQueryDto;
import com.guming.common.base.vo.ResponseParam;
import com.guming.service.arrangement.ArrangementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 10:10
 */
@Api(description = "客户端路线安排接口", tags = "客户端路线安排接口")
@RestController
@RequestMapping("client/arrangement")
public class ArrangementClientController {

    @Autowired
    private ArrangementService arrangementService;

    @ApiOperation(value = "根据店铺查询送货安排")
    @ApiImplicitParam(name = "shopArrangementQueryDto", required = true, dataType = "ShopArrangementQueryDto")
    @PostMapping("/findByShop")
    public @ResponseBody
    ResponseParam remove(@RequestBody ShopArrangementQueryDto shopArrangementQueryDto) {
        return arrangementService.findByShop(shopArrangementQueryDto);
    }


}
