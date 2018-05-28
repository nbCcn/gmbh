package com.guming.common.exceptions;


import com.guming.common.constants.ErrorMsgConstants;

public class LoginNotException  extends RuntimeException{

    public static final Integer CODE =ErrorMsgConstants.OPTION_NOT_LOGIN_CODE;

    public static final String MSG = ErrorMsgConstants.OPTION_LOGIN_FIRST;
}
