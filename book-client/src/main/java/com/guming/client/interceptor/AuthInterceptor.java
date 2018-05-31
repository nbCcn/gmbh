package com.guming.client.interceptor;

import com.guming.authority.entity.User;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.SessionConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.exceptions.LoginNotException;
import com.guming.common.utils.CookieUtil;
import com.guming.dao.authority.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/8
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //验证用户是否登录
        HttpSession httpSession = request.getSession();
        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) httpSession.getAttribute(SessionConstants.LOGIN_SESSION_KEY);
        if (userAuthorityVo !=null) {
            User user = userRepository.findUserById(userAuthorityVo.getId());

            //查看当前用户是否还存在，不存在则清除session
            if (user != null && user.getUserName().equals(userAuthorityVo.getUserName())) {
                if (!user.getIsActive()){
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_ACTIVE);
                }
                httpSession.setMaxInactiveInterval(SessionConstants.SESSION_EXPIRE);
                return true;
            }
        }
        httpSession.removeAttribute(SessionConstants.LOGIN_SESSION_KEY);
        httpSession.removeAttribute(SessionConstants.MENU_AUTH);
        CookieUtil.deleteCookie(SessionConstants.LOGIN_STATUS,request,response);
        throw new LoginNotException();
    }
}
