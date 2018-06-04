package com.guming.dao.shops;

import com.guming.common.base.repository.BaseRepository;
import com.guming.shops.entitiy.ShopsShop;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/12 15:12
 */

public interface ShopRepository extends BaseRepository<ShopsShop, Long> {


    @Query("UPDATE ShopsShop SET isDeleted = true where id in ?1 ")
    @Modifying
    void deleteByIds(List<Long> ids);

    Long countByName(String name);

    ShopsShop findShopsShopByCode(String code);

    @Query(value = "DELETE FROM sys_plans_pathshop WHERE id = ?1 ",nativeQuery = true)
    @Modifying
    void deletePathShop(Long id);
}
