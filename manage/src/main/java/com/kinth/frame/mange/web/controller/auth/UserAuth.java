package com.kinth.frame.mange.web.controller.auth;

import java.util.List;

public class UserAuth {
	
	private String id;
	private String loginName;
	private String realName;
	private List<UserRole> userRoles;
	
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

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
	
}
