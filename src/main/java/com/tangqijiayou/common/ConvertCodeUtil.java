package com.tangqijiayou.common;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author merry
 */
public class ConvertCodeUtil{


	/**
	 *
	 * unicode码转中文
	 *
	 * */
	public static String unicode2string(String s) {
		List<String> list =new ArrayList<String>();
		String zz="\\\\u[0-9,a-z,A-Z]{4}";

		//正则表达式用法参考API
		Pattern pattern = Pattern.compile(zz);
		Matcher m = pattern.matcher(s);
		while(m.find()){
			list.add(m.group());
		}
		for(int i=0,j=2;i<list.size();i++){
			String st = list.get(i).substring(j, j+4);

			//将得到的数值按照16进制解析为十进制整数，再強转为字符
			char ch = (char) Integer.parseInt(st, 16);
			//用得到的字符替换编码表达式
			s = s.replace(list.get(i), String.valueOf(ch));
		}
		return s;
	}
}