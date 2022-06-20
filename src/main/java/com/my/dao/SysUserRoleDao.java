package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDao {
	@Delete("delete from sys_user_roles where role_id=#{roleId}")
	public int deleteObjectsByRoleId(Integer roleId);
	public int insertObjects(@Param("userId")Integer userId,@Param("roleIds")Integer[] roleIds);
	public List<Integer> findRoleIdsByUserId(Integer id);
	public int deleteObjectsByUserId(Integer userId);
}
