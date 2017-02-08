package com.tangqijiayou.shiro;

import com.tangqijiayou.repository.IAccountRepository;
import com.tangqijiayou.util.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.tangqijiayou.model.Account;
/**
 * 自定义的指定Shiro验证用户登录的类
 *
 * @author merry
 */
public class ShiroAuthorizingRealmService extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroAuthorizingRealmService.class);

	private @Autowired
	IAccountRepository accountRepository;


	/**
	 * 为当前登录的Subject授予角色和权限
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String currentUserPhone = (String) super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		// 从数据库中获取当前登录用户的详细信息
		Account account = accountRepository.findByPhone(currentUserPhone);
		// 判断该用户是否存在,如果存在则获取相关信息，不存在则抛出异常信息
		if (null != account) {
			// 判断该用户是否有角色，有则获取
			if (StringUtils.isNotBlankAndEmpty(account.getRole())) {

				simpleAuthorInfo.addRole(account.getRole());
			}
		} else {
			throw new AuthorizationException();
		}
		return simpleAuthorInfo;
	}

	/**
	 * 验证当前登录的Subject
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		// 两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		logger.info(
				"验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		Account account = accountRepository.findByPhone(token.getUsername());
		if (null != account) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(account.getPhone(), account.getPassword(),
					account.getNickName());
			this.setSession("currentUser", account);
			return authcInfo;
		} else {
			return null;
		}
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 *
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}


	/**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPreviousPrincipals());
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}