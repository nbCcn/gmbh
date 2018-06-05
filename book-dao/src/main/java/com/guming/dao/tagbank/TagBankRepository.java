package com.guming.dao.tagbank;

import com.guming.common.base.repository.BaseRepository;
import com.guming.tagbank.entity.TagBank;
import com.guming.tagline.entity.TagLine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 10:53
 */
public interface TagBankRepository extends BaseRepository<TagBank, Long> {
}
