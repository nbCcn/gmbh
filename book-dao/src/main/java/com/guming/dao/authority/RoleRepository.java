package com.guming.dao.authority;

import com.guming.authority.entity.Role;
import com.guming.common.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
public interface RoleRepository extends BaseRepository<Role, Long> {

    /**
     * 删除角色，角色等级为1的无法删除
     * @param ids   角色id
     */
    @Modifying
    @Query(value = "delete from Role r where r.id in ?1 and r.roleLevel <> 1")
    void deleteAllByIds(List<Long> ids);

    Long countAllByRoleName(String roleName);

    /**
     * 通过id查询角色
     * @param ids   角色id
     * @return       List<Role>
     */
    @Query("select r from Role r where 1=1 and r.id in ?1")
    List<Role> findRolesByIds(List<Long> ids);

    @Query("select r from Role r where r.id in (3,4,5) ")
    List<Role> findShopRole();

    Role findRoleByRoleName(String rolename);
}
