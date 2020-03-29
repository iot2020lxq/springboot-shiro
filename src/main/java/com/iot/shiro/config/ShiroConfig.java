package com.iot.shiro.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 16:08
 */
@Configuration
public class ShiroConfig {

    /*
     * cookie对象
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
    	SimpleCookie simpleCookie = new SimpleCookie();
    	simpleCookie.setMaxAge(60);
    	simpleCookie.setName("rememberMe");
    	return simpleCookie;
    }

	/*
	 * cookie管理器
	 */
    @Bean
    public CookieRememberMeManager cookieManager() {
    	CookieRememberMeManager manager = new CookieRememberMeManager();
    	manager.setCookie(rememberMeCookie());
    	return manager;
    }
    
	
    /*
        创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //shiro内置过滤器
        /*
        Shiro内置过滤器，可以实现权限相关的拦截器
            常用的过滤器：
                anon：无需认证（登录）可以访问
                authc：必须认证才可以访问
                user：如果使用rememberMe的功能可以直接访问
                perms：该资源必须得到资源权限才可以访问
                role：该资源必须得到角色权限才可以访问
         */

        Map<String,String> filterMap = new LinkedHashMap<>();
        
        //记住我
        filterMap.put("/list.html","user");
        
        //不需要拦截
        filterMap.put("/login","anon");
        filterMap.put("/index.html","anon");
        filterMap.put("/index","anon");
        filterMap.put("/","anon");

        //需要授权的拦截器
        filterMap.put("/add.html","authc,perms[user:add]");
        filterMap.put("/update.html","authc,perms[user:update]");
        
        //需要角色认证的
        filterMap.put("/admin.html", "roles[admin]");
        
        //退出
        filterMap.put("/logout","logout");
        //拦截所有
        filterMap.put("/*","authc");


        //修改认证拦截后跳转到的登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        //设置授权拦截的跳转地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }


    /*
        创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("authenticator") ModularRealmAuthenticator authenticator,
        @Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联Realm
        defaultWebSecurityManager.setRealm(userRealm);
//        defaultWebSecurityManager.setAuthenticator(authenticator);
        
        defaultWebSecurityManager.setRememberMeManager(cookieManager());
        return defaultWebSecurityManager;
    }

    /*
        创建认证器，可以认证多个Realm
     */;
     @Bean(name = "authenticator")
    public ModularRealmAuthenticator getAuthenticator(@Qualifier("userRealm") UserRealm userRealm,
         /*@Qualifier("userRealm2") UserRealm2 userRealm2,*/@Qualifier("strategy") AtLeastOneSuccessfulStrategy strategy){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        List<Realm> reamls = new ArrayList<>();
        reamls.add(userRealm);
//        reamls.add(userRealm2);
        authenticator.setRealms(reamls);

        //设置认证策略
        authenticator.setAuthenticationStrategy(strategy);
        
        return authenticator;
    }

    /*
        认证策略
     */
/*    @Bean(name = "strategy")
    public AllSuccessfulStrategy getStrategy(){
        AllSuccessfulStrategy strategy = new AllSuccessfulStrategy();
        return strategy;
    }*/

    @Bean(name = "strategy")
    public AtLeastOneSuccessfulStrategy getStratery(){
        AtLeastOneSuccessfulStrategy strategy = new AtLeastOneSuccessfulStrategy();
        return strategy;
    }

    /*
        创建Realm
     */
    @Bean(name="userRealm")
    public UserRealm getUserRealm(@Qualifier("matcher") HashedCredentialsMatcher matcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthorizationCachingEnabled(false);
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    /*
        MD5加密
     */
    @Bean(name="matcher")
    public HashedCredentialsMatcher getCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        return credentialsMatcher;
    }


    /*
        创建Realm2
     */
//    @Bean(name="userRealm2")
//    public UserRealm2 getUserRealm2(@Qualifier("matcher2") HashedCredentialsMatcher matcher2){
//        UserRealm2 userRealm2 = new UserRealm2();
//        userRealm2.setAuthorizationCachingEnabled(false);
//        userRealm2.setCredentialsMatcher(matcher2);
//        return userRealm2;
//    }
//
//    /*
//        SHA1加密
//     */
//    @Bean("matcher2")
//    public HashedCredentialsMatcher getCredentialsMatcher2(){
//        HashedCredentialsMatcher credentialsMatcher2 = new HashedCredentialsMatcher();
//        credentialsMatcher2.setHashAlgorithmName("SHA1");
//        return credentialsMatcher2;
//    }

    /**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}
}
