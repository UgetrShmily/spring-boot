package com.my.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.my.dao.SysMenuDao;
import com.my.dao.SysRoleMenuDao;
import com.my.dao.SysUserDao;
import com.my.dao.SysUserRoleDao;
import com.my.pojo.SysUser;
@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	/**
	 * 设置凭证匹配器
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("MD5");
		matcher.setHashIterations(1);
		super.setCredentialsMatcher(matcher);
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		Integer userId=user.getId();
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(userId);
		if(roleIds==null || roleIds.size()==0)throw new AuthorizationException();
		Integer[] array = {};
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		if(menuIds==null||menuIds.size()==0)throw new AuthorizationException();
		List<String> permissions=sysMenuDao.findPermissions(menuIds.toArray(array));
		Set<String> set=new HashSet<>();
		for(String s:set) {
			if(!StringUtils.isEmpty(s)) {
				set.add(s);
			}
		}
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt=(UsernamePasswordToken)token;
		String username=upt.getUsername();
		SysUser user=sysUserDao.findUserByUserName(username);
		//用户是否存在
		if(user==null)throw new UnknownAccountException();
		//用户是否禁用
		if(user.getValid()==0)throw new LockedAccountException();
		ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
		return new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,getName());
	}

}
