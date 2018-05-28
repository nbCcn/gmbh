package com.guming.service.orderTemplate.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.dao.orderTemplate.TemplatesTypeRepository;
import com.guming.orderTemplate.dto.TemplatesTypeAddDto;
import com.guming.orderTemplate.dto.TemplatesTypeUpdateDto;
import com.guming.orderTemplate.dto.query.TemplatesTypeQuery;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.orderTemplate.vo.TemplatesTypeVo;
import com.guming.service.orderTemplate.TemplatesTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Service
public class TemplatesTypeServiceImpl extends BaseServiceImpl implements TemplatesTypeService {

    @Autowired
    private TemplatesTypeRepository templatesTypeRepository;

    @Override
    protected BaseRepository getRepository() {
        return templatesTypeRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam add(TemplatesTypeAddDto templatesTypeAddDto) {
        if (templatesTypeAddDto.getName() == null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_TYPE_NAME_EMPTY);
        }

        TemplatesType templatesType = new TemplatesType();
        BeanUtils.copyProperties(templatesTypeAddDto,templatesType,"id");
        templatesTypeRepository.save(templatesType);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam update( TemplatesTypeUpdateDto templatesTypeUpdateDto) {
        if (templatesTypeUpdateDto.getId() ==null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        if (StringUtils.isEmpty(templatesTypeUpdateDto.getName())){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TEMPLATES_TYPE_NAME_EMPTY);
        }
        TemplatesType templatesType = templatesTypeRepository.findOne(templatesTypeUpdateDto.getId());
        if (templatesType ==null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_OBJECT_NOT_EXISTS);
        }

        BeanUtils.copyProperties(templatesTypeUpdateDto,templatesType,"id");
        templatesTypeRepository.save(templatesType);
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<TemplatesTypeVo> findById(Long id) {
        if (id == null){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        TemplatesType templatesType = templatesTypeRepository.findOne(id);
        TemplatesTypeVo templatesTypeVo = new TemplatesTypeVo();
        BeanUtils.copyProperties(templatesType,templatesTypeVo);
        return ResponseParam.success(templatesTypeVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if (ids==null || ids.isEmpty()){
            throw  new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ID_EMPTY);
        }
        List<TemplatesType> templatesTypeList = templatesTypeRepository.findAll(ids);

        if (templatesTypeList != null && !templatesTypeList.isEmpty()) {
            templatesTypeRepository.delete(templatesTypeList);
        }
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<TemplatesTypeVo>> findByPage(TemplatesTypeQuery templatesTypeQuery) {
        Specification<TemplatesType> specification = new Specification<TemplatesType>() {
            @Override
            public Predicate toPredicate(Root<TemplatesType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(templatesTypeQuery.getName())){
                    Predicate query = criteriaBuilder.like(root.get("name").as(String.class),"%" + templatesTypeQuery.getName()+"%");
                    predicates.add(query);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Pageable pageable = new PageRequest(templatesTypeQuery.getPage(),templatesTypeQuery.getPageSize());
        Page<TemplatesType> pageResult=templatesTypeRepository.findAll(specification,pageable);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());
        List<TemplatesTypeVo> result = CovertUtil.copyList(pageResult.getContent(), TemplatesTypeVo.class);
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<MapVo>> findTemplatesTypeList() {
        List<TemplatesType> templatesTypeList = templatesTypeRepository.findAll();
        List<MapVo> mapVoList = new ArrayList<MapVo>();
        if (templatesTypeList != null && !templatesTypeList.isEmpty()){
            for (TemplatesType templatesType:templatesTypeList) {
                MapVo mapVo = new MapVo();
                BeanUtils.copyProperties(templatesType,mapVo);
                mapVoList.add(mapVo);
            }
        }
        return ResponseParam.success(mapVoList);
    }


}
