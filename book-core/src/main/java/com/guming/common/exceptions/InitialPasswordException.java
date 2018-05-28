package com.guming.common.exceptions;


import com.guming.common.constants.ErrorMsgConstants;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/9
 */
public class InitialPasswordException extends RuntimeException{
    public static final Integer CODE =ErrorMsgConstants.OPTION_INITIAL_PASSWORD_CODE;

    public static final String MSG = ErrorMsgConstants.OPTION_INITIAL_PASSWORD_FIRST;
}
