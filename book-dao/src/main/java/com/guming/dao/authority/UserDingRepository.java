package com.guming.dao.authority;


import com.guming.authority.entity.UserDing;
import com.guming.common.base.repository.BaseRepository;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/16
 */
public interface UserDingRepository extends BaseRepository<UserDing,Long> {

    UserDing findOneByDingUserAndDingId(String dingUser, String dingId);
}
