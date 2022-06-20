package com.my.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.my.common.annotation.LogOperation;
import com.my.common.config.PaginationProperties;
import com.my.common.exception.ServiceException;
import com.my.dao.SysUserDao;
import com.my.dao.SysUserRoleDao;
import com.my.pojo.SysUser;
import com.my.service.SysUserService;
import com.my.vo.PageObject;
import com.my.vo.SysUserDeptVo;
@Service
@Transactional(timeout = 60)
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private PaginationProperties paginationProperties;
	@Override
	@LogOperation(operation = "查询用户")
	@Transactional(readOnly = true)
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.对参数进行校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数并进行校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("没有找到对应记录");
		int pageSize=paginationProperties.getPageSize();
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records=
				sysUserDao.findPageObjects(username,
						startIndex, pageSize);
		//4.对查询结果进行封装并返回
		return new PageObject<>(pageCurrent, pageSize, rowCount, records);

	}
	@Override
	@RequiresPermissions("sys:user:update")
	@LogOperation(operation = "禁用启用用户")
	public int validById(Integer id,Integer valid,String modifiedUser) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("参数不合法,id="+id);
		if(valid!=1&&valid!=0)
			throw new ServiceException("参数不合法,valie="+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启用操作
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		//3.判定结果,并返回
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}
	@Override
	@LogOperation(operation = "新建用户")
	@Transactional
	public int saveObject(SysUser entity, Integer[] roleIds) {
		if(entity==null)
			throw new ServiceException("用户为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");

		//密码加密
		String source=entity.getPassword();
		String salt=UUID.randomUUID().toString();
		SimpleHash hash=new SimpleHash("MD5",source,salt,1);
		entity.setSalt(salt);
		entity.setPassword(hash.toHex());
		System.out.println(hash);
		System.out.println(hash.toHex());

		int rows=sysUserDao.insertObject(entity);
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	@Override
	@LogOperation(operation = "查询单一用户")
	public Map<String, Object> findObjectById(Integer userId) {
		//1.合法性验证
		if(userId==null||userId<=0)
			throw new ServiceException("参数数据不合法,userId="+userId);
		//2.业务查询
		SysUserDeptVo user=sysUserDao.findObjectById(userId);
		if(user==null)
			throw new ServiceException("此用户已经不存在");
		List<Integer> roleIds=
				sysUserRoleDao.findRoleIdsByUserId(userId);
		//3.数据封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@Override
	@LogOperation(operation = "更新用户信息")
	@Transactional
	public int updateObject(SysUser entity,Integer[] roleIds) {
		//1.参数有效性验证
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为其指定角色");
		//其它验证自己实现，例如用户名已经存在，密码长度，...
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(),
				roleIds);
		//4.返回结果
		if(rows>0)throw new RuntimeException();//故意异常回滚
		return rows;
	}	
	@Override
	@LogOperation(operation = "修改密码")
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		//1.判定新密码与密码确认是否相同
		if(StringUtils.isEmpty(newPassword))
			throw new IllegalArgumentException("新密码不能为空");
		if(StringUtils.isEmpty(cfgPassword))
			throw new IllegalArgumentException("确认密码不能为空");
		if(!newPassword.equals(cfgPassword))
			throw new IllegalArgumentException("两次输入的密码不相等");
		//2.判定原密码是否正确
		if(StringUtils.isEmpty(password))
			throw new IllegalArgumentException("原密码不能为空");

		//TDO获取当前登录用户
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
		System.out.println(user);
		//验证密码
		SimpleHash sh=new SimpleHash("MD5",password, user.getSalt(), 1);
		if(!user.getPassword().equals(sh.toHex()))
			throw new IllegalArgumentException("原密码不正确");
		//验证通过保存新密码
		//3.对新密码进行加密
		String salt=UUID.randomUUID().toString();
		sh=new SimpleHash("MD5",newPassword,salt, 1);
		//4.将新密码加密以后的结果更新到数据库
		int rows=sysUserDao.updatePassword(sh.toHex(), salt,user.getId());
		if(rows==0)
			throw new ServiceException("修改失败");
		return rows;
	}
}
