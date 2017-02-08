package com.tangqijiayou.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查正则
 * 
 * @author iversonLiang
 * 
 */
public class RegularUtil {
	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static void checkEmail(String email) {
//		String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//		String reg = "^([a-zA-Z0-9_\\.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
		String reg = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		boolean result = RegularUtil.match(reg, email);
		if (!result) {
			throw new IllegalArgumentException("邮箱规则不符");
		}
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static void checkMobile(String mobile) {
//		String reg = "^[1][3,4,5,8][0-9]{9}$";
//		String reg = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
		String reg = "^1[1-9][0-9]{9}$";
		boolean result = RegularUtil.match(reg, mobile);
		if (!result) {
			throw new IllegalArgumentException("手机规则不符");
		}
	}

	/**
	 * 检查是否字母+数字，min-max位
	 * 
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static void checkNumberAndLetter(String str, int min, int max) {
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + min + "," + max + "}$";
		boolean result = RegularUtil.match(reg, str);
		if (!result) {
			throw new IllegalArgumentException("规则不符");
		}
	}
	
	public static void checkUrl(String str) {
		String reg = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
		boolean result = RegularUtil.match(reg, str);
		if (!result) {
			throw new IllegalArgumentException("不是url");
		}
	}
	
	/**
	 * 可以包含字母数字，标点
	 * 
	 * @param str
	 * @param min
	 * @param max
	 */
	public static void checkNumLetterAndPunctuation(String str, int min, int max) {
		String reg = "^[\\w_.,~?-]{" + min + "," + max + "}$";
		boolean result = RegularUtil.match(reg, str);
		if (!result) {
			throw new IllegalArgumentException("规则不符");
		}
	}
	

	private static boolean match(String reg, String checkStr) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(checkStr);
		return matcher.matches();
	}

	/**
	 * 检查字符长度
	 * 
	 * @param str
	 * @param maxLength
	 * @param type
	 */
	public static void checkStrLength(String str, int maxLength) {
		String reg = "[^\\x00-\\xff]";
		int currLength = str.replaceAll(reg, "xx").length();
		if (currLength > maxLength) {
			throw new IllegalArgumentException("字符长度不符");
		}
	}
	
	/**
	 * 检查是否字母，中文，标点
	 * 
	 * @param str
	 */
	public static void checkWordAndPunctuation(String str) {
		String reg = "^(?!_)(?!.*?_$)[a-zA-Z0-9\\'\\.\\,\\&\\_\\-\\s\\（\\）\\(\\)\\u4e00-\\u9fa5]+$";
		boolean result = RegularUtil.match(reg, str);
		if (!result) {
			throw new IllegalArgumentException("字符不合符规则");
		}
	}
	
	/**
	 * 过滤错误信息里面的[]
	 * 
	 * @param message
	 * @return
	 */
	public static String filterErrorMessage(String message) {
		return message.replaceAll("\\[.+?\\]", "");
	}
}
