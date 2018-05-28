package com.guming.service.tagline.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.CovertUtil;
import com.guming.dao.plans.PathRepository;
import com.guming.dao.tagline.TagLineRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.plans.entity.PlansPath;
import com.guming.service.tagline.TagLineService;
import com.guming.tagline.dto.TagLineAddDto;
import com.guming.tagline.dto.TagLineUpdateDto;
import com.guming.tagline.dto.query.TagLineQuery;
import com.guming.tagline.entity.TagLine;
import com.guming.tagline.vo.TagLineVo;
import com.guming.tagwareHouse.entity.TagwareHouse;
import com.guming.tagwareHouse.vo.TagwareHouseVo;
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
import java.util.Date;
import java.util.List;


/**
 * @Author: Ccn
 * @Description:
 * @Date: 2018/4/20 10:45
 */
@Service
public class TagLineServiceImpl extends BaseServiceImpl implements TagLineService {

    @Autowired
    private TagLineRepository tagLineRepository;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.tagLineRepository;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<?> find(TagLineQuery tagLineQuery) {
        Pageable pageable = new PageRequest(tagLineQuery.getPage(), tagLineQuery.getPageSize());
        Specification<TagLine> specification = new Specification<TagLine>() {
            @Override
            public Predicate toPredicate(Root<TagLine> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (tagLineQuery.getTagwareHouseId() != null) {
                    list.add(criteriaBuilder.equal(root.get("tagWarehouseId").as(Long.class), tagLineQuery.getTagwareHouseId()));
                }
                Predicate[] predicates = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderCode")), criteriaBuilder.desc(root.get("id")));
                return criteriaQuery.getRestriction();

            }
        };
        Page<TagLine> page = tagLineRepository.findAll(specification, pageable);
        List<TagLine> content = page.getContent();
        List<TagLineVo> tagLineVoList = new ArrayList<>();
        for (TagLine tagLine : content) {
            TagLineVo tagLineVo = new TagLineVo();
            TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();
            BeanUtils.copyProperties(tagLine, tagLineVo);
            BeanUtils.copyProperties(tagLine.getTagwareHouse(), tagwareHouseVo);
            tagLineVo.setTagwareHouseVo(tagwareHouseVo);
            tagLineVoList.add(tagLineVo);
        }

        Pagination pagination = new Pagination(page.getTotalElements(), page.getNumber(), page.getSize());
        return ResponseParam.success(tagLineVoList, pagination);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<?> add(TagLineAddDto tagLineAddDto) {
        if (tagLineAddDto.getName() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NAME_EMPTY);
        }
        if (tagLineAddDto.getFtype() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_FTYPE_EMPTY);
        }
        Long count = tagLineRepository.countAllByName(tagLineAddDto.getName());
        if (count >= 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NAME_EXISTS);
        }
        TagwareHouse tagwareHouse = tagwareHouseRepository.findOne(tagLineAddDto.getTagwareHouseId());
        TagLine tagLine = new TagLine();
        BeanUtils.copyProperties(tagLineAddDto, tagLine);
        tagLine.setTagwareHouse(tagwareHouse);
        tagLine.setCreatedTime(new Date());
        tagLine.setUpdatedTime(new Date());
        TagLine saveTagLine = tagLineRepository.save(tagLine);

        PlansPath path = new PlansPath();
        path.setCreatedTime(new Date());
        path.setUpdatedTime(new Date());
        path.setTagLine(saveTagLine);
        pathRepository.save(path);

        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NOT_EXISTS_DELETE);
        }
        tagLineRepository.deleteByIds(ids);
        return getSuccessDeleteResult();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam update(TagLineUpdateDto tagLineUpdateDto) {
        if (tagLineUpdateDto.getId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERR_VALIDATION_TAGLINE_CLASS_ID_EMPTY);
        }
        if (tagLineUpdateDto.getName() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NAME_EMPTY);
        }
        if (tagLineUpdateDto.getFtype() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_FTYPE_EMPTY);
        }
        TagLine tagLine = tagLineRepository.findOne(tagLineUpdateDto.getId());
        if (tagLine == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NOT_EXISTS);
        }
        BeanUtils.copyProperties(tagLineUpdateDto, tagLine);
        tagLine.setUpdatedTime(new Date());
        tagLineRepository.save(tagLine);
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional
    public ResponseParam<List<MapVo>> findByWarehouseId(Long tagwareHouseId) {
        List<TagLine> tagLineList = tagLineRepository.findByTagwareHouseId(tagwareHouseId);
        return ResponseParam.success(CovertUtil.copyList(tagLineList, MapVo.class));
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam findById(Long id) {
        if (id == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERR_VALIDATION_TAGLINE_CLASS_ID_EMPTY);
        }
        TagLine tagLine = tagLineRepository.findOne(id);
        if (tagLine == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGLINE_CLASS_NOT_EXISTS);
        }
        TagLineVo tagLineVo = new TagLineVo();
        TagwareHouse tagwareHouse = tagLine.getTagwareHouse();
        BeanUtils.copyProperties(tagLine, tagLineVo);
        TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();
        BeanUtils.copyProperties(tagwareHouse, tagwareHouseVo);
        tagLineVo.setTagwareHouseVo(tagwareHouseVo);
        return ResponseParam.success(tagLineVo);
    }
}
