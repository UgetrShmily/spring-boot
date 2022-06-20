package com.my.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.my.pojo.SysLog;

@Mapper
public interface SysLogDao {
	/**
	 * 根据用户名查询相关记录数
	 * @return
	 */
	public int getRowCount(@Param("username") String username);
	/**
	 * 根据用户名查询日志分页信息
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysLog> findLogsPageByUserName(@Param("username") String username,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
	/**删除*/
	public int deleteByIds(@Param("ids") Integer... ids);
	public int insert(SysLog entity);
	
}
