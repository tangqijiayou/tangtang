package com.tangqijiayou.Exception;


import com.tangqijiayou.common.ResultJsonMsg;
import com.tangqijiayou.common.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller全局异常处理类
 * @author merry
 */
@ControllerAdvice
public class ExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJsonMsg processValidationError(Exception ex) {
		if (ex instanceof MethodArgumentNotValidException || ex instanceof BindException) {
			final BindingResult bindingResult = getBindingResult(ex);
			final List<ObjectError> allErrors = bindingResult.getAllErrors();
			final List<String> errorMsgs = new ArrayList<String>();
			for (ObjectError objectError : allErrors) {
				errorMsgs.add(objectError.getDefaultMessage());
			}
			return new ResultJsonMsg(StringUtils.join(errorMsgs, ","));
		} else if (ex instanceof ServiceException) {
			logger.warn(ex.getMessage());
			return new ResultJsonMsg(((ServiceException) ex).getResultCode(), ex.getMessage());
		} else {
			logger.error(ex.getMessage(), ex);
			return new ResultJsonMsg(ex);
		}
    }

	private BindingResult getBindingResult(Exception ex) {
		return (ex instanceof BindException) ? ((BindException) ex).getBindingResult()
				: ((MethodArgumentNotValidException) ex).getBindingResult();
	}

}