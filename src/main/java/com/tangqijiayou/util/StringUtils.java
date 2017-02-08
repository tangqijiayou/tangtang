package com.tangqijiayou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * @param strs
	 * @return boolean 当为多个时全部不为空则返回true,只要有一个为空则返回false
	 */
	public static boolean isNotBlankAndEmpty(String... strs) {
		if (null == strs)
			return false;
		for (String str : strs) {
			if (isBlank(str) || isEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param strs
	 * @return boolean 当为多个时全部为空则返回true,只要有一个不为空则返回false
	 */
	public static boolean isBlankOrEmpty(String... strs) {
		if (null == strs)
			return true;
		for (String str : strs) {
			if (isNotBlank(str) && isNotEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	public static String convert2Unicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuilder sb = new StringBuilder(str.length() * 6);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
		}
		return (new String(sb));
	}

	/**
	 * 通过值获取文本，用于实体类中采用枚举类型的属性，例如Entrant的status属性
	 * 
	 * @param value
	 * @param statuses
	 * @param names
	 * @return
	 */
	public static String getTextByValue(String value, String[] statuses, String[] names) {
		if (isBlankOrEmpty(value) || isBlankOrEmpty(statuses) || isBlankOrEmpty(names)) {
			return null;
		}
		if (statuses.length != names.length) {
			return null;
		}
		String returnStr = null;
		for (int i = 0; i < statuses.length; i++) {
			if (statuses[i].equals(value)) {
				returnStr = names[i];
				break;
			}
		}
		return returnStr;
	}

	/**
	 * 模板替换函数，替换规则为template中的{0},{1},{2}...用params替换
	 * 
	 * @param template
	 * @param params
	 * @return
	 */
	public static String replaceTemplate(String template, String... params) {
		if (isBlankOrEmpty(template)) {
			return template;
		}

		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				template = template.replace(new StringBuilder("{").append(i).append("}").toString(), isBlankOrEmpty(params[i]) ? "" : params[i]);
			}
		}
		return template;
	}

	/**
	 * 向URL后面加参数
	 * 
	 * @param url
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public static String addParam2Url(String url, String paramName, String paramValue) {
		if (!isNotBlankAndEmpty(url, paramName, paramValue)) {
			return url;
		}
		StringBuilder returnUrl = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			returnUrl.append("?");
		} else {
			returnUrl.append("&");
		}
		returnUrl.append(paramName).append("=").append(paramValue);
		return returnUrl.toString();
	}

	/**
	 * 根据参数生成sql中的字符串参数
	 * 
	 * @param str
	 * @return 去掉首位空格的字符串，或者字符串"null"
	 */
	public static String getStrInSql(String str) {
		if (StringUtils.isNotBlankAndEmpty(str)) {
			return new StringBuilder("'").append(str.trim()).append("'").toString();
		}
		return "null";
	}

	/**
	 * 根据参数生成sql中的整形参数
	 * 
	 * @param l
	 * @return 返回整型，或者字符串"null"
	 */
	public static String getLongInSql(Long l) {
		if (l != null && l.longValue() != 0L) {
			return l.toString();
		}
		return "null";
	}

	/**
	 * 根据参数生成sql中的整形参数
	 * 
	 * @param i
	 * @return 返回整型，或者字符串"null"
	 */
	public static Object getIntegerInSql(Integer i) {
		if (i != null && i.intValue() != 0L) {
			return i.toString();
		}
		return "null";
	}

	public static final char UNDERLINE = '_';

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			// String.valueOf(Character.toUpperCase(sb.charAt(position)));
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

}