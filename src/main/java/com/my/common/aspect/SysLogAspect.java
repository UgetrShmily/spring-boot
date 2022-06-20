package com.my.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.MissingNode;
import com.my.common.annotation.LogOperation;
import com.my.common.util.IpUtils;
import com.my.pojo.SysLog;
import com.my.service.SysLogService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Aspect
@Component
public class SysLogAspect {
	@Around("bean(sysUserServiceImpl)")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		Object result = null;

		Long start=System.currentTimeMillis();
		result = jp.proceed();
		Long end=System.currentTimeMillis();
		writedLog(jp,end-start);

		return result;
	}
	@Autowired
	private SysLogService sysLogService;
	private void writedLog(ProceedingJoinPoint jp,Long time) {
		//用户名
		String username="admin";
		//用户操作
		MethodSignature signature = (MethodSignature)jp.getSignature();
		LogOperation annotation = signature.getMethod().getAnnotation(com.my.common.annotation.LogOperation.class);
		String operation="待定";
		if(annotation!=null)operation=annotation.operation();
		//请求方法
		String method=jp.getSignature().toString();
		//请求参数
		String params=Arrays.toString(jp.getArgs());
		System.out.println(Arrays.toString(jp.getArgs()));
		//封装日志
		SysLog sysLog=new SysLog();
		sysLog.setIp(IpUtils.getIpAddr())
			  .setMethod(method)
			  .setOperation(operation)
			  .setParams(params)
			  .setTime(time)
			  .setUsername(username);
		//写入数据库
		sysLogService.saveLogs(sysLog);
	}
}
