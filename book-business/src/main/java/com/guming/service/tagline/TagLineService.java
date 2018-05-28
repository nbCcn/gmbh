package com.guming.service.tagline;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.tagline.dto.TagLineAddDto;
import com.guming.tagline.dto.TagLineUpdateDto;
import com.guming.tagline.dto.query.TagLineQuery;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 10:45
 */
public interface TagLineService extends BaseService {
    ResponseParam<?> find(TagLineQuery tagLineQuery);

    ResponseParam<?> add(TagLineAddDto tagLineAddDto);

    ResponseParam<?> delete(List<Long> ids);

    ResponseParam<?> update(TagLineUpdateDto tagLineUpdateDto);

    ResponseParam<List<MapVo>> findByWarehouseId(Long tagwareHouseId);

    ResponseParam<?> findById(Long id);
}
