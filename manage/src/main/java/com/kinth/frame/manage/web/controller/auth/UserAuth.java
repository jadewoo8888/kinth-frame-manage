package com.kinth.frame.manage.web.controller.auth;

import java.util.List;
import java.util.Set;

import com.kinth.frame.manage.domain.Role;

public class UserAuth {
	
	private String id;
	private String loginName;
	private String realName;
	//private List<UserRole> userRoles;
	private Set<AuthorityMenu> authorityMenus;
	private Set<PermissionMenu> permissionMenus;
	private List<Role> roles;
	private UserRole currRole;
	
	public UserAuth(String id, String loginName, String realName){
		this.id=id;
		this.loginName=loginName;
		this.realName=realName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Set<AuthorityMenu> getAuthorityMenus() {
		return authorityMenus;
	}

	public void setAuthorityMenus(Set<AuthorityMenu> authorityMenus) {
		this.authorityMenus = authorityMenus;
	}

	public Set<PermissionMenu> getPermissionMenus() {
		return permissionMenus;
	}

	public void setPermissionMenus(Set<PermissionMenu> permissionMenus) {
		this.permissionMenus = permissionMenus;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UserRole getCurrRole() {
		return currRole;
	}

	public void setCurrRole(UserRole currRole) {
		this.currRole = currRole;
	}

	/*public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}*/
	
	
	
}
