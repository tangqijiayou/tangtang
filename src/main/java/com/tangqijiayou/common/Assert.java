package com.tangqijiayou.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Collection;

public class Assert {
	
	/**
	 * 验证`bool`是否为true，为false时，将以`errorMessage`为参数，构造并抛出`type`类型的异常
	 * @param bool 为false时抛出异常
	 * @param type 异常类型，必须带有String为参数的构造方法
	 * @param errorMessage 错误信息
	 */
	public static void validate(boolean bool, Class<? extends RuntimeException> type, String errorMessage) {
		if (!bool) {
			RuntimeException exception = null;
			try {
				exception  = type.getConstructor(String.class).newInstance(errorMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (exception != null) {
				throw exception;
			}
		}
	}
	
	/**
	 * 判定对象不为空，否则抛出异常
	 * @param obj
	 * @param message
	 * @author merry
	 */
	public static void notNull(Object obj, String message) {
		validate(obj!=null, ServiceException.class, message);
	}
	
	/**
	 * 判定对象不为空，否则抛出异常
	 * @param obj
	 * @param message format like :"this is a message with params, {}, {}"
	 * @param objs
	 * @author merry
	 */
	public static void notNull(Object obj, String message, Object... objs) {
		final FormattingTuple msg = MessageFormatter.arrayFormat(message, objs);
		notNull(obj, msg.getMessage());
	}
	
	public static void isNull(Object obj, String message) {
		validate(obj == null, ServiceException.class, message);
	}
	
	public static void isNull(Object obj, String message, Object...objects) {
		final FormattingTuple msg = MessageFormatter.arrayFormat(message, objects);
		isNull(obj, msg.getMessage());
	}
	
	public static void isTrue(boolean expression, String message) {
		validate(expression, ServiceException.class, message);
	}
	
	public static void isFalse(boolean expression, String message, Object...objects) {
		isTrue(!expression, message, objects);
	}
	
	public static void isTrue(boolean expression, String pattern, Object...objects) {
		isTrue(expression, MessageFormatter.arrayFormat(pattern, objects).getMessage());
	}
	
	/**
	 * 判定字符串不是为空
	 * @param str
	 * @param message
	 * @param objects
	 * @author merry
	 */
	public static void notBlank(String str, String message, Object...objects) {
		notBlank(str, MessageFormatter.arrayFormat(message, objects).getMessage());
	}
	
	/**
	 * 判定字符串不是为空
	 * @author merry
	 */
	public static void notBlank(String str, String message) {
		isTrue(StringUtils.isNotBlank(str), message);
	}
	
	/**
	 * 判定集合不为空
	 * @param list
	 * @param message
	 * @author merry
	 */
	public static void notEmpty(Collection<?> list, String message) {
		isTrue(CollectionUtils.isNotEmpty(list), message);
	}
	
}
