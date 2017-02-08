package com.tangqijiayou.shiro;

import com.tangqijiayou.common.ResultJsonMsg;
import com.tangqijiayou.util.ResponseUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * 
 * @author merry
 * 
 */
public class LoginAuthorizationControlFilter extends AccessControlFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAuthorizationControlFilter.class);
	

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
				
		Subject subject = getSubject(request, response);
		if(subject.getPrincipal() != null){
            return Boolean.TRUE;
        }else{
        	logger.debug("您未登录，请登录！");
        	ResponseUtils.out(response, new ResultJsonMsg("您未登录，请登录！", ResultJsonMsg.RESULT_CODE_NOTLOGIN));
        }
		
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return Boolean.FALSE;
	}

}
