package com.my.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.my.common.exception.ServiceException;
import com.my.common.vo.Node;
import com.my.dao.SysMenuDao;
import com.my.dao.SysRoleMenuDao;
import com.my.pojo.SysMenu;
import com.my.service.SysMenuService;
/**
 * 有事务
 * @author A2
 *
 */
@Service
public class SysMenuServiceImpl implements SysMenuService{
	private static Logger log=LoggerFactory.getLogger(SysMenuServiceImpl.class);
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	@Cacheable(value = "userCache")
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if(list==null)
			throw new ServiceException("没有查询到任何菜单");
		return list;
	}
	@Override
	@Transactional
	@CacheEvict(value = "userCache",allEntries = true,beforeInvocation = false)
	public int deleteObject(Integer id) {
		//参数校验
		if(id==null)
			throw new IllegalArgumentException("请先选择");
		//查询有没有子菜单，有则拒绝删除
		int count=sysMenuDao.getChildCount(id);
		if(count>0)
			throw new ServiceException("请先删除子菜单");
		//删除菜单角色信息
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//删除菜单
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("菜单可能不存在");
		return rows;
	}
	@Override
	@Cacheable(value = "userCache")
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	@Override
	@CacheEvict(value = "userCache",allEntries = true,beforeInvocation = false)
	public int saveObject(SysMenu entity) {
		if(entity==null)
			throw new ServiceException("保存对象为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows;
		try {
			rows=sysMenuDao.insertObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
	}
	@Override
	@CacheEvict(value = "userCache",beforeInvocation = false,allEntries = true)
	public int updateObject(SysMenu entity) {
		if(entity==null)
			throw new ServiceException("更新内容为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows;
		try {
			rows=sysMenuDao.updateObject(entity);
		} catch (Exception e) {
			log.info(e.toString());
			throw new ServiceException("更新失败");
		}
		return rows;
	}
}
