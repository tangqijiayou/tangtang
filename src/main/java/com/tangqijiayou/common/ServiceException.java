package com.tangqijiayou.common;

/**
 * Service层业务处理出现非无法处理的异常时，需返回错误信息至客户端时，抛出该异常
 * @author merry
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int resultCode = ResultJsonMsg.RESULT_CODE_FAILED;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(int errorCode, String message) {
		super(message);
		this.resultCode = errorCode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
