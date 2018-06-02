package com.guming.dao.products;
import com.guming.common.base.repository.BaseRepository;
import com.guming.products.entity.Products;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/18
 */
public interface ProductsRepository extends BaseRepository<Products,Long> {

    @Query(value = "select p from sys_products p " +
            "LEFT JOIN sys_products_tagwarehouse pw ON p.id = pw.product_id " +
            "LEFT JOIN sys_products_tagline pl on pl.product_id=p.id " +
            "where p.name like %?1% and p.classify_id =?2 and p.is_up =?3 and pw.tagwarehouse_id in ?4 and pl.tagline_id in ?5 " +
            "group by p.id order by p.sort limit ?6,?7",
            nativeQuery = true)
    List<Products> findAllForQueryByPage(String name, Long classifyId, Boolean isUp, List<Long> warehouseIds, List<Long> tagLineIds, Integer page, Integer pageSize);

    @Query(value = "select count (1) from (select p from sys_products p LEFT JOIN sys_products_tagwarehouse pw ON p.id = pw.product_id " +
            " LEFT JOIN sys_products_tagline pl on pl.product_id=p.id " +
            "where p.name like %?1% and p.classify_id =?2 and p.is_up =?3 and pw.tagwarehouse_id in ?4 and pl.tagline_id in ?5  group by p.id)",
            nativeQuery = true)
    Long countAllForQueryByPage(String name, Long classifyId, Boolean isUp, List<Long> warehouseIds, List<Long> tagLineIds);

    /**
     * 查找对应仓库下未过滤线路的商品
     * @param tagwarehouseId
     * @return
     */
    @Query(value = "select p.* from sys_products p LEFT JOIN sys_products_tagwarehouse pw on p.id = pw.product_id " +
            "where p.need_tag_lines != 1 and pw.tagwarehouse_id=?1",
    nativeQuery = true)
    List<Products> findWareHouseProductWithOutFilterLine(Long tagwarehouseId);
}
