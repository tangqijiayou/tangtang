package com.tangqijiayou.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/27.
 */
@Configuration
public class ShiroConfiguration {

    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager(SessionDAO sessionDAO, SimpleCookie sessionIdCookie, EhCacheManager shiroEhcacheManager, SessionValidationScheduler sessionValidationScheduler) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setCacheManager(shiroEhcacheManager);
        sessionManager.setSessionIdCookieEnabled(true);

        return sessionManager;
    }

    @Bean("sessionDAO")
    public SessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return sessionDAO;
    }


    @Bean("shiroEhcacheManager")
    public EhCacheManager shiroEhcacheManager() {
        EhCacheManager shiroEhcacheManager = new EhCacheManager();
        shiroEhcacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return shiroEhcacheManager;
    }


    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie sessionIdCookie = new SimpleCookie("sid");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        return sessionIdCookie;
    }

    @Bean("sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
        return sessionIdGenerator;
    }

    @Bean("sessionValidationScheduler")
    public SessionValidationScheduler sessionValidationScheduler() {
        SessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        return sessionValidationScheduler;
    }

    @Bean("kickoutSessionControlFilter")
    public KickoutSessionControlFilter kickoutSessionControlFilter(SessionManager sessionManager, EhCacheManager shiroEhcacheManager) {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setSessionManager(sessionManager);
        kickoutSessionControlFilter.setCacheManager(shiroEhcacheManager);
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(1);

        return kickoutSessionControlFilter;
    }

    @Bean("roleAuthorizationControlFilter")
    public RoleAuthorizationControlFilter roleAuthorizationControlFilter() {
        RoleAuthorizationControlFilter roleAuthorizationControlFilter = new RoleAuthorizationControlFilter();

        return roleAuthorizationControlFilter;
    }

    @Bean("loginAuthorizationControlFilter")
    public LoginAuthorizationControlFilter loginAuthorizationControlFilter() {
        LoginAuthorizationControlFilter loginAuthorizationControlFilter = new LoginAuthorizationControlFilter();

        return loginAuthorizationControlFilter;
    }


    /**
     * 注册DelegatingFilterProxy（Shiro）* 集成Shiro有2种方法：
     * 1. 按这个方法自己组装一个FilterRegistrationBean（这种方法更为灵活，可以自己定义UrlPattern，
     * 在项目使用中你可能会因为一些很但疼的问题最后采用它， 想使用它你可能需要看官网或者已经很了解Shiro的处理原理了）
     * 2. 直接使用ShiroFilterFactoryBean（这种方法比较简单，其内部对ShiroFilter做了组装工作，无法自己定义UrlPattern，
     * 默认拦截 /*）
     *
     * @return
     * @author SHANHY
     * @create  2016年1月13日
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager, KickoutSessionControlFilter kickoutSessionControlFilter, RoleAuthorizationControlFilter roleAuthorizationControlFilter,
                                              LoginAuthorizationControlFilter loginAuthorizationControlFilter){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();

        filters.put("kickout", kickoutSessionControlFilter);
        filters.put("role", roleAuthorizationControlFilter);
        filters.put("login", loginAuthorizationControlFilter);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/user/v1.0.0/getAccountInfo", "anon");
        filterChainDefinitionMap.put("/**", "anon");

        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager sessionManager, ShiroAuthorizingRealmService myRealm, EhCacheManager shiroEhcacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(shiroEhcacheManager);
        return securityManager;
    }



    @Bean("myRealm")
    public ShiroAuthorizingRealmService myRealm() {
        ShiroAuthorizingRealmService myRealm = new ShiroAuthorizingRealmService();
        return myRealm;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }
}