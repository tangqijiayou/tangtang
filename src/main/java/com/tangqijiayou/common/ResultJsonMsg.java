package com.tangqijiayou.common;

import java.io.Serializable;

/**
 * @author merry
 */
public class ResultJsonMsg<T> implements Serializable {
	
	private static final long serialVersionUID = -6437045767326463837L;
	/** 状态码：成功 */
	public static final int RESULT_CODE_SUCCESS = 200;
	/** 状态码：失败 */
	public static final int RESULT_CODE_FAILED = 300;
	/** 状态码：未登录 */
	public static final int RESULT_CODE_NOTLOGIN = 1001;
	
	private int statusCode = ResultJsonMsg.RESULT_CODE_SUCCESS;//系统默认正确码 200 为错误或者异常
	private String message = "操作成功";// 提示信息
	private T data;
	
	public ResultJsonMsg() {}
	
	/**
	 * 构建操作失败的返回消息
	 * @param errorMsg
	 */
	public ResultJsonMsg(String errorMsg) {
		this.message = errorMsg;
		this.statusCode = 300;
	}
	
	public ResultJsonMsg(String errorMsg, int statusCode) {
		this.message = errorMsg;
		this.statusCode = statusCode;
	}
	
	public ResultJsonMsg(int statusCode, String errorMsg) {
		this.message = errorMsg;
		this.statusCode = statusCode;
	}
	
	public ResultJsonMsg(int statusCode, String errorMsg, T data) {
		this.message = errorMsg;
		this.statusCode = statusCode;
		this.data = data;
	}
	
	public ResultJsonMsg(Exception e) {
		this.message = e.getMessage();
		this.statusCode = 300;
		this.data = null;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
