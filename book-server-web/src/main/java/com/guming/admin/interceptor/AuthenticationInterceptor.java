package com.guming.admin.interceptor;

import com.guming.authority.entity.User;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.LoginConstants;
import com.guming.common.constants.RedisCacheConstants;
import com.guming.common.exceptions.LoginNotException;
import com.guming.common.utils.CookieUtil;
import com.guming.dao.authority.UserRepository;
import com.guming.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengCheng
 * @Description: 用户认证拦截器
 * @Date: 2018/4/12
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //验证用户是否登录
        if (isLogin(httpServletRequest, httpServletResponse)) {
            return true;
        }
        throw new LoginNotException();
    }

    private boolean isLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String userFlag = CookieUtil.getCookieValue(httpServletRequest, LoginConstants.LOGIN_COOKIE_KEY);
        if (!StringUtils.isEmpty(userFlag)) {
            if (redisService.get(userFlag) != null) {
                UserAuthorityVo userAuthorityVo = (UserAuthorityVo) redisService.get(userFlag);
                User user = userRepository.findUserById(userAuthorityVo.getId());
                //查看当前用户是否还存在，不存在则清除缓存和cookie
                if (user != null && user.getUserName().equals(userAuthorityVo.getUserName())) {
                    Cookie loginCookie = CookieUtil.getCookie(httpServletRequest, LoginConstants.LOGIN_COOKIE_KEY);
                    loginCookie.setMaxAge(LoginConstants.LOGIN_COOKIE_EXPIRE);
                    httpServletResponse.addCookie(loginCookie);

                    redisService.updateExpireTime(loginCookie.getValue(), LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());
                    redisService.updateExpireTime(loginCookie.getValue() + RedisCacheConstants.AUTHORITY_CACHE_KEY_SUFFIX, LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());
                    return true;
                } else {
                    redisService.remove(userFlag);
                    redisService.remove(userFlag + RedisCacheConstants.AUTHORITY_CACHE_KEY_SUFFIX);
                    CookieUtil.deleteCookie(LoginConstants.LOGIN_COOKIE_KEY, httpServletRequest, httpServletResponse);
                }
            }
        }
        return false;
    }

}
