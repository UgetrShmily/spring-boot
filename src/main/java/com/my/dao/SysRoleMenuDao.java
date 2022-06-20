package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {
	@Delete("delete from sys_role_menus where menu_id=#{menuId}")
	public int deleteObjectsByMenuId(Integer menuId);
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	public int deleteObjectsByRoleId(Integer roleId);
	public int insertObjects(@Param("roleId")Integer roleId,@Param("menuIds")Integer[] menuIds);
	public List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);

}
