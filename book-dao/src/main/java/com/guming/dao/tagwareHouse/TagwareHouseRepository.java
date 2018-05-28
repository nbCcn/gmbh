package com.guming.dao.tagwareHouse;

import com.guming.common.base.repository.BaseRepository;
import com.guming.tagwareHouse.entity.TagwareHouse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/11 17:09
 */
@Repository
public interface TagwareHouseRepository extends BaseRepository<TagwareHouse, Long> {
    TagwareHouse findByName(String tagwareHouse);

    TagwareHouse findById(Long id);

    Long countByName(String name);

    @Query("delete from TagwareHouse t where  t.id = ?1")
    @Modifying
    void deleteByIds(List<Long> ids);
}
