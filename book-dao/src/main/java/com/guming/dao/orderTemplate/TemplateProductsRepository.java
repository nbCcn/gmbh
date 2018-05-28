package com.guming.dao.orderTemplate;


import com.guming.common.base.repository.BaseRepository;
import com.guming.orderTemplate.entity.TemplateProducts;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/23
 */
public interface TemplateProductsRepository extends BaseRepository<TemplateProducts,Long> {

    void deleteAllByProductId(Long id);

    List<TemplateProducts> findAllByProductId(Long id);

    List<TemplateProducts> findAllByTemplateId(Long id);

    List<TemplateProducts> findAllByProductIdAndTemplateId(Long id, Long templateId);
}
