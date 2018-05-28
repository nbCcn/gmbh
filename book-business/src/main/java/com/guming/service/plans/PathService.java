package com.guming.service.plans;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.plans.dto.PathAddDto;
import com.guming.plans.dto.query.PathQuery;
import com.guming.plans.dto.query.ShopFuzzyQuery;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 09:40
 */
public interface PathService extends BaseService {


    ResponseParam findShop(PathQuery pathQuery);

    ResponseParam findLine(PathQuery pathQuery);

    ResponseParam<?> add(PathAddDto pathAddDto);

    ResponseParam fuzzyFind(ShopFuzzyQuery shopFuzzyQuery);

    ResponseParam findShopbyName(ShopFuzzyQuery shopFuzzyQuery);

    void export(Long tagwareHouseId, HttpServletResponse response);
}
