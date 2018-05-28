package com.guming.service.authority;


import com.guming.authority.dto.AuthorityDto;
import com.guming.authority.entity.RoleMenu;
import com.guming.common.base.service.BaseService;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 权限操作
 * @Date: 2018/4/12
 */
public interface AuthorityService extends BaseService {

    /**
     * 通过角色id和菜单code查出该角色对菜单的操作权限
     * @param menuCode
     * @param roleId
     * @return
     */
    List<RoleMenu> findRoleOperationAuthority(String menuCode, Long roleId);

    /**
     * 通过角色id查询权限
     * @param roleId
     * @return
     */
    List<RoleMenu> findRoleAuthority(Long roleId);

    /**
     * 添加角色权限
     * @param authorityDtoList
     * @param roleId                所属的角色id
     */
    void addRoleAuthority(List<AuthorityDto> authorityDtoList, Long roleId);

    /**
     * 更新角色权限
     * @param authorityDtoList
     * @param roleId                所属的角色id
     */
    void updateRoleAuthority(List<AuthorityDto> authorityDtoList, Long roleId);
}
