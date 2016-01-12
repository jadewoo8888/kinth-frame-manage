package com.kinth.frame.mange.web.model;


public class RoleEditModel {
	private String id;
	private String name;	
	
	public void setId(String id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}	
	
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
}
