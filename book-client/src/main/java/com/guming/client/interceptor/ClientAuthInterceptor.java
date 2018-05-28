package com.guming.client.interceptor;

import com.guming.authority.entity.User;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.LoginConstants;
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
 * @Description:
 * @Date: 2018/5/8
 */
public class ClientAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证用户是否登录
        if (isLogin(request, response)) {
            return true;
        }
        throw new LoginNotException();
    }

    private boolean isLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String userFlag = CookieUtil.getCookieValue(httpServletRequest, LoginConstants.CLIENT_LOGIN_COOKIE_KEY);
        if (!StringUtils.isEmpty(userFlag)) {
            if (redisService.get(userFlag) != null) {
                UserAuthorityVo userAuthorityVo = (UserAuthorityVo) redisService.get(userFlag);
                User user = userRepository.findUserById(userAuthorityVo.getId());
                //查看当前用户是否还存在，不存在则清除缓存和cookie
                if (user != null && user.getUserName().equals(userAuthorityVo.getUserName())) {
                    Cookie loginCookie = CookieUtil.getCookie(httpServletRequest, LoginConstants.CLIENT_LOGIN_COOKIE_KEY);
                    loginCookie.setMaxAge(LoginConstants.LOGIN_COOKIE_EXPIRE);
                    httpServletResponse.addCookie(loginCookie);
                    redisService.updateExpireTime(loginCookie.getValue(), LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());
                    return true;
                } else {
                    redisService.remove(userFlag);
                }
            }
        }
        CookieUtil.deleteCookie(LoginConstants.CLIENT_LOGIN_COOKIE_KEY, httpServletRequest, httpServletResponse);
        return false;
    }
}
