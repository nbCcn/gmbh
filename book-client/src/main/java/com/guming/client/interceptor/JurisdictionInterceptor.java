package com.guming.client.interceptor;

import com.guming.authority.vo.RoleAuthorityVo;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.SessionConstants;
import com.guming.common.constants.RoleConstants;
import com.guming.common.exceptions.AuthorityException;
import com.guming.common.utils.CookieUtil;
import com.guming.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) request.getSession().getAttribute(SessionConstants.LOGIN_SESSION_KEY);
        if (userAuthorityVo!=null) {
            //非超级管理员判断是否有该访问的方法的权限
            List<RoleAuthorityVo>  roleAuthorityVoList= userAuthorityVo.getRoleAuthorityDtoList();
            Integer temp = 0;
            if (roleAuthorityVoList != null && !roleAuthorityVoList.isEmpty()){
                for (RoleAuthorityVo roleAuthorityVo:roleAuthorityVoList){
                    if(roleAuthorityVo.getRoleLevel().equals(RoleConstants.CLIENT_ROLE)){
                        temp+=1;
                    }
                }
                if (temp >= 1){
                    return true;
                }
            }
        }
        throw new AuthorityException();
    }
}
