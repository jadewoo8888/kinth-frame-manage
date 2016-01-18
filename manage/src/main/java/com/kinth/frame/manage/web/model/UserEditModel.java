package com.kinth.frame.manage.web.model;

import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.NotEmpty;


public class UserEditModel {
	@NotNull(message="{用户id不能为空}")
	private String id;
	//@NotEmpty(message="{用户name不能为空}")
	private String loginName;
	private String email;
	private String realName;
	private String password;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
