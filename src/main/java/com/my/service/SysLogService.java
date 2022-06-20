package com.my.service;

import org.apache.ibatis.annotations.Param;

import com.my.pojo.SysLog;
import com.my.vo.PageObject;

public interface SysLogService {
	/**
	 * 查询页面数据
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysLog> findPageObject(String username,Integer pageCurrent);
	/**根据id删除日志*/
	Integer deleteByIds(@Param("ids") Integer... ids);
	Integer saveLogs(SysLog entity);
}
