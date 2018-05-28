package com.guming.admin.interceptor;

import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.LoginConstants;
import com.guming.common.utils.CookieUtil;
import com.guming.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/22
 */
public class MdcInterceptor extends HandlerInterceptorAdapter {

    public final static String USER_KEY = "user_id";

    public final static String USER_NAME = "user_name";

    public final static String REQUEST_REQUEST_URI = "request_uri";

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadContext.put(REQUEST_REQUEST_URI, request.getRemoteHost());
        //訪問ip
        String userToken = CookieUtil.getCookieValue(request,LoginConstants.LOGIN_COOKIE_KEY);
        String userId = "";
        String userName = "";
        if (!StringUtils.isEmpty(userToken)) {
            UserAuthorityVo userAuthorityVo = (UserAuthorityVo) redisService.get(userToken);
            userId = userAuthorityVo.getId().toString();
            userName = userAuthorityVo.getUserName();
        }

        ThreadContext.put(USER_KEY, userId);
        ThreadContext.put(USER_NAME, userName);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadContext.remove(USER_KEY);
        ThreadContext.remove(USER_NAME);
        ThreadContext.remove(REQUEST_REQUEST_URI);
    }
}
