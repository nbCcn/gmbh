package com.guming.base;

import com.guming.authority.entity.User;
import com.guming.authority.vo.UserAuthorityVo;
import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.LoginConstants;
import com.guming.common.exceptions.LoginNotException;
import com.guming.common.utils.CookieUtil;
import com.guming.dao.authority.UserRepository;
import com.guming.redis.RedisService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/10
 */
public abstract class BaseController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public abstract BaseService getService();


    protected String i18nHandler(String i18nMsg,String ... text){
        String msg = messageSource.getMessage(i18nMsg.trim(), null, LocaleContextHolder.getLocale());
        if (text!=null && text.length>0){
            for (int i=0;i<text.length;i++){
                msg = msg.replace("{"+i+"}",text[i]);
            }
        }
        return msg;
    }

    protected ResponseParam getSuccessOperationResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessAddResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_ADD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessUpdateResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_UPDATE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessDeleteResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_DELETE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessImportResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_UPLOAD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessExportResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_EXPORT_SUCCESS_MSG));
    }

    /**
     * 获取当前服务端登录的用户
     * @return
     */
    public User getCurrentServerUser(){
        //从会话中获取当前用户信息
        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) redisService.get(CookieUtil.getCookieValue(getRequest(),LoginConstants.LOGIN_COOKIE_KEY));
        //这里获取订单提交人数据，如果提交人在此期间被删除了，则强制退出
        User user = userRepository.findUserById(userAuthorityVo.getId());
        if (user == null){
            throw new LoginNotException();
        }
        return user;
    }

    /**
     * 获取当前客户端登录的用户
     * @return
     */
    public User getCurrentClientUser(){
        //从会话中获取当前用户信息
        UserAuthorityVo userAuthorityVo = (UserAuthorityVo) redisService.get(CookieUtil.getCookieValue(getRequest(),LoginConstants.CLIENT_LOGIN_COOKIE_KEY));
        //这里获取订单提交人数据，如果提交人在此期间被删除了，则强制退出
        User user = userRepository.findUserById(userAuthorityVo.getId());
        if (user == null){
            throw new LoginNotException();
        }
        return user;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    @ApiOperation(value = "导入excel")
    @GetMapping("importExcel")
    @ResponseBody
    public ResponseParam importExcel(MultipartFile multipartFile){
        getService().importExcel(multipartFile);
        return getSuccessExportResult();
    }

}
