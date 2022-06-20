package com.my.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class SysDeptCacheAspect {
	private Map<String, Object> cacheMap=new ConcurrentHashMap<>();
	@Around("@annotation(com.my.common.annotation.RequiredCach)")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("获取缓存");
		Object cacheObj=cacheMap.get("dept.findObjects");
		if(cacheObj!=null)return cacheObj;
		Object resurt=jp.proceed();
		System.out.println("写入缓存");
		cacheMap.put("dept.findObjects", resurt);
		return resurt;
	}
	@AfterReturning("@annotation(com.my.common.annotation.RequiredClearCach)")
	public void afterReturing() {
		cacheMap.remove("dept.findObjects");
		System.out.println("缓存清除");
	}
}
