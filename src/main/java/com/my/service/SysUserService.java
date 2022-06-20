package com.my.service;

import java.util.Map;

import com.my.pojo.SysUser;
import com.my.vo.PageObject;
import com.my.vo.SysUserDeptVo;

public interface SysUserService {
	public PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);
	public int validById(Integer id,Integer valid,String modifiedUser);
	public int saveObject(SysUser entity,Integer[]roleIds);
	public Map<String,Object> findObjectById(Integer userId) ;
	public int updateObject(SysUser entity,Integer[] roleIds);
	public int updatePassword(String password,String newPassword,String cfgPassword);

}
