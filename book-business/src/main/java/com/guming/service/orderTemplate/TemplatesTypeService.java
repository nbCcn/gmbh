package com.guming.service.orderTemplate;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.orderTemplate.dto.TemplatesTypeAddDto;
import com.guming.orderTemplate.dto.TemplatesTypeUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesTypeQuery;
import com.guming.orderTemplate.vo.TemplatesTypeVo;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
public interface TemplatesTypeService extends BaseService {
    ResponseParam add(TemplatesTypeAddDto templatesTypeAddDto);

    ResponseParam update(TemplatesTypeUpdateDto templatesTypeUpdateDto);

    ResponseParam findById(Long id);

    ResponseParam delete(List<Long> ids);

    ResponseParam<List<TemplatesTypeVo>> findByPage(TemplatesTypeQuery templatesTypeQuery);

    ResponseParam<List<MapVo>> findTemplatesTypeList();
}
