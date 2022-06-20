package com.my.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysLogDaoTest {
	@Autowired
	private SysLogDao sysLogDao;
	@Test
	public void testFindLogsPageByUserName() {
		
		System.out.println(sysLogDao.deleteByIds(new Integer[] {1,2,3,4}));
	}

}
