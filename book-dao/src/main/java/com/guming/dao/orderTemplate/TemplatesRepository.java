package com.guming.dao.orderTemplate;

import com.guming.common.base.repository.BaseRepository;
import com.guming.orderTemplate.entity.Templates;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
public interface TemplatesRepository extends BaseRepository<Templates,Long> {

    @Query("select t from Templates t where t.isValid=true and t.id=?1  and t.isActive=?2")
    Templates findByIdAndIsActive(Long id, Boolean isActive);
}
