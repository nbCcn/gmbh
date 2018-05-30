package com.guming.service.authority.impl;

import com.guming.authority.entity.Menu;
import com.guming.authority.entity.RoleMenu;
import com.guming.common.base.vo.PermissionVo;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.base.vo.TreeVo;
import com.guming.common.constants.SessionConstants;
import com.guming.common.constants.RedisCacheConstants;
import com.guming.common.constants.business.OperationType;
import com.guming.common.utils.CookieUtil;
import com.guming.dao.authority.AuthorityRepository;
import com.guming.dao.authority.MenuRepository;
import com.guming.redis.RedisService;
import com.guming.service.authority.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pengCheng
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RedisService redisService;

    @Override
    protected BaseRepository getRepository() {
        return this.menuRepository;
    }

    @Override
    public ResponseParam<List<TreeVo>> findAllMenusRoleHas(Long roleId) {
        return ResponseParam.success(findMenuTreeForRole(roleId));
    }

    @Override
    public ResponseParam<List<TreeVo>> findAllMenus() {
        return ResponseParam.success(findMenuTreeForRole(null));
    }

    @Override
    public ResponseParam<List<TreeVo>> findCurrentUserMenu(HttpServletRequest request, HttpServletResponse response){
        //在登录时，已将用户权限存入
        List<TreeVo> treeVoList = (List<TreeVo>) request.getSession().getAttribute(SessionConstants.MENU_AUTH);
        return ResponseParam.success(treeVoList);
    }


    /**
     * 直接查询所有菜单
     * @param roleId 角色id，根据其查出对应权限的菜单树；当其为null时，查询出所有的菜单树
     * @return treeDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TreeVo> findMenuTreeForRole(Long roleId){
        List<TreeVo> menuTreeVoList = new ArrayList<TreeVo>();

        //权限
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (roleId != null) {
            //通过角色id查出对应的权限
            roleMenuList = authorityRepository.findRoleMenuByRoleId(roleId);
            if (roleMenuList==null || roleMenuList.isEmpty()){
                return menuTreeVoList;
            }
        }

        //先查出查询出所有的菜单
        List<Menu> menuList = menuRepository.findAllByIsValidOrderByMenuOrderAsc(true);
        if (menuList!= null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                if (StringUtils.isEmpty(menu.getPid())) {
                    menuAuthorityDetailStatusHandler(menuTreeVoList,menu,menuList,roleMenuList);
                }
            }
        }
        return menuTreeVoList;
    }

    @Transactional
    @Override
    public List<TreeVo> findMenuTreeWithCheckedByRole(Long roleId){
        List<TreeVo> menuTreeVoList = new ArrayList<TreeVo>();

        //权限
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (roleId != null) {
            //通过角色id查出对应的权限
            roleMenuList = authorityRepository.findRoleMenuByRoleId(roleId);

        }

        //先查出查询出所有的菜单
        List<Menu> menuList = menuRepository.findAllByIsValidOrderByMenuOrderAsc(true);
        if (menuList!= null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                if (StringUtils.isEmpty(menu.getPid())) {
                    menuAuthorityDetailStatusHandler(menuTreeVoList,menu,menuList,roleMenuList);
                }
            }
        }
        return menuTreeVoList;
    }

    private void menuAuthorityDetailStatusHandler(List<TreeVo> menuTreeVoList,Menu menu,List<Menu> menuList, List<RoleMenu> roleMenuList){
        TreeVo treeVo = new TreeVo();
        //操作权限组
        Set<PermissionVo> permissions = new HashSet<PermissionVo>();
        Set<String> ownedPermissions = new HashSet<String>();
        //权限处理
        copyMenuToTreeVo(menu, treeVo);
        String menuOperations = menu.getOperationType();
        if (!StringUtils.isEmpty(menuOperations)){
            String[] operationTypes = menuOperations.split(",");
            for (String operationType : operationTypes){
                PermissionVo permissionVo = new PermissionVo();
                permissionVo.setInteractiveValue(OperationType.getInteractiveValue(Integer.parseInt(operationType)));
                permissionVo.setName(i18nHandler(OperationType.getI18nName(Integer.parseInt(operationType))));
                permissions.add(permissionVo);
            }
        }
        treeVo.setPermissions(permissions);
        if (roleMenuList != null && !roleMenuList.isEmpty()){
            for (RoleMenu roleMenu: roleMenuList){
                if (roleMenu.getMenuCode().equals(menu.getMenuCode())){
                    if (roleMenu.getMenuOperation()!=null) {
                        ownedPermissions.add(OperationType.getInteractiveValue(roleMenu.getMenuOperation()));
                    }
                    treeVo.setChecked(true);
                }
            }
        }
        treeVo.setOwnedPermissions(ownedPermissions);
        treeVo.setChildren(getMenuTreeCheckedChildren(treeVo, menuList,roleMenuList));
        menuTreeVoList.add(treeVo);
    }

    /**
     * 查询子菜单树节点(有选中状态的)
     * @param treeVo        父节点
     * @param menuList      菜单组
     * @param roleMenuList  权限组
     * @return
     */
    private List<TreeVo> getMenuTreeCheckedChildren(TreeVo treeVo,List<Menu> menuList, List<RoleMenu> roleMenuList){
        List<TreeVo> childrenTreeVoList = new ArrayList<>();
        for (Menu menu : menuList){
            if (menu.getPid() != null && menu.getPid().equals(treeVo.getId())){
                menuAuthorityDetailStatusHandler(childrenTreeVoList,menu,menuList,roleMenuList);
            }
        }
        return childrenTreeVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TreeVo> findMenuTreeForRoleGroup(List<Long> roleIdList){
        List<TreeVo> menuTreeVoList = new ArrayList<TreeVo>();

        //权限
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (roleIdList != null && !roleIdList.isEmpty()) {
            //通过角色id查出对应的权限
            roleMenuList = authorityRepository.findDistinctAuthorityInRoleId(roleIdList);
        }

        //先查出查询出所有的菜单
        List<Menu> menuList = menuRepository.findAllByIsValidOrderByMenuOrderAsc(true);
        if (menuList!= null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                if (StringUtils.isEmpty(menu.getPid())) {
                    menuAuthorityDetailHandler(menuTreeVoList,menu,menuList,roleMenuList);
                }
            }
        }
        return menuTreeVoList;
    }


    private void copyMenuToTreeVo(Menu menu,TreeVo treeVo){
        treeVo.setId(menu.getMenuCode());
        treeVo.setName(i18nHandler(menu.getI18NMenuName()));
        treeVo.setIcon(menu.getMenuImg());
        treeVo.setUrl(menu.getMenuUrl());
        treeVo.setOrder(menu.getMenuOrder());
    }

    /**
     * 权限处理细节
     * @param menuTreeVoList
     * @param menu
     * @param menuList
     * @param roleMenuList
     */
    private void menuAuthorityDetailHandler(List<TreeVo> menuTreeVoList,Menu menu,List<Menu> menuList, List<RoleMenu> roleMenuList){
        TreeVo treeVo = null;

        //操作权限组
        Set<String> permissions = new HashSet<String>();

        //权限处理
        if (roleMenuList != null && !roleMenuList.isEmpty()){
            for (RoleMenu roleMenu: roleMenuList){
                if (roleMenu.getMenuCode().equals(menu.getMenuCode())){
                    treeVo = new TreeVo();
                    copyMenuToTreeVo(menu, treeVo);
                    if (roleMenu.getMenuOperation() != null) {
                        permissions.add(OperationType.getInteractiveValue(roleMenu.getMenuOperation()));
                    }
                    treeVo.setChildren(getMenuTreeChildren(treeVo, menuList,roleMenuList));
                    treeVo.setOwnedPermissions(permissions);
                }
            }
            if(treeVo != null ) {
                menuTreeVoList.add(treeVo);
            }
        }else {
            treeVo = new TreeVo();
            copyMenuToTreeVo(menu, treeVo);
            if (menu.getOperationType() != null) {
                String[] menuOperationType = menu.getOperationType().split(",");
                for (int i = 0; i < menuOperationType.length; i++) {
                    if (!StringUtils.isEmpty(menuOperationType[i])) {
                        permissions.add(OperationType.getInteractiveValue(Integer.parseInt(menuOperationType[i])));
                    }
                }
            }
            treeVo.setChildren(getMenuTreeChildren(treeVo, menuList,null));
            treeVo.setOwnedPermissions(permissions);
            menuTreeVoList.add(treeVo);
        }
    }

    /**
     * 查询子菜单树节点
     * @param treeVo        父节点
     * @param menuList      菜单组
     * @param roleMenuList  权限组
     * @return
     */
    private List<TreeVo> getMenuTreeChildren(TreeVo treeVo,List<Menu> menuList, List<RoleMenu> roleMenuList){
        List<TreeVo> childrenTreeVoList = new ArrayList<>();
        for (Menu menu : menuList){
            if (menu.getPid() != null && menu.getPid().equals(treeVo.getId())){
                menuAuthorityDetailHandler(childrenTreeVoList,menu,menuList,roleMenuList);
            }
        }
        return childrenTreeVoList;
    }
}
