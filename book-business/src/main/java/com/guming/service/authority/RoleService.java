package com.guming.service.authority;


import com.guming.authority.dto.RoleAddDto;
import com.guming.authority.dto.RoleUpdateDto;
import com.guming.authority.dto.query.RoleQuery;
import com.guming.authority.vo.RoleVo;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
public interface RoleService extends BaseService {

    ResponseParam addRole(RoleAddDto roleAddDto);

    ResponseParam updateRole(RoleUpdateDto roleUpdateDto);

    ResponseParam deleteRole(List<Long> ids);

    ResponseParam findRole(RoleQuery roleQuery);

    ResponseParam edit(IdDto idDto);

    ResponseParam<List<RoleVo>> findAllRole();
}
