package com.guming.dao.authority;


import com.guming.authority.entity.Menu;
import com.guming.common.base.repository.BaseRepository;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/11
 */
public interface MenuRepository extends BaseRepository<Menu,Long> {

    List<Menu> findAllByIsValidOrderByMenuOrderAsc(Boolean isValid);

}
