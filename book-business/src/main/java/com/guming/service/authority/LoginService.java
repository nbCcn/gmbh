package com.guming.service.authority;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.dingtalk.vo.DingSignVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
public interface LoginService extends BaseService {

    /**
     * 获取Token并缓存
     * @param httpSession
     * @param userName
     * @return
     */
    ResponseParam<String> getToken(HttpSession httpSession, String userName);

    /**
     * 验证登陆
     * @param httpSession
     * @param tokenPassInfo
     * @return
     */
    ResponseParam validateLogin(HttpSession httpSession, String tokenPassInfo);

    /**
     * 登出
     * @param httpSession
     * @return
     */
    ResponseParam loginOut(HttpSession httpSession);

    /**
     * 钉钉免登,首次登录手机号关联插入模式
     * @param code
     * @param httpSession
     * @return
     */
    ResponseParam validateLoginForDing(String code, HttpSession httpSession);

    ResponseParam<DingSignVo> config(String url);

    /**
     * 钉钉免登，首次登录跳转登录页面方式关联数据方式
     * @param code
     * @param httpSession
     * @return
     */
    ResponseParam validateLoginForDingWithLogin(String code, HttpSession httpSession);

    /**
     * 客户端登录
     * @param tokenPassInfo
     * @param httpSession
     * @return
     */
    ResponseParam loginClient(String tokenPassInfo, HttpSession httpSession);
}
