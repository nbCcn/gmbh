package com.guming.client.controller.shop;

import com.guming.base.BaseController;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.service.shops.ShopService;
import com.guming.shops.vo.ShopVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author peng_cheng
 */
@Api(description = "客户端店铺模块",tags = "客户端店铺模块")
@RestController
@RequestMapping("/client/shop")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Override
    public BaseService getService() {
        return this.shopService;
    }

    @ApiOperation(value = "查询当前用户关联的店铺")
    @PostMapping("/findCurrentUserShops")
    @ResponseBody
    public ResponseParam<List<ShopVo>> findCurrentUserShops(HttpServletRequest request) {
        return shopService.findCurrentUserShops(getCurrentClientUser());
    }


}
