package com.guming.common.exceptions;


import com.guming.common.constants.ErrorMsgConstants;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/13
 */
public class AuthorityException extends RuntimeException{

    public static final Integer CODE =ErrorMsgConstants.OPTION_NOT_AUTHORITY_CODE;

    public static final String MSG = ErrorMsgConstants.OPTION_NOT_AUTHORITY_MSG;

}
