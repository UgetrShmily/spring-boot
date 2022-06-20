package com.my.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.my.common.config.PaginationProperties;
import com.my.common.exception.ServiceException;
import com.my.dao.SysLogDao;
import com.my.pojo.SysLog;
import com.my.service.SysLogService;
import com.my.vo.PageObject;
@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private PaginationProperties paginationProperties;
	@Override
	public PageObject<SysLog> findPageObject(String username, Integer pageCurrent) {
		//1.数据校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值不正确");
		//2.查询符号条件的记录数
		int rowCount=sysLogDao.getRowCount(username);
		//3.验证结果，如果没记录则不再执行
		if(rowCount==0)
			throw new ServiceException("没查寻到日志记录");
		//4.查询记录
		int pageSize=paginationProperties.getPageSize();
		System.out.println(pageSize);
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> list=sysLogDao.findLogsPageByUserName(username, startIndex, pageSize);
		//数据封装
		PageObject<SysLog> pageObject=new PageObject<>(pageCurrent, pageSize, rowCount, list);
		return pageObject;
	}
	@Override
	public Integer deleteByIds(Integer... ids) {
		// 参数校验
		if(ids==null || ids.length<1 )
			throw new IllegalArgumentException("删除日志列表为空");
		int rows;
		try {
			rows=sysLogDao.deleteByIds(ids);
		} catch (Exception e) {
			throw new ServiceException("删除失败");
		}
		if(rows==0)
			throw new ServiceException("记录不存在，或已经被删除");
		return rows;
	}
	@Override
	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer saveLogs(SysLog entity) {
		if(entity==null)
			throw new ServiceException("日志信息为空");
		//模拟耗时操作
		try {Thread.sleep(6000);} catch (InterruptedException e) {e.printStackTrace();}
		return sysLogDao.insert(entity);
	}
}
