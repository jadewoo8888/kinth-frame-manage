package com.kinth.frame.mange.web.controller.auth;

import java.util.Collection;

public class UserRole {
	
	private String id;
	private String name;
	//private Collection<AuthorityMenu> authorityMenus;
	//private Collection<PermissionMenu> permissionMenus;
	
	public UserRole(String id, String name){
		this.id=id;
		this.name=name;
	}
	
	public void setId(String id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	/*public void setPermissionMenus(Collection<PermissionMenu> permissionMenus){
		this.permissionMenus=permissionMenus;
	}
	public void setAuthorityMenus(Collection<AuthorityMenu> authorityMenus){
		this.authorityMenus=authorityMenus;
	}*/
	
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	/*public Collection<PermissionMenu> getPermissionMenus(){
		return this.permissionMenus;
	}
	public Collection<AuthorityMenu> getAuthorityMenus(){
		return this.authorityMenus;
	}*/
	
}
