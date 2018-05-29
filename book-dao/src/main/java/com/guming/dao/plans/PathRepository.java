package com.guming.dao.plans;

import com.guming.common.base.repository.BaseRepository;
import com.guming.plans.entity.PlansPath;
import com.guming.plans.vo.PathVo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/24 13:50
 */
public interface PathRepository extends BaseRepository<PlansPath, Long> {

    @Query(value = "SELECT  " +
            "new com.guming.plans.vo.PathVo(h.id,p.id,t.id ,t.name) " +
            "FROM " +
            "PlansPath p ,TagwareHouse h ,TagLine t " +
            "WHERE " +
            "p.tagLine.id = t.id  AND t.tagwareHouse.id = h.id AND h.id = ?1 order by t.orderCode desc ,t.id desc ")
    List<PathVo> findTagLine(Long id);


    @Query(value = "SELECT " +
            "t.name as tagwareHouseName,p.id as pathId,l.name as tagLineName , s.id as shopId , s.name ,s.code, s.address ,s.contact,s.phone,s.status,s.lng,s.lat ,t.id  as tagwareHouseId,s.province,s.city,s.district " +
            "FROM " +
            "sys_shops_shop s," +
            "sys_setups_tagwarehouse t," +
            "sys_shops_shop_tag_warehouses st," +
            "sys_plans_path p," +
            "sys_setups_tagline l," +
            "sys_plans_pathshop sp " +
            "WHERE " +
            "s.name LIKE %?1% and s.id = st.shop_id and t.id = st.tagwarehouse_id and s.id = sp.shop_id and p.id = sp.path_id and l.id = p.tag_line_id and s.status != 0 ", nativeQuery = true)
    List<Object[]> findByShopName(String name);

    @Query(value = "SELECT " +
            "mes.tagwarehouseId,mes.tagwarehouseName, mes.id, mes.name, mes.address, mes.code, mes.contact,mes.phone, mes.lng, mes.lat, mes.status,mes.province,mes.city,mes.district " +
            "FROM " +
            "(SELECT " +
            "ss.id, ss.name, ss.address,ss.province,ss.city,ss.district ,ss.code, ss.contact, ss.phone, ss.lng, ss.lat, ss.status, st.id AS tagwarehouseId, st.name AS tagwarehouseName " +
            "FROM " +
            "sys_shops_shop ss, sys_setups_tagwarehouse st,sys_shops_shop_tag_warehouses sst " +
            "WHERE " +
            "ss.NAME LIKE %?1%  AND ss.STATUS != 0  AND ss.id = sst.shop_id  AND st.id = sst.tagwarehouse_id  ) mes " +
            "WHERE " +
            "mes.id " +
            "NOT IN (SELECT " +
            "sp.shop_id " +
            "FROM " +
            "sys_shops_shop s, sys_setups_tagwarehouse t, sys_shops_shop_tag_warehouses st, sys_plans_path p, sys_setups_tagline l, sys_plans_pathshop sp " +
            "WHERE s.id = st.shop_id AND t.id = st.tagwarehouse_id  AND s.id = sp.shop_id  AND p.id = sp.path_id  AND l.id = p.tag_line_id AND s.NAME LIKE %?2%)", nativeQuery = true)
    List<Object[]> findNoLineShopByName(String fName, String sName);


    @Query(value = "SELECT idTable.id " +
            "FROM " +
            "(SELECT ss.id,ss.name " +
            "FROM sys_shops_shop ss,sys_setups_tagwarehouse st,sys_shops_shop_tag_warehouses sst " +
            "WHERE " +
            "ss.id = sst.shop_id AND st.id = sst.tagwarehouse_id AND st.id = ?1 AND ss.status != ?2) as idTable " +
            "WHERE " +
            "idTable.id NOT IN (SELECT " +
            "sh.id " +
            "FROM " +
            "sys_plans_pathshop ps,sys_shops_shop sh,sys_plans_path pa , sys_setups_tagwarehouse th,sys_shops_shop_tag_warehouses st " +
            "WHERE ps.shop_id = sh.id AND pa.id = ps.path_id " +
            "AND st.shop_id = sh.id AND st.tagwarehouse_id = th.id " +
            "AND th.id = ?3 )", nativeQuery = true)
    List<Object> findNoTagLine(Long stId, Integer status, Long thId);


    @Query(value = "SELECT idTable.tagwareHouseName,idTable.id,idTable.name,idTable.code,idTable.address,idTable.contact,idTable.phone,idTable.status ,idTable.lng ,idTable.lat,idTable.tagwareHouseId,idTable.province,idTable.city,idTable.district " +
            "FROM " +
            "(SELECT ss.id,st.name as tagwareHouseName,st.id as tagwareHouseId,ss.name,ss.code,ss.province,ss.city,ss.district,ss.address,ss.contact,ss.phone,ss.status,ss.lng,ss.lat " +
            "FROM sys_shops_shop ss,sys_setups_tagwarehouse st,sys_shops_shop_tag_warehouses sst " +
            "WHERE " +
            "ss.id = sst.shop_id AND st.id = sst.tagwarehouse_id AND st.id = ?1 AND ss.status != ?2) as idTable " +
            "WHERE " +
            "idTable.id NOT IN (SELECT " +
            "sh.id " +
            "FROM " +
            "sys_plans_pathshop ps,sys_shops_shop sh,sys_plans_path pa , sys_setups_tagwarehouse th,sys_shops_shop_tag_warehouses st " +
            "WHERE ps.shop_id = sh.id AND pa.id = ps.path_id " +
            "AND st.shop_id = sh.id AND st.tagwarehouse_id = th.id " +
            "AND th.id = ?3 )", nativeQuery = true)
    List<Object[]> findNoTagLineShop(Long stId, Integer status, Long thId);


    @Query(value = "delete from sys_plans_pathshop where shop_id = ?1 and path_id = ?2 ", nativeQuery = true)
    @Modifying
    int removeToNoLine(Long shopId, Long pathId);


    @Query(value = "SELECT ss.id " +
            "FROM " +
            "sys_shops_shop ss " +
            "WHERE " +
            "ss.status != ?1 " +
            "and " +
            "ss.id NOT IN (SELECT " +
            "sh.id " +
            "FROM  " +
            "sys_plans_pathshop ps,sys_shops_shop sh,sys_plans_path pa " +
            "WHERE ps.shop_id = sh.id AND pa.id = ps.path_id ) ", nativeQuery = true)
    List<Object[]> findAllNoLine(Integer status);


    @Query(value = "SELECT idTable.tagwareHouseName,idTable.id,idTable.name,idTable.code,idTable.address,idTable.contact,idTable.phone,idTable.status ,idTable.lng ,idTable.lat,idTable.tagwareHouseId,idTable.province,idTable.city,idTable.district " +
            "FROM " +
            "(SELECT ss.id,st.name as tagwareHouseName,st.id as tagwareHouseId,ss.name,ss.code,ss.province,ss.city,ss.district,ss.address,ss.contact,ss.phone,ss.status,ss.lng,ss.lat " +
            "FROM sys_shops_shop ss,sys_setups_tagwarehouse st,sys_shops_shop_tag_warehouses sst " +
            "WHERE " +
            "ss.id = sst.shop_id AND st.id = sst.tagwarehouse_id  AND ss.status != ?1 ) as idTable " +
            "WHERE " +
            "idTable.id NOT IN (SELECT " +
            "sh.id " +
            "FROM " +
            "sys_plans_pathshop ps,sys_shops_shop sh,sys_plans_path pa , sys_setups_tagwarehouse th,sys_shops_shop_tag_warehouses st " +
            "WHERE ps.shop_id = sh.id AND pa.id = ps.path_id " +
            "AND st.shop_id = sh.id AND st.tagwarehouse_id = th.id )", nativeQuery = true)
    List<Object[]> findAllNoLineShop(Integer status);


    @Query(value = "SELECT  " +
            "t.name as tagwareHouseName,l.name as tagLineName , s.name ,s.code,s.province,s.city,s.district  ,s.address ,s.contact,s.phone,s.status " +
            "FROM " +
            "sys_shops_shop s," +
            "sys_setups_tagwarehouse t," +
            "sys_shops_shop_tag_warehouses st," +
            "sys_plans_path p," +
            "sys_setups_tagline l," +
            "sys_plans_pathshop sp " +
            "WHERE " +
            "s.id = st.shop_id and t.id = st.tagwarehouse_id and s.id = sp.shop_id and p.id = sp.path_id and l.id = p.tag_line_id and t.id = ?1 and s.status != 0 ", nativeQuery = true)
    List<Object[]> findAllInLineShopById(Long id);

    @Query(value = "SELECT  " +
            "t.name as tagwareHouseName,  s.name ,s.code,s.province,s.city,s.district  ,s.address ,s.contact,s.phone,s.status " +
            "FROM" +
            "(SELECT * " +
            "FROM " +
            "sys_shops_shop s " +
            "WHERE s.id not in (SELECT shop_id from sys_plans_pathshop)) s ," +
            "sys_setups_tagwarehouse t," +
            "sys_shops_shop_tag_warehouses st " +
            "WHERE " +
            "s.id = st.shop_id and t.id = st.tagwarehouse_id and t.id = ?1 and s.status != 0 ", nativeQuery = true)
    List<Object[]> findAllNotInLineShopById(Long id);


    @Query(value = "SELECT  " +
            "t.name as tagwareHouseName,l.name as tagLineName , s.name ,s.code,s.province,s.city,s.district  ,s.address ,s.contact,s.phone,s.status " +
            "FROM " +
            "sys_shops_shop s," +
            "sys_setups_tagwarehouse t," +
            "sys_shops_shop_tag_warehouses st," +
            "sys_plans_path p," +
            "sys_setups_tagline l," +
            "sys_plans_pathshop sp " +
            "WHERE " +
            "s.id = st.shop_id and t.id = st.tagwarehouse_id and s.id = sp.shop_id and p.id = sp.path_id and l.id = p.tag_line_id and s.status != 0 ", nativeQuery = true)
    List<Object[]> findAllInLineShop();

    @Query(value = "SELECT  " +
            "t.name as tagwareHouseName,  s.name ,s.code,s.province,s.city,s.district  ,s.address ,s.contact,s.phone,s.status " +
            "FROM" +
            "(SELECT * " +
            "FROM " +
            "sys_shops_shop s " +
            "WHERE s.id not in (SELECT shop_id from sys_plans_pathshop)) s ," +
            "sys_setups_tagwarehouse t," +
            "sys_shops_shop_tag_warehouses st " +
            "WHERE " +
            "s.id = st.shop_id and t.id = st.tagwarehouse_id ", nativeQuery = true)
    List<Object[]> findAllNotInLineShop();


}
