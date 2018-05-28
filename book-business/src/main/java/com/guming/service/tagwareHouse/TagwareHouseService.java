package com.guming.service.tagwareHouse;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.tagwareHouse.dto.TagwareHouseAddDto;
import com.guming.tagwareHouse.dto.TagwareHouseUpdateDto;
import com.guming.tagwareHouse.dto.query.TagwareHouseQuery;

import java.util.List;


/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/11 11:35
 */
public interface TagwareHouseService extends BaseService {

    ResponseParam<?> find(TagwareHouseQuery tagwareHouseDto);

    /**
     * 获取下拉框相关参数
     *
     * @return
     */
    List<MapVo> getWarehouseList();

    ResponseParam<?> add(TagwareHouseAddDto tagwareHouseAddDto);

    ResponseParam<?> update(TagwareHouseUpdateDto tagwareHouseUpdateDto);

    ResponseParam<?> delete(List<Long> ids);

    ResponseParam<?> findById(Long id);
}
