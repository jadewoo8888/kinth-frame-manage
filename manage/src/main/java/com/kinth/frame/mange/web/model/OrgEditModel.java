package com.kinth.frame.mange.web.model;

import javax.validation.constraints.NotNull;

public class OrgEditModel {

	private String id;
	private String name;	
	private int position;
	private String theValue;
	private String parentId;

	public void setId(String id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}	
	public void setPosition(int position){
		this.position=position;
	}
	public void setTheValue(String theValue){
		this.theValue=theValue;
	}
	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getPosition(){
		return this.position;
	}
	public String getTheValue(){
		return this.theValue;
	}
	public String getParentId(){
		return this.parentId;
	}
	
}
