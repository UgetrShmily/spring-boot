package com.my.service;

import java.util.List;

import com.my.common.vo.CheckBox;
import com.my.pojo.SysRole;
import com.my.vo.PageObject;
import com.my.vo.SysRoleMenuVo;

public interface SysRoleService {
	public PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	public int deleteObject(Integer id);
	public int saveObject(SysRole entity,Integer[]menuIds);
	public SysRoleMenuVo findObjectById(Integer id) ;
	public int updateObject(SysRole entity,Integer[] menuIds);
	public List<CheckBox> findRoles();
}
