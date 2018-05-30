package com.guming.admin.interceptor;

import com.guming.authority.entity.RoleMenu;
import com.guming.authority.vo.RoleAuthorityVo;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.annotation.MenuOperateAuthority;
import com.guming.common.constants.SessionConstants;
import com.guming.common.exceptions.AuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 权限拦截器
 * @Date: 2018/4/12
 */
public class JurisdictionInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(JurisdictionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("========================权限处理开始==========================");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MenuOperateAuthority authority = handlerMethod.getMethodAnnotation(MenuOperateAuthority.class);
        //当没有权限注解时，代表该方法不在权限控制内，直接放过
        if (authority == null){
            return true;
        }
        String belongMenuCode = authority.belongMenuCode();
        Integer operateType = authority.operationType().getType();

        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) request.getSession().getAttribute(SessionConstants.LOGIN_SESSION_KEY);
        if (userAuthorityVo!=null) {
            //超级管理员无需验证权限
            if (userAuthorityVo.getIsSuperuser()!=null && userAuthorityVo.getIsSuperuser()){
                return true;
            }

            //非超级管理员判断是否有该访问的方法的权限
            List<RoleAuthorityVo>  roleAuthorityVoList= userAuthorityVo.getRoleAuthorityDtoList();
            for (RoleAuthorityVo roleAuthorityVo:roleAuthorityVoList){
                List<RoleMenu> roleMenuList = roleAuthorityVo.getRoleMenuList();
                for (RoleMenu roleMenu:roleMenuList){
                    if (roleMenu.getMenuCode().equals(belongMenuCode) && roleMenu.getMenuOperation().equals(operateType)){
                        return true;
                    }
                }
            }
        }
        throw new AuthorityException();
    }
}
