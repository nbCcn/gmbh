package com.guming.service.tagrank.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.dao.tagrank.TagRankRepository;
import com.guming.service.tagrank.TagRankService;
import com.guming.shops.entitiy.ShopsShop;
import com.guming.tagrank.dto.TagRankAddDto;
import com.guming.tagrank.dto.TagRankUpdateDto;
import com.guming.tagrank.dto.query.TagRankQuery;
import com.guming.tagrank.entity.TagRank;
import com.guming.tagrank.vo.TagRankVo;
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
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/13 21:21
 */
@Service
@SuppressWarnings("all")
public class TagRankServiceImpl extends BaseServiceImpl implements TagRankService {

    @Autowired
    private TagRankRepository tagRankRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.tagRankRepository;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseParam<?> addTagrank(TagRankAddDto tagRankAddDto) {
        if (tagRankAddDto.getName() == null || tagRankAddDto.getName().isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NAME_EMPTY);
        }
        Long count = tagRankRepository.countAllByName(tagRankAddDto.getName());
        if (count >= 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NAME_EXISTS);
        }
        if (tagRankAddDto.getIsPrepare() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_ISPREPARE_EMPTY);
        }
        TagRank tagRank = new TagRank();
        BeanUtils.copyProperties(tagRankAddDto, tagRank);
        tagRank.setCreatedTime(new Date());
        tagRank.setUpdatedTime(new Date());
        tagRank = tagRankRepository.save(tagRank);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseParam<?> deleteTagrank(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS_DELETE);
        }
        for (Long id : ids) {
            TagRank tagRank = tagRankRepository.findOne(id);
            List<ShopsShop> shopsShop = tagRank.getShopsShop();
            if (shopsShop.size() != 0) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_SHOP_EXISTS);
            }
        }
        tagRankRepository.deleteByIds(ids);
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseParam<?> updateTagrank(TagRankUpdateDto tagRankUpdateDto) {
        if (tagRankUpdateDto.getId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_ID_EMPTY);
        }
        TagRank tagRank = tagRankRepository.findOne(tagRankUpdateDto.getId());
        if (tagRank == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUPSTAGRANK_CLASS_NOT_EXISTS);
        }
        BeanUtils.copyProperties(tagRankUpdateDto, tagRank);
        tagRank.setUpdatedTime(new Date());
        tagRankRepository.save(tagRank);
        return getSuccessUpdateResult();
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> findTagrank(TagRankQuery setupsTagrankDto) {
        Pageable pageable = new PageRequest(setupsTagrankDto.getPage(), setupsTagrankDto.getPageSize());

        Specification<TagRank> specification = new Specification<TagRank>() {
            @Override
            public Predicate toPredicate(Root<TagRank> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                Predicate[] predicates = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(predicates));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderCode")), criteriaBuilder.desc(root.get("id")));
                return criteriaQuery.getRestriction();

            }
        };

        Page<TagRank> resultPage = tagRankRepository.findAll(specification, pageable);
        Pagination pagination = new Pagination(resultPage.getTotalElements(), resultPage.getNumber(), resultPage.getSize());
        List<TagRank> tagrankList = resultPage.getContent();
        List<TagRankVo> tagrankVoList = new ArrayList<>();
        if (tagrankList != null && !tagrankList.isEmpty()) {
            for (TagRank tagRank : tagrankList) {
                TagRankVo tagRankVo = new TagRankVo();
                BeanUtils.copyProperties(tagRank, tagRankVo);
                tagrankVoList.add(tagRankVo);
            }
        }
        return ResponseParam.success(tagrankVoList, pagination);
    }

    @Override
    public List<MapVo> getTagrankList() {
        List<MapVo> mapVoList = new ArrayList<>();
        List<TagRank> tagRankList = tagRankRepository.findAll();
        if (tagRankList != null && !tagRankList.isEmpty()) {
            for (TagRank tagRank : tagRankList) {
                MapVo mapVo = new MapVo();
                mapVo.setId(tagRank.getId());
                mapVo.setName(tagRank.getName());
                mapVoList.add(mapVo);
            }
        }
        return mapVoList;
    }
}
