package com.guming.service.tagrank;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.MapVo;
import com.guming.common.base.vo.ResponseParam;
import com.guming.tagrank.dto.TagRankAddDto;
import com.guming.tagrank.dto.TagRankUpdateDto;
import com.guming.tagrank.dto.query.TagRankQuery;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/13 21:20
 */
public interface TagRankService extends BaseService {

    ResponseParam<?> addTagrank(TagRankAddDto tagRankAddDto);

    ResponseParam<?> findTagrank(TagRankQuery setupsTagrankDto);

    ResponseParam<?> deleteTagrank(List<Long> ids);

    ResponseParam<?> updateTagrank(TagRankUpdateDto tagRankUpdateDto);

    List<MapVo> getTagrankList();
}
