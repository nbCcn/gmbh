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
import com.guming.common.constants.LoginConstants;
import com.guming.common.constants.RedisCacheConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.exceptions.InitialPasswordException;
import com.guming.common.exceptions.LoginNotException;
import com.guming.common.utils.CookieUtil;
import com.guming.common.utils.EncryptUtils;
import com.guming.common.utils.PBKDF2PasswordHasher;
import com.guming.common.utils.SessionUtil;
import com.guming.config.BookConfig;
import com.guming.dao.authority.UserDingRepository;
import com.guming.dao.authority.UserRepository;
import com.guming.dingtalk.DingTalkService;
import com.guming.dingtalk.response.DingUserInfoResponseParam;
import com.guming.dingtalk.vo.DingSignVo;
import com.guming.redis.RedisService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private RedisService redisService;

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
    public ResponseParam<String> getToken(HttpServletRequest request, String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_LOGIN_USERNAME_EMPTY);
        }
        String cacheKey = request.getRemoteAddr() + "::" + userName;
        String result = (int) (Math.random() * 3000) + UUID.randomUUID().toString().replaceAll("-", "");
        //2分钟后过期
        redisService.set(cacheKey, result, LoginConstants.LOGIN_TOKEN_EXPIRE);
        return ResponseParam.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam validateLogin(HttpServletRequest request, HttpServletResponse response, String tokenPassInfo) {
        User user = logInValidation(request,tokenPassInfo,true);
        //设置sessionId cookie
        try {
            String sessionId = SessionUtil.generateSessionId();
            CookieUtil.setCookie(LoginConstants.LOGIN_COOKIE_KEY, sessionId, null, LoginConstants.LOGIN_COOKIE_EXPIRE, response);
            //将用户信息存入缓存，过时时间与cookie一致
            userCacheHandler(sessionId, user, LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());
            //菜单权限信息存入缓存，过时时间与cookie一致
            userAuthorityHandler(sessionId, user, LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());

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
     * @param request
     * @param tokenPassInfo
     * @return
     */
    private User logInValidation(HttpServletRequest request, String tokenPassInfo, Boolean isValidationInitPass){
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
        String tokenCacheKey = request.getRemoteAddr() + "::" + userName;
        String tokenCacheValue = (String) redisService.get(tokenCacheKey);
        if (StringUtils.isEmpty(tokenCacheValue) || StringUtils.isEmpty(tokenInfoDto.getToken()) || !tokenCacheValue.equals(tokenInfoDto.getToken())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_TOKEN);
        }
        //token认证成功去除token缓存
        redisService.remove(tokenCacheKey);

        User user = userRepository.findUserByUserName(userName);
        if (user == null || !new PBKDF2PasswordHasher().checkPassword(tokenInfoDto.getUserPass(), user.getUserPass())) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS);
        }
        if (user.getIsActive() == null || !user.getIsActive()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_ACTIVE);
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
     * @param sessionId cookie中的用户标志参数，用来生成缓存key
     * @param user      用户信息
     */
    private void userAuthorityHandler(String sessionId, User user, Long expireTime) {
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
        String cacheKey = sessionId + RedisCacheConstants.AUTHORITY_CACHE_KEY_SUFFIX;
        redisService.set(cacheKey, treeVoList, expireTime);
    }

    /**
     * 查询出用户信息并存入缓存
     * @param user
     * @param cacheKey
     */
    private void userCacheHandler(String cacheKey, User user, Long expireTime) {
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
        redisService.set(cacheKey, userAuthorityVo, expireTime);
    }

    @Override
    public ResponseParam loginOut(HttpServletRequest request, HttpServletResponse response) {
        String userFlag = CookieUtil.getCookieValue(request, LoginConstants.LOGIN_COOKIE_KEY);
        if (!StringUtils.isEmpty(userFlag)) {
            //从缓存清除用户信息
            redisService.remove(userFlag);
            //从缓存清除权限信息
            redisService.remove(userFlag + RedisCacheConstants.AUTHORITY_CACHE_KEY_SUFFIX);
            //移除cookie
            CookieUtil.deleteCookie(LoginConstants.LOGIN_COOKIE_KEY, request, response);
        }
        return ResponseParam.success(null);
    }

    /**
     * 手机号关联插入模式钉钉免登登录
     * @param code
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam validateLoginForDing(String code, HttpServletRequest request, HttpServletResponse response) {
        DingUserInfoResponseParam dingUserInfoResponseParam = dingTalkService.getUserInfo(code);
        UserDing userDing = userDingRepository.findOneByDingUserAndDingId(dingUserInfoResponseParam.getUserid(),dingUserInfoResponseParam.getDingId());

        User user = null;

        //当通过钉钉查不到信息时，代表后台账号与钉钉并未关联.通过手机号查询出账号，并将两者关联
        if (userDing == null){
            user = userRepository.findUserByUserName(dingUserInfoResponseParam.getMobile());
            if (user == null){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_CLIENT_LOGIN_ERROR);
            }
            user.setJoinedTime(new Date());
            user.setIsStaff(false);
            user.setUpdateTime(new Date());
            user.setFirstName(dingUserInfoResponseParam.getName());

            List<UserDing> userDingList = user.getUserDingList();

            if (userDingList == null){
                userDingList = new ArrayList<>();
            }
            userDing = new UserDing();
            userDing.setCreateTime(new Date());
            userDing.setUpdateTime(new Date());
            userDing.setDingId(dingUserInfoResponseParam.getDingId());
            userDing.setDingUser(dingUserInfoResponseParam.getUserid());
            userDingList.add(userDing);

            user.setUserDingList(userDingList);
        }else{
            user = userDing.getUser();
        }
        //更新登陆时间
        user.setLastLoginTime(new Date());
        userRepository.save(user);

        userInfoClientCache(user,response);
        return ResponseParam.success(null);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> validateLoginForDingWithLogin(String code, HttpServletRequest request, HttpServletResponse response){
        DingUserInfoResponseParam dingUserInfoResponseParam = dingTalkService.getUserInfo(code);
        UserDing userDing = userDingRepository.findOneByDingUserAndDingId(dingUserInfoResponseParam.getUserid(),dingUserInfoResponseParam.getDingId());
        //如果通过钉钉查不到用户，则通知前台退出到登录页面
        if(userDing == null){
            CookieUtil.setCookie(LoginConstants.DING_USER_COOKIE_KEY, dingUserInfoResponseParam.getUserid(), null, response);
            throw new LoginNotException();
        }
        User user = userDing.getUser();
        //更新登陆时间
        user.setLastLoginTime(new Date());
        userRepository.save(user);

        userInfoClientCache(user,response);
        return ResponseParam.success(dingUserInfoResponseParam.getMobile());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<String> loginClient(String tokenPassInfo, HttpServletRequest request, HttpServletResponse response) {
        User user = logInValidation(request,tokenPassInfo,false);
        userInfoClientCache(user,response);
        user = syncUserDing(user,request,response);
        return ResponseParam.success(user.getPhone());
    }

    /**
     * 同步綁定釘釘信息
     * @param user      當前用戶
     * @param request
     */
    private User syncUserDing(User user, HttpServletRequest request, HttpServletResponse response) {
        //從cookie中獲取用戶釘釘userid
        String dingUser = CookieUtil.getCookieValue(request,LoginConstants.DING_USER_COOKIE_KEY);
        if(!StringUtils.isEmpty(dingUser)) {
            DingUserInfoResponseParam dingUserInfoResponseParam = dingTalkService.getUserInfoByUserId(dingUser);
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
            CookieUtil.deleteCookie(LoginConstants.DING_USER_COOKIE_KEY, request, response);
        }
        return user;
    }

    @Override
    public ResponseParam loginOutClient(HttpServletRequest request, HttpServletResponse response) {
        String userFlag = CookieUtil.getCookieValue(request, LoginConstants.CLIENT_LOGIN_COOKIE_KEY);
        if (!StringUtils.isEmpty(userFlag)) {
            //从缓存清除用户信息
            redisService.remove(userFlag);
            //移除cookie
            CookieUtil.deleteCookie(LoginConstants.CLIENT_LOGIN_COOKIE_KEY, request, response);
        }
        return ResponseParam.success(null);
    }


    /**
     * 用户信息存入缓存
     * @param user
     * @param response
     */
    private void userInfoClientCache(User user,HttpServletResponse response){
        //设置sessionId cookie
        try {
            String sessionId = SessionUtil.generateSessionId();
            CookieUtil.setCookie(LoginConstants.CLIENT_LOGIN_COOKIE_KEY, sessionId, null, LoginConstants.LOGIN_COOKIE_EXPIRE, response);
            //将用户信息存入缓存，过时时间与cookie一致
            UserAuthorityVo userAuthorityVo = new UserAuthorityVo();
            BeanUtils.copyProperties(user, userAuthorityVo);
            redisService.set(sessionId,userAuthorityVo,LoginConstants.LOGIN_COOKIE_EXPIRE.longValue());
        } catch (Exception e) {
            logger.error("", e);
        }
    }


    @Override
    public ResponseParam<DingSignVo> config(HttpServletRequest request) {
        return ResponseParam.success(dingTalkService.config(request));
    }
}
