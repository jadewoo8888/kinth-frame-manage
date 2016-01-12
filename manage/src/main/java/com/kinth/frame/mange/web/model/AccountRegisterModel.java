package com.kinth.frame.mange.web.model;

import javax.validation.constraints.AssertTrue;


public class AccountRegisterModel {
	private String name;
	private String email;
	private String username;
	private String password;
	private String confirmPassword;
	private boolean agreement;
	
	public void setName(String name){
		this.name=name;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword=confirmPassword;
	}
	public void setAgreement(boolean agreement){
		this.agreement=agreement;
	}
	
	public String getName(){
		return this.name;
	}
	public String getEmail(){
		return this.email;
	}
	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public String getConfirmPassword(){
		return this.confirmPassword;
	}
	public boolean getAgreement(){
		return this.agreement;
	}
}
