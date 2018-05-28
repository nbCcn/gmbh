package com.guming.dao.authority;

import com.guming.authority.entity.RoleMenu;
import com.guming.common.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
public interface AuthorityRepository  extends BaseRepository<RoleMenu,Long> {

    List<RoleMenu> findRoleMenuByRoleIdAndMenuCode(Long RoleId, String menuCode);

    List<RoleMenu> findRoleMenuByRoleId(Long RoleId);

    @Query(value = "select rm from RoleMenu rm where rm.roleId in ?1 group by rm.menuCode,rm.menuOperation")
    List<RoleMenu> findDistinctAuthorityInRoleId(List<Long> roleId);

    void deleteAllByRoleId(Long roleId);

}
