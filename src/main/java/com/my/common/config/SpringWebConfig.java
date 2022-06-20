package com.my.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.my.common.web.TimeAccessInterceptor;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeAccessInterceptor()).addPathPatterns("/user/doLogin");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
