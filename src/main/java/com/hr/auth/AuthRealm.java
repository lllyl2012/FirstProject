package com.hr.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hr.model.Permission;
import com.hr.model.Role;
import com.hr.model.User;
import com.hr.service.UserService;


public class AuthRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = (User)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
		List<String> permissionList = new ArrayList<>();//权限
		List<String> roleNameList = new ArrayList<>();//角色
		Set<Role> roleSet = user.getRoles();
		if(!CollectionUtils.isEmpty(roleSet)) {
			for(Role role: roleSet) {
				roleNameList.add(role.getRname());
				Set<Permission> permissionSet = role.getPermissions();
				if(!CollectionUtils.isEmpty(permissionSet)) {
					for(Permission permission : permissionSet) {
						permissionList.add(permission.getName());
					}
				}
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissionList);
		info.addRoles(roleNameList);
		return info;
	}

	//认证登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		User user = userService.findByUsername(username);
		
		return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
	}
	
}
