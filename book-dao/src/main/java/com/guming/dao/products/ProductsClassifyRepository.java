package com.guming.dao.products;

import com.guming.common.base.repository.BaseRepository;
import com.guming.products.entity.ProductsClassify;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
public interface ProductsClassifyRepository extends BaseRepository<ProductsClassify,Long> {

    /**
     * 通过id组删除商品分类
     * @param ids
     */
    @Modifying
    @Query("delete from ProductsClassify p where p.id in ?1")
    void deleteProductsClassifiesByIds(List<Long> ids);

    /**
     * 排序获取所有的商品分类
     * @return
     */
    @Query("select p from ProductsClassify p order by p.order desc ,p.id desc ")
    List<ProductsClassify> findProductsClassifyList();
}
