package com.guming.dao.tagline;

import com.guming.common.base.repository.BaseRepository;
import com.guming.tagline.entity.TagLine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 10:53
 */
public interface TagLineRepository extends BaseRepository<TagLine, Long> {

    @Query("select t from TagLine t where t.tagWarehouseId =?1 order by t.orderCode asc,t.id asc ")
    List<TagLine> findByTagwareHouseId(Long id);

    @Query("select t from TagLine t where t.tagWarehouseId in ?1")
    List<TagLine> findByTagwareHouseIds(List<Long> tagwareHouseIds);

    @Query("delete from TagLine t where t.id in ?1")
    @Modifying
    void deleteByIds(List<Long> ids);

    Long countAllByName(String name);
}
