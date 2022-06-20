package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.my.common.vo.CheckBox;
import com.my.pojo.SysRole;
import com.my.vo.SysRoleMenuVo;
@Mapper
public interface SysRoleDao {
	public int getRowCount(@Param("name") String name);
	public List<SysRole> findPageObjects(@Param("name")String  name,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	@Delete("delete from sys_roles where id=#{id}")
	public int deleteObject(Integer id);
	public int insertObject(SysRole entity);
	/**根据角色id查询角色菜单数据*/
	public SysRoleMenuVo findObjectById(Integer id);
	public int updateObject(SysRole entity);
	public List<CheckBox> findObjects();
}
