package com.kinth.frame.mange.web.controller.auth;

public class PermissionMenu {
	
	public String rootId;
	public String rootName;
	public String subId;
	public String subName;
	public String curName;
	public String permission;
	
	public PermissionMenu(String rootId, String rootName, String subId, String subName, String curName, String permission){
		this.rootId=rootId;
		this.rootName=rootName;
		this.subId=subId;
		this.subName=subName;
		this.curName=curName;
		this.permission=permission;
	}
	
	public void setRootId(String rootId){
		this.rootId=rootId;
	}
	public void setRootName(String rootName){
		this.rootName=rootName;
	}
	public void setSubId(String subId){
		this.subId=subId;
	}
	public void setSubName(String subName){
		this.subName=subName;
	}
	public void setCurName(String curName){
		this.curName=curName;
	}
	public void setPermission(String permission){
		this.permission=permission;
	}
	
	public String getRootId(){
		return this.rootId;
	}
	public String getRootName(){
		return this.rootName;
	}
	public String getSubId(){
		return this.subId;
	}
	public String getSubName(){
		return this.subName;
	}
	public String getCurName(){
		return this.curName;
	}
	public String getPermission(){
		return this.permission;
	}
	
}
