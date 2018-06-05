package com.guming.service.tagwareHouse.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.dao.tagbank.TagBankRepository;
import com.guming.dao.tagwareHouse.TagwareHouseRepository;
import com.guming.service.tagwareHouse.TagwareHouseService;
import com.guming.tagbank.dto.TagBankAddDto;
import com.guming.tagbank.entity.TagBank;
import com.guming.tagbank.vo.TagBankVo;
import com.guming.tagline.entity.TagLine;
import com.guming.tagwareHouse.dto.TagwareHouseAddDto;
import com.guming.tagwareHouse.dto.TagwareHouseUpdateDto;
import com.guming.tagwareHouse.dto.query.TagwareHouseQuery;
import com.guming.tagwareHouse.entity.TagwareHouse;
import com.guming.tagwareHouse.vo.TagwareHouseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/17 16:00
 */
@Service
@SuppressWarnings("all")
public class TagwareHouseServiceImpl extends BaseServiceImpl implements TagwareHouseService {

    @Autowired
    private TagwareHouseRepository tagwareHouseRepository;

    @Autowired
    private TagBankRepository tagBankRepository;


    @Override
    protected BaseRepository getRepository() {
        return this.tagwareHouseRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> find(TagwareHouseQuery tagwareHouseDto) {
        Pageable pageable = new PageRequest(tagwareHouseDto.getPage(), tagwareHouseDto.getPageSize());
        Page<TagwareHouse> page = tagwareHouseRepository.findAll(pageable);
        Pagination pagination = new Pagination(page.getTotalElements(), page.getNumber(), page.getSize());
        List<TagwareHouseVo> resultVo = new ArrayList<>();
        List<TagwareHouse> result = page.getContent();
        for (TagwareHouse tagwareHouse : result) {
            TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();
            BeanUtils.copyProperties(tagwareHouse, tagwareHouseVo);
            resultVo.add(tagwareHouseVo);
        }
        return ResponseParam.success(resultVo, pagination);
    }

    @Override
    @Transactional
    public List<MapVo> getWarehouseList() {
        List<TagwareHouse> tagwareHouseList = tagwareHouseRepository.findAll();
        List<MapVo> mapVoList = new ArrayList<MapVo>();
        for (TagwareHouse tagwareHouse : tagwareHouseList) {
            MapVo mapVo = new MapVo();
            mapVo.setId(tagwareHouse.getId());
            mapVo.setName(tagwareHouse.getName());
            mapVoList.add(mapVo);
        }
        return mapVoList;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> add(TagwareHouseAddDto tagwareHouseAddDto) {
        if (StringUtils.isEmpty(tagwareHouseAddDto.getName())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NAME_EMPTY);
        }
        Long countByName = tagwareHouseRepository.countByName(tagwareHouseAddDto.getName());
        if (countByName >= 1) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NAME_EXISTS);
        }
        TagwareHouse tagwareHouse = new TagwareHouse();
        BeanUtils.copyProperties(tagwareHouseAddDto, tagwareHouse);
        tagwareHouse.setCreatedTime(new Date());
        tagwareHouse.setUpdatedTime(new Date());

        List<TagBankAddDto> tagBankAddDtoList = tagwareHouseAddDto.getTagBankAddDtoList();

        if (tagBankAddDtoList != null) {
            List<TagBank> tagBankList = new ArrayList<>();
            for (TagBankAddDto tagBankAddDto : tagBankAddDtoList) {
                TagBank tagBank = new TagBank();
                BeanUtils.copyProperties(tagBankAddDto, tagBank);
                tagBank.setCreatedTime(new Date());
                tagBank.setUpdatedTime(new Date());
                tagBankList.add(tagBank);
            }
            tagwareHouse.setTagBankList(tagBankList);
        }

        tagwareHouseRepository.save(tagwareHouse);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam<?> update(TagwareHouseUpdateDto tagwareHouseUpdateDto) {
        if (tagwareHouseUpdateDto.getId() == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_ID_EMPTY);
        }
        TagwareHouse tagwareHouse = tagwareHouseRepository.findOne(tagwareHouseUpdateDto.getId());
        if (tagwareHouse == null) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS);
        }
        BeanUtils.copyProperties(tagwareHouseUpdateDto, tagwareHouse);
        tagwareHouse.setUpdatedTime(new Date());

        List<TagBankAddDto> tagBankAddDtoList = tagwareHouseUpdateDto.getTagBankAddDtoList();

        if (tagBankAddDtoList != null) {
            List<TagBank> tagBankList = new ArrayList<>();
            for (TagBankAddDto tagBankAddDto : tagBankAddDtoList) {
                if (tagBankAddDto.getId() == null) {
                    TagBank tagBank = new TagBank();
                    BeanUtils.copyProperties(tagBankAddDto, tagBank);
                    tagBank.setCreatedTime(new Date());
                    tagBank.setUpdatedTime(new Date());
                    tagBankList.add(tagBank);
                } else {
                    TagBank tagBank = tagBankRepository.findOne(tagBankAddDto.getId());
                    BeanUtils.copyProperties(tagBankAddDto, tagBank);
                    tagBank.setUpdatedTime(new Date());
                    tagBankList.add(tagBank);
                }

            }
            tagwareHouse.setTagBankList(tagBankList);
        } else {
            tagwareHouse.setTagBankList(null);
        }


        tagwareHouseRepository.save(tagwareHouse);
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseParam delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_CLASS_NOT_EXISTS_DELETE);
        }
        String notDeleteTagWareHouse = "";
        for (Long id : ids) {
            TagwareHouse tagwareHouse = tagwareHouseRepository.findOne(id);
            List<TagLine> tagLines = tagwareHouse.getTagLines();
            if (tagLines == null) {
                notDeleteTagWareHouse += tagwareHouse.getName() + ",";
            }
        }
        if (!StringUtils.isEmpty(notDeleteTagWareHouse)) {
            notDeleteTagWareHouse = notDeleteTagWareHouse.substring(0, notDeleteTagWareHouse.length() - 1);
            return ResponseParam.error(ErrorMsgConstants.OPTION_FAILED_HELF_CODE, i18nHandler(ErrorMsgConstants.ERROR_VALIDATION_TAGWAREHOUSE_LINE_EXISTS, notDeleteTagWareHouse));
        }

        tagwareHouseRepository.deleteByIds(ids);
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseParam<?> findById(Long id) {
        if (id == null) {
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        }
        TagwareHouse tagwareHouse = tagwareHouseRepository.findOne(id);
        if (tagwareHouse == null) {
            throw new ErrorMsgException(ErrorMsgConstants.SYSTEM_FAILED_MSG);
        }
        TagwareHouseVo tagwareHouseVo = new TagwareHouseVo();
        BeanUtils.copyProperties(tagwareHouse, tagwareHouseVo);

        List<TagBank> tagbankList = tagwareHouse.getTagBankList();

        if (tagbankList != null) {
            List<TagBankVo> tagBankVoList = new ArrayList<>();
            for (TagBank tagBank : tagbankList) {
                TagBankVo tagBankVo = new TagBankVo();
                BeanUtils.copyProperties(tagBank, tagBankVo);
                tagBankVoList.add(tagBankVo);
            }
            tagwareHouseVo.setTagBankVoList(tagBankVoList);
        }
        return ResponseParam.success(tagwareHouseVo);
    }


}
