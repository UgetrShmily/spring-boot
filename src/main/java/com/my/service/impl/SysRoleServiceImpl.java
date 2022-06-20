package com.my.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.my.common.config.PaginationProperties;
import com.my.common.exception.ServiceException;
import com.my.common.vo.CheckBox;
import com.my.dao.SysRoleDao;
import com.my.dao.SysRoleMenuDao;
import com.my.dao.SysUserRoleDao;
import com.my.pojo.SysRole;
import com.my.service.SysRoleService;
import com.my.vo.PageObject;
import com.my.vo.SysRoleMenuVo;
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private PaginationProperties paginationProperties;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.对参数进行校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数并进行校验
		int rowsCount=sysRoleDao.getRowCount(name);
		if(rowsCount==0)
			throw new ServiceException("没有查询到记录");
		//3.查询当前页记录
		int pageSize=paginationProperties.getPageSize();
		System.out.println("======================"+pageSize);
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//封装数据
		return new PageObject<>(pageCurrent, pageSize, rowsCount, records);
	}
	@Override
	@Transactional
	public int deleteObject(Integer id) {
		//1.验证数据的合法性
		if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
		//2.基于id删除关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		int rows;
		try{
			rows=sysRoleDao.deleteObject(id);
		}catch(Throwable t) {
			t.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Transactional
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.参数有效性校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回业务结果
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("id的值不合法");
		//2.执行查询
		SysRoleMenuVo result=sysRoleDao.findObjectById(id);
		//3.验证结果并返回
		if(result==null)
			throw new ServiceException("此记录已经不存在");
		return result;
	}
	@Override
	@Transactional
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.合法性验证
    	if(entity==null)
        throw new ServiceException("更新的对象不能为空");
    	if(entity.getId()==null)
    	throw new ServiceException("id的值不能为空");
    	
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new ServiceException("角色名不能为空");
    	if(menuIds==null||menuIds.length==0)
    	throw new ServiceException("必须为角色指定一个权限");
    	
    	//2.更新数据
    	int rows=sysRoleDao.updateObject(entity);
    	if(rows==0)
        throw new ServiceException("对象可能已经不存在");
    	sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
    	sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
    	//3.返回结果
    	return rows;
	}
	@Override
	public List<CheckBox> findRoles() {
		return sysRoleDao.findObjects();
	}
}
