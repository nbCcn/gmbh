package com.guming.service.orderTemplate;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.orderTemplate.dto.TemplatesActiveDto;
import com.guming.orderTemplate.dto.TemplatesAddDto;
import com.guming.orderTemplate.dto.TemplatesUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesQuery;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.vo.TemplatesVo;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
public interface TemplatesService extends BaseService {
    ResponseParam add(TemplatesAddDto templatesAddDto);

    ResponseParam update(TemplatesUpdateDto templatesUpdateDto);

    ResponseParam<TemplatesVo> findById(Long id);

    ResponseParam delete(List<Long> ids);

    ResponseParam<List<TemplatesVo>> findByPage(TemplatesQuery templatesQuery);

    /**
     * 更新模板是否有效
     * @param templatesActiveDtoList
     * @return
     */
    ResponseParam updateActive(List<TemplatesActiveDto> templatesActiveDtoList);

    /**
     * 查詢線路下的籌備(與否)的所有模板
     * @param id                線路id
     * @param isPreparation     是否籌備模板
     * @return
     */
    List<Templates> findByTagline(Long id, Boolean isPreparation);

    /**
     * 查询店铺下的模板
     * @param shopId    店铺id
     * @return
     */
    ResponseParam<List<TemplatesVo>> findByShop(Long shopId);

}
