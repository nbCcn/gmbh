package com.guming.dao.authority;


import com.guming.authority.entity.User;
import com.guming.common.base.repository.BaseRepository;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
public interface UserRepository  extends BaseRepository<User,Long> {

    User findUserByUserName(String userName);

    User findUserById(Long id);

    Long countByUserName(String userName);
}
