package com.my.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringShiroConfig {
	@Bean
	public CacheManager shiroCacheManager(){
		return new MemoryConstrainedCacheManager();
	}
	@Bean
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager cManager=new CookieRememberMeManager();
		SimpleCookie cookie=new SimpleCookie("rememberMe");
		cookie.setMaxAge(10*60);
		cManager.setCookie(cookie);
		return cManager;
	}

	@Bean   
	public SessionManager sessionManager() {
		DefaultWebSessionManager sManager=new DefaultWebSessionManager();
		sManager.setGlobalSessionTimeout(60*60*1000);
		return sManager;
	}

	@Bean
	public SecurityManager securityManager(Realm realm,CacheManager cacheManager,RememberMeManager rememberMeManager,SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(cacheManager);
		securityManager.setRememberMeManager(rememberMeManager);
		securityManager.setSessionManager(sessionManager);
		return securityManager;	
	} 
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(@Autowired SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean=new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		//?????????????????????????????????????????????url
		sfBean.setLoginUrl("/doLoginUI");
		//??????map????????????????????????(??????????????????????????????,????????????????????????)
		LinkedHashMap<String,String> map=new LinkedHashMap<>();
		//??????????????????????????????:"anon"
		map.put("/bower_components/**","anon");
		map.put("/build/**","anon");
		map.put("/dist/**","anon");
		map.put("/plugins/**","anon");
		map.put("/user/doLogin","anon");
		map.put("/doLogout","logout");
		//???????????????????????????,??????????????????("authc")?????????
		map.put("/**","user");//authc
		sfBean.setFilterChainDefinitionMap(map);
		return sfBean;
	}

}
