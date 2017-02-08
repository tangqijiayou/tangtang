package com.tangqijiayou.util;

import com.tangqijiayou.common.ResultJsonMsg;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author merry
 *
 */

public class ResponseUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * response 输出JSON
	 * @param hresponse
	 * @param resultJsonMsg
	 * @throws IOException
	 */
	public static void out(ServletResponse hresponse, ResultJsonMsg resultJsonMsg) throws IOException {
		hresponse.setCharacterEncoding("UTF-8");
		PrintWriter out = hresponse.getWriter();

		try {

			out.println(JSONObject.fromObject(resultJsonMsg).toString());

		} catch (Exception e) {
			logger.error(" 返回JSON异常。");
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
}