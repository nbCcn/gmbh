package com.guming.common.interceptors;

import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.AuthorityException;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.exceptions.InitialPasswordException;
import com.guming.common.exceptions.LoginNotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author peng_cheng
 */
@ControllerAdvice
public class GlobalExceptionResolver{
	
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseParam<?> defultExcepitonHandler(HttpServletRequest request, Exception  e){
		ResponseParam<?> responseParam = null;
		if (e instanceof ErrorMsgException){
			ErrorMsgException x = (ErrorMsgException) e;
			String msg = getI18nMessage(x.getMsg());
			if (x.getText()!=null && x.getText().length>0){
				for (int i=0;i<x.getText().length;i++){
					msg = msg.replace("{"+i+"}",x.getText()[i]);
				}
			}
			responseParam= ResponseParam.error(ErrorMsgException.CODE, msg);
		}else if(e instanceof LoginNotException){
			responseParam= ResponseParam.error(LoginNotException.CODE,getI18nMessage(LoginNotException.MSG));
		}else if(e instanceof AuthorityException){
			responseParam= ResponseParam.error(AuthorityException.CODE,getI18nMessage(AuthorityException.MSG));
		}else if(e instanceof InitialPasswordException){
			responseParam= ResponseParam.error(InitialPasswordException.CODE,getI18nMessage(InitialPasswordException.MSG));
		}else{
			logger.error("==============================系统出错===========================",e);
			responseParam= ResponseParam.error(ErrorMsgConstants.SYSTEM_ERROR_CODE, getI18nMessage(ErrorMsgConstants.SYSTEM_FAILED_MSG));
		}
		return responseParam;
	}

	private String getI18nMessage(String message){
		try {
			return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
		}catch (NoSuchMessageException x){
			return message;
		}
	}

}
