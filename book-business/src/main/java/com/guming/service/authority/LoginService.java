package com.guming.service.authority;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.dingtalk.vo.DingSignVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
public interface LoginService extends BaseService {

    /**
     * 获取Token并缓存
     * @param request
     * @param userName
     * @return
     */
    ResponseParam<String> getToken(HttpServletRequest request, String userName);

    /**
     * 验证登陆
     * @param request
     * @param response
     * @param tokenPassInfo
     * @return
     */
    ResponseParam validateLogin(HttpServletRequest request, HttpServletResponse response, String tokenPassInfo);

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    ResponseParam loginOut(HttpServletRequest request, HttpServletResponse response);

    /**
     * 钉钉免登,首次登录手机号关联插入模式
     * @param code
     * @param request
     * @param response
     * @return
     */
    ResponseParam validateLoginForDing(String code, HttpServletRequest request, HttpServletResponse response);

    ResponseParam<DingSignVo> config(HttpServletRequest request);

    /**
     * 钉钉免登，首次登录跳转登录页面方式关联数据方式
     * @param code
     * @param request
     * @param response
     * @return
     */
    ResponseParam validateLoginForDingWithLogin(String code, HttpServletRequest request, HttpServletResponse response);

    ResponseParam loginClient(String tokenPassInfo, HttpServletRequest request, HttpServletResponse response);

    ResponseParam loginOutClient(HttpServletRequest request, HttpServletResponse response);
}
