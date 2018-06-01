package com.guming.dao.arrangement;

import com.guming.arrangement.entity.PlansArrangement;
import com.guming.common.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 11:26
 */
public interface ArrangementRepository extends BaseRepository<PlansArrangement, Long> {

    @Query(value = "SELECT pp.id as pathId,tableId.id as arrangementId,st.name,tableId.day " +
            "FROM " +
            "(SELECT id,day FROM sys_plans_arrangement WHERE YEAR(day) = ?1 AND MONTH(day) = ?2 ) AS tableId," +
            "sys_plans_arrangement_paths ap ," +
            "sys_plans_path pp, " +
            "sys_setups_tagline st, " +
            "sys_setups_tagwarehouse sh " +
            "WHERE " +
            "tableId.id = ap.arrangement_id and pp.id = ap.path_id and st.id = pp.tag_line_id and sh.id = ?3 and sh.id = st.tag_warehouse_id order by st.order_code asc ,st.id asc ", nativeQuery = true)
    List<Object[]> find(Integer year, Integer month, Long tagwareHouseId);

    @Query(value = "DELETE FROM sys_plans_arrangement_paths WHERE arrangement_id = ?1 and  path_id = ?2 ", nativeQuery = true)
    @Modifying
    void deleteById(Long arrangementId, Long pathId);

    @Query(value = "SELECT b.* from (SELECT id FROM `sys_plans_arrangement` " +
            "WHERE YEAR(`day`) = ?1 AND MONTH(day) = ?2 and DAY(`day`) between ?3 and ?4 ) a , sys_plans_arrangement_paths b " +
            "WHERE a.id = b.arrangement_id AND b.path_id = ?5 ",
            nativeQuery = true)
    List<Object> findPathCount(Integer year, Integer month, Integer startDay, Integer lastDay, Long pathId);


    @Query(value = "SELECT pa.day,st.name,st.manager,st.phone FROM " +
            "sys_plans_pathshop ss, " +
            "sys_plans_arrangement pa, " +
            "sys_plans_arrangement_paths pap , " +
            "sys_plans_path pp, " +
            "sys_setups_tagline st, " +
            "sys_setups_tagwarehouse sth ," +
            "(SELECT tagwarehouse_id as id from sys_shops_shop_tag_warehouses WHERE shop_id = ?3) as tb1 " +
            "WHERE " +
            "pa.id = pap.arrangement_id and pap.path_id = pp.id and pp.tag_line_id = st.id and  st.tag_warehouse_id = sth.id and tb1.id = sth.id and ss.shop_id =?3 and ss.path_id = pp.id and  " +
            "YEAR(pa.day) = ?1 and MONTH(pa.day) = ?2 And ss.shop_id = ?3 order by pa.day asc ", nativeQuery = true)
    List<Object[]> findByShop(String yearStr, String monthStr, Long shopId);

    @Query(value = "select p from PlansArrangement p ,TagwareHouse t where p.day between ?1 and ?2 and t.id = ?3 ")
    List<PlansArrangement> findByDay(Date firstDay, Date lastDay, Long tagwareHouseId);


    @Query(value = "select a.* from sys_plans_arrangement a,sys_plans_path b,sys_plans_arrangement_paths c,sys_plans_pathshop d " +
            "where c.arrangement_id=a.id and c.path_id=b.id and d.path_id=b.id and d.shop_id=?1 and a.day>=?2 and a.day<=?3 order by a.day",
            nativeQuery = true)
    List<PlansArrangement> findAllByShopIdAndDay(Long shopId, Date startDay, Date endDay);

}
