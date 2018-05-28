package com.guming.service.products;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.orderTemplate.vo.TemplateProductsVo;
import com.guming.products.dto.ProductsAddDto;
import com.guming.products.dto.ProductsUpdateDto;
import com.guming.products.dto.UndercarriageDto;
import com.guming.products.dto.query.ProductsQuery;
import com.guming.products.entity.Products;
import com.guming.shops.dto.ShopLineProductsQuery;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
public interface ProductsService extends BaseService {
    /**
     *  分頁查詢
     */
    ResponseParam findByPage(ProductsQuery productsDto);

    /**
     * 根据id查询该商品数据以及其关联的数据
     */
    ResponseParam findAllMessageById(Long id);

    /**
     * 根据id查询出常用数据
     * @param id
     * @return
     */
    ResponseParam findById(Long id);

    ResponseParam undercarriage(UndercarriageDto undercarriageDtoList);

    ResponseParam add(ProductsAddDto productsAddDto);

    ResponseParam update(ProductsUpdateDto productsUpdateDto);

    ResponseParam delete(List<Long> ids);

    /**
     * 查询店铺路线下的商品
     * @param shopLineProductsQuery
     * @return
     */
    ResponseParam<List<TemplateProductsVo>> findTagLineTemplateProducesByShopId(ShopLineProductsQuery shopLineProductsQuery);

    /**
     * 查詢出商店模板下的商品(分页)
     * @param shopLineProductsQuery
     * @return
     */
    ResponseParam<List<TemplateProductsVo>> findProducesByTempIdAndShopId(ShopLineProductsQuery shopLineProductsQuery);


    /**
     * 检测商品限购
     * @param products  变动的商品原数据
     * @param amount    变动的数量
     * @return
     */
    void checkProductsLimit(Products products, Integer amount);
}
