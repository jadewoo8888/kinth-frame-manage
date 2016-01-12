package com.kinth.frame.mange.web.model;

import javax.validation.constraints.NotNull;

public class AuthorityEditModel {

	private String id;
	private String name;
	private int position;
	private String theValue;
	private String url;
	private String matchUrl;
	private String itemIcon;
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
	public void setUrl(String url){
		this.url=url;
	}
	public void setMatchUrl(String matchUrl){
		this.matchUrl=matchUrl;
	}
	public void setItemIcon(String itemIcon){
		this.itemIcon=itemIcon;
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
	public String getUrl(){
		return this.url;
	}
	public String getMatchUrl(){
		return this.matchUrl;
	}
	public String getItemIcon(){
		return this.itemIcon;
	}
	public String getParentId(){
		return this.parentId;
	}
	
}
