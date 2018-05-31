package com.guming.service.authority.impl;

import com.guming.authority.entity.Role;
import com.guming.authority.entity.User;
import com.guming.authority.entity.UserDing;
import com.guming.authority.vo.RoleAuthorityVo;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.base.dto.TokenInfoDto;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.base.vo.TreeVo;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.SessionConstants;
import com.guming.common.constants.RoleConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.exceptions.InitialPasswordException;
import com.guming.common.exceptions.LoginNotException;
import com.guming.common.utils.*;
import com.guming.config.BookConfig;
import com.guming.dao.authority.UserDingRepository;
import com.guming.dao.authority.UserRepository;
import com.guming.dingtalk.DingTalkService;
import com.guming.dingtalk.response.DingUserInfoResponseParam;
import com.guming.dingtalk.vo.DingSignVo;
import com.guming.service.authority.AuthorityService;
import com.guming.service.authority.LoginService;
import com.guming.service.authority.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDingRepository userDingRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private BookConfig bookConfig;

    @Override
    protected BaseRepository getRepository() {
        return null;
    }

    @Override
    public ResponseParam<String> getToken(HttpSession httpSession, String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_LOGIN_USERNAME_EMPTY);
        }
        String result = (int) (Math.random() * 3000) + UUID.randomUUID().toString().replaceAll("-", "");

        httpSession.setAttribute(SessionConstants.CSRF_TOKEN,result);
        return ResponseParam.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam validateLogin(HttpSession httpSession, String tokenPassInfo) {
        User user = logInValidation(httpSession,tokenPassInfo,true,false);
        try {
            //将用户信息存入session
            userSessionHandler(user,httpSession);
            //菜单权限信息存入session，过时时间与用户信息一致
            userMenuSessionHandler(user,httpSession);
            user.setLastLoginTime(new Date());
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
        return ResponseParam.success(null);
    }

    /**
     * 验证登陆加密信息，验证成功返回用户信息
     * @param httpSession
     * @param tokenPassInfo          登录加密串
     * @param isValidationInitPass  是否要验证初始密码
     * @param isClient                是否是客户端
     * @return
     */
    private User logInValidation(HttpSession httpSession, String tokenPassInfo, Boolean isValidationInitPass, Boolean isClient){
        //参数空判断
        if (StringUtils.isEmpty(tokenPassInfo)) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_LOGIN_INFO_EMPTY);
        }

        //信息解析
        TokenInfoDto tokenInfoDto = null;
        try {
            tokenInfoDto = EncryptUtils.decryptTokenPassInfo(tokenPassInfo);
        } catch (Exception e) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_LOGIN_INFO);
        }

        //token认证
        String userName = tokenInfoDto.getUserName();

        String tokenCacheValue = (String) httpSession.getAttribute(SessionConstants.CSRF_TOKEN);
        if (StringUtils.isEmpty(tokenCacheValue) || StringUtils.isEmpty(tokenInfoDto.getToken()) || !tokenCacheValue.equals(tokenInfoDto.getToken())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TOKEN);
        }
        //token认证成功去除token缓存
        httpSession.removeAttribute(SessionConstants.CSRF_TOKEN);

        User user = userRepository.findUserByUserName(userName);
        if (user == null || !new PBKDF2PasswordHasher().checkPassword(tokenInfoDto.getUserPass(), user.getUserPass())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS);
        }
        if (user.getIsActive() == null || !user.getIsActive()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_ACTIVE);
        }
        List<Role> roleList = user.getRoleList();
        Integer clientRoleSize = 0;
        if (roleList != null && !roleList.isEmpty()){
            for (Role role : roleList){
                if (role.getRoleLevel().equals(RoleConstants.CLIENT_ROLE)){
                    clientRoleSize +=1;
                }
            }
            //如果是客户端，判断是否有客户端角色
            if(isClient){
                if (clientRoleSize<1){
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS);
                }
            }else {
                if (roleList.size() == clientRoleSize) {
                    throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS);
                }
            }
        }

        //如果是初始密码通知前台
        if (isValidationInitPass && tokenInfoDto.getUserPass().equals(bookConfig.getInitialPassword())){
            throw new InitialPasswordException();
        }

        return user;
    }

    /**
     * 通过角色组查询出权限组并存入缓存
     *
     * @param user      用户信息
     */
    private void userMenuSessionHandler(User user, HttpSession httpSession) {
        List<TreeVo> treeVoList = new ArrayList<>();
        //超级管理员拥有所用权限
        if (user.getIsSuperuser()) {
            treeVoList = menuService.findMenuTreeForRoleGroup(null);
        } else {
            //没有角色的用户没有权限
            List<Role> roleSet = user.getRoleList();
            List<Long> roleIdList = new ArrayList<>();
            if (roleSet != null && !roleSet.isEmpty()) {
                for (Role role : roleSet) {
                    roleIdList.add(role.getId());
                }
                treeVoList = menuService.findMenuTreeForRoleGroup(roleIdList);
            }
        }
        httpSession.setAttribute(SessionConstants.MENU_AUTH,treeVoList);
    }

    /**
     * 查询出用户信息并存入session
     * @param user
     */
    private void userSessionHandler(User user,HttpSession httpSession) {
        UserAuthorityVo userAuthorityVo = new UserAuthorityVo();
        BeanUtils.copyProperties(user, userAuthorityVo);
        List<Role> roleSet = user.getRoleList();
        List<RoleAuthorityVo> roleAuthorityVoList = new ArrayList<RoleAuthorityVo>();
        if (roleSet != null && !roleSet.isEmpty()) {
            for (Role role : roleSet) {
                RoleAuthorityVo roleAuthorityVo = new RoleAuthorityVo();
                BeanUtils.copyProperties(role, roleAuthorityVo);
                roleAuthorityVo.setRoleMenuList(authorityService.findRoleAuthority(roleAuthorityVo.getId()));
                roleAuthorityVoList.add(roleAuthorityVo);
            }
        }
        userAuthorityVo.setRoleAuthorityDtoList(roleAuthorityVoList);
        httpSession.setAttribute(SessionConstants.LOGIN_SESSION_KEY,userAuthorityVo);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.setCookie(SessionConstants.LOGIN_STATUS,"true","",response);
    }

    @Override
    public ResponseParam loginOut(HttpSession httpSession) {
        //登出时，清除用户登录信息
        httpSession.removeAttribute(SessionConstants.LOGIN_SESSION_KEY);
        httpSession.removeAttribute(SessionConstants.MENU_AUTH);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CookieUtil.deleteCookie(SessionConstants.LOGIN_STATUS,request,response);
        return ResponseParam.success(null);
    }

    /**
     * 手机号关联插入模式钉钉免登登录
     * @param code
     * @param httpSession
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> validateLoginForDing(String code, HttpSession httpSession) {
        DingUserInfoResponseParam dingUserInfoResponseParam = dingTalkService.getUserInfo(code);
        UserDing userDing = userDingRepository.findOneByDingUserAndDingId(dingUserInfoResponseParam.getUserid(),dingUserInfoResponseParam.getDingId());

        User user = null;
        //当通过钉钉查不到信息时，代表后台账号与钉钉并未关联.通过手机号查询出账号，并将两者关联
        if (userDing == null){
            user = userRepository.findUserByUserName(dingUserInfoResponseParam.getMobile());
            if (user == null){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_CLIENT_LOGIN_ERROR);
            }
            syncUserDing(user,dingUserInfoResponseParam);
        }else{
            user = userDing.getUser();
            if (user == null){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_CLIENT_LOGIN_ERROR);
            }
        }
        //更新登陆时间
        user.setLastLoginTime(new Date());
        userRepository.save(user);

        userSessionHandler(user,httpSession);
        return ResponseParam.success(dingUserInfoResponseParam.getMobile());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> validateLoginForDingWithLogin(String code, HttpSession httpSession){
        DingUserInfoResponseParam dingUserInfoResponseParam = dingTalkService.getUserInfo(code);
        UserDing userDing = userDingRepository.findOneByDingUserAndDingId(dingUserInfoResponseParam.getUserid(),dingUserInfoResponseParam.getDingId());
        //如果通过钉钉查不到用户，则通知前台退出到登录页面
        if(userDing == null){
            //查到钉钉信息后将钉钉信息存入会话，用于登录.为防止session
            httpSession.setAttribute(SessionConstants.DING_USER_SESSION_KEY, dingUserInfoResponseParam);
            throw new LoginNotException();
        }
        User user = userDing.getUser();
        //更新登陆时间
        user.setLastLoginTime(new Date());
        userRepository.save(user);

        userSessionHandler(user,httpSession);
        return ResponseParam.success(dingUserInfoResponseParam.getMobile());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> loginClient(String tokenPassInfo, HttpSession httpSession) {
        User user = logInValidation(httpSession,tokenPassInfo,true,true);
        userSessionHandler(user,httpSession);
        DingUserInfoResponseParam dingUserInfoResponseParam = (DingUserInfoResponseParam) httpSession.getAttribute(SessionConstants.DING_USER_SESSION_KEY);
        user = syncUserDing(user,dingUserInfoResponseParam);
        httpSession.removeAttribute(SessionConstants.DING_USER_SESSION_KEY);
        return ResponseParam.success(user.getPhone());
    }

    /**
     * 同步綁定釘釘信息
     * @param user      當前用戶
     * @param dingUserInfoResponseParam 用户钉钉信息
     */
    private User syncUserDing(User user, DingUserInfoResponseParam dingUserInfoResponseParam) {
        //從cookie中獲取用戶釘釘userid
        if(dingUserInfoResponseParam != null) {
            UserDing userDing = userDingRepository.findOneByDingUserAndDingId(dingUserInfoResponseParam.getUserid(), dingUserInfoResponseParam.getDingId());

            if (userDing == null) {
                userDing = new UserDing();
                userDing.setCreateTime(new Date());
                userDing.setUpdateTime(new Date());
                userDing.setDingId(dingUserInfoResponseParam.getDingId());
                userDing.setDingUser(dingUserInfoResponseParam.getUserid());
                userDing.setUserId(user.getId());

                List<UserDing> userDingList =user.getUserDingList();
                if (userDingList == null){
                    userDingList = new ArrayList<>();
                }
                userDingList.add(userDing);

                user.setUserDingList(userDingList);
                user.setPhone(dingUserInfoResponseParam.getMobile());
                user.setJoinedTime(new Date());
                user.setIsStaff(false);
                user.setUpdateTime(new Date());
                user.setFirstName(dingUserInfoResponseParam.getName());

                user.setLastLoginTime(new Date());
                user = userRepository.save(user);
            }
        }
        return user;
    }

    @Override
    public ResponseParam<DingSignVo> config(HttpServletRequest request) {
        return ResponseParam.success(dingTalkService.config(request));
    }
}
