package com.my.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.my.common.vo.Node;
import com.my.pojo.SysMenu;
@Mapper
public interface SysMenuDao {
	public List<Map<String, Object>> findObjects();
	/**查询子菜单*/
	@Select("select count(1) from sys_menus where parentId=#{id}")
	public int getChildCount(Integer id);
	/**删除菜单*/
	@Delete("delete from sys_menus where id=#{id}")
	public int deleteObject(Integer id);
	//查询节点信息组成父子树
	public List<Node> findZtreeMenuNodes();
	public int insertObject(SysMenu entity);
	public int updateObject(SysMenu entity);
	List<String> findPermissions(@Param("menuIds") Integer[] menuIds);

}
