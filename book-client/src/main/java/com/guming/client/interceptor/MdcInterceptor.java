package com.guming.client.interceptor;

import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.SessionConstants;
import org.apache.logging.log4j.ThreadContext;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ThreadContext.put(REQUEST_REQUEST_URI, request.getRemoteHost());

        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) request.getSession().getAttribute(SessionConstants.LOGIN_SESSION_KEY);

        if (userAuthorityVo != null) {
            ThreadContext.put(USER_KEY, userAuthorityVo.getId().toString());
            ThreadContext.put(USER_NAME, userAuthorityVo.getUserName());
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadContext.remove(USER_KEY);
        ThreadContext.remove(USER_NAME);
        ThreadContext.remove(REQUEST_REQUEST_URI);
    }
}
