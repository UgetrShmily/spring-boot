package com.my.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.my.service.SysLogService;

@SpringBootTest
public class SysLogServiceImplTest {
	@Autowired
	private SysLogService sysLogService;
	@Test
	public void testFindPageObject() {
		System.out.println(sysLogService.findPageObject("admin", 1));
	}
}
