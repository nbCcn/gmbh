package com.guming.service.products;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.products.dto.ProductsClassifyAddDto;
import com.guming.products.dto.ProductsClassifyUpdateDto;
import com.guming.products.dto.query.ProductsClassifyQuery;
import com.guming.products.vo.ProductsClassifyVo;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
public interface ProductsClassifyService extends BaseService {
    ResponseParam addProductsClassify(ProductsClassifyAddDto productsClassifyAddDto);

    ResponseParam updateProductsClassify(ProductsClassifyUpdateDto productsClassifyUpdateDto);

    ResponseParam deleteProductsClassify(List<Long> ids);

    ResponseParam<List<ProductsClassifyVo>> findPage(ProductsClassifyQuery productsClassifyQuery);

    ResponseParam findById(Long id);

    ResponseParam findAll();

    /**
     * 获取用于下拉框的参数
     * @return
     */
    List<MapVo> getProductsClassifyList();
}
