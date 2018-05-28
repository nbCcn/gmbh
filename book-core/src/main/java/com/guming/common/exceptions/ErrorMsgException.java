package com.guming.common.exceptions;


import com.guming.common.constants.ErrorMsgConstants;

public class ErrorMsgException  extends RuntimeException{

	public static final Integer CODE = ErrorMsgConstants.OPTION_FAILED_CODE;

	private String msg;

	private String[] text;

	public ErrorMsgException() {
		super();
	}

	public ErrorMsgException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorMsgException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorMsgException(Throwable cause) {
		super(cause);
	}



	public ErrorMsgException(String msg) {
		this.msg = msg;
	}

	public ErrorMsgException(String msg,String... text){
		this.msg = msg;
		this.text=text;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String[] getText() {
		return text;
	}

	public void setText(String... text) {
		this.text = text;
	}
}
