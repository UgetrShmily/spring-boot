package com.my.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysRoleDaoTest {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Test
	public void testGetRowCount() {
		System.out.println(sysRoleDao.getRowCount(null));
	}
}
