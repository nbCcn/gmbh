package com.guming.dao.setups;

import com.guming.common.base.repository.BaseRepository;
import com.guming.setups.entity.Setups;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
public interface SetupsRepository extends BaseRepository<Setups,Long> {

    List<Setups> findAllByCodeAndIsValid(String code, Boolean isValid);

}
