package com.guming.service.authority;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.base.vo.TreeVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MenuService extends BaseService {

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    ResponseParam<List<TreeVo>> findAllMenusRoleHas(Long roleId);

    /**
     * 查询所有的菜单
     * @return
     */
    ResponseParam<List<TreeVo>> findAllMenus();

    /**
     * 通过单个角色id查询出带选择状态菜单树
     * @param roleId
     * @return
     */
    List<TreeVo> findMenuTreeWithCheckedByRole(Long roleId);

    /**
     * 通过单个角色id查询出菜单树
     * @param roleId
     * @return
     */
    List<TreeVo> findMenuTreeForRole(Long roleId);

    /**
     * 通过多个角色id查询出菜单树
     * @param roleIdList
     * @return
     */
    List<TreeVo> findMenuTreeForRoleGroup(List<Long> roleIdList);


    ResponseParam<List<TreeVo>> findCurrentUserMenu(HttpServletRequest request, HttpServletResponse response);
}
