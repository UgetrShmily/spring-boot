package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.my.common.vo.CheckBox;
import com.my.pojo.SysUser;
import com.my.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	public int getRowCount(@Param("username") String username);
	public List<SysUserDeptVo> findPageObjects(
			@Param("username")String  username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	public int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
	public int insertObject(SysUser entity);
	public SysUserDeptVo findObjectById(Integer id);
	public int updateObject(SysUser entity);
	public int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);
	public SysUser findUserByUserName(String username);
}
