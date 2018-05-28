package com.guming.common.base.vo;


import com.guming.common.constants.ErrorMsgConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jenson
 * 返回给客户端的json对象
 * @param <T>
 */
@ApiModel(description= "返回响应数据")
public class ResponseParam<T> {

	@ApiModelProperty(value = "信息code (0：系统内部错误；1：成功；2：外部错误；3：未登录；4：权限不足)")
	private int code;

	@ApiModelProperty(value = "返回的消息")
	private String message;

	@ApiModelProperty(value = "返回的数据")
	private T result;

	@ApiModelProperty(value = "返回的分页数据")
	private Pagination pagination;

	public ResponseParam() {
	}

	public ResponseParam(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.result = data;
	}
	
	
	public ResponseParam(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public static  <K> ResponseParam<K> success(K data) {
		return new ResponseParam<K>(ErrorMsgConstants.OPTION_SUCCESS_CODE, "success", data);
	}

	public static  <K> ResponseParam<K> success(K data,String msg) {
		return new ResponseParam<K>(ErrorMsgConstants.OPTION_SUCCESS_CODE, msg, data);
	}

	public static  <K> ResponseParam<K> success(K data,Pagination pagination) {
		ResponseParam<K> responseParam = new ResponseParam<K>(ErrorMsgConstants.OPTION_SUCCESS_CODE, "success", data);
		responseParam.setPagination(pagination);
		return responseParam;
	}
	
	
	public static  <K> ResponseParam<K> error(int i, String msg) {
		return new ResponseParam<K>(i,msg);
	}

	@Override
	public String toString() {
		return "ResponseParam{" +
				"code=" + code +
				", message='" + message + '\'' +
				", result=" + result +
				'}';
	}
}
