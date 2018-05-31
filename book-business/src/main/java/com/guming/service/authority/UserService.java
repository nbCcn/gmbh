package com.guming.service.authority;


import com.guming.authority.dto.ChangePassDto;
import com.guming.authority.dto.UserAddDto;
import com.guming.authority.dto.UserUpdateDto;
import com.guming.authority.dto.query.UserQuery;
import com.guming.authority.entity.User;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
public interface UserService extends BaseService {
    ResponseParam updateUser(UserUpdateDto userUpdateDto);

    ResponseParam addUser(UserAddDto userAddDto);

    ResponseParam deleteUser(List<Long> ids);

    ResponseParam findUserByPage(UserQuery userQuery);

    ResponseParam findById(Long id);

    ResponseParam changePass(ChangePassDto changePassDto);

    User addUserMsg(UserAddDto userAddDto);

    User updateUserMsg(UserUpdateDto userUpdateDto);
}