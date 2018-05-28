package com.guming.dao.tagrank;

import com.guming.common.base.repository.BaseRepository;
import com.guming.tagrank.entity.TagRank;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/13 21:23
 */
public interface TagRankRepository extends BaseRepository<TagRank, Long> {
    Long countAllByName(String name);

    @Modifying
    @Query("DELETE FROM TagRank s where s.id in ?1")
    void deleteByIds(List<Long> ids);

    TagRank findByName(String tagrankStr);
}
