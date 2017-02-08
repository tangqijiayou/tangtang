package com.tangqijiayou.shiro;

import com.tangqijiayou.common.ResultJsonMsg;
import com.tangqijiayou.util.ResponseUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


/**  
 *   
 * 1.扩展异步请求认证提示功能;  
 *   
 * @author merry  
 *   
 */  
public class RoleAuthorizationControlFilter extends AccessControlFilter {  
	private static final Logger logger = LoggerFactory.getLogger(RoleAuthorizationControlFilter.class);
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {  
		Subject subject = getSubject(request, response);  
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面  
        	logger.debug("您账号登录已经过期，请重新登录！");
        	ResponseUtils.out(response, new ResultJsonMsg("您账号登录已经过期，请重新登录！", ResultJsonMsg.RESULT_CODE_NOTLOGIN));
        } else {  
        	logger.debug("您账号没有权限访问，请登录有权限的账户！");
        	ResponseUtils.out(response, new ResultJsonMsg("您账号没有权限访问，请登录有权限的账户！", ResultJsonMsg.RESULT_CODE_NOTLOGIN));
        }  
	return false;
}
		
		
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		String[] arra = (String[])mappedValue;
		
		Subject subject = getSubject(request, response);
		for (String role : arra) {
			if(subject.hasRole(role)){
				return true;
			}
		}
		return false;
	}
  
}  