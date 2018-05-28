package com.guming.dao.products;

import com.guming.common.base.repository.BaseRepository;
import com.guming.products.entity.ProductsHistory;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/19
 */
public interface ProductsHistoryRepository extends BaseRepository<ProductsHistory,Long> {

    List<ProductsHistory> findAllByProductId(Long productId);

    void deleteAllByProductId(Long productId);
}
