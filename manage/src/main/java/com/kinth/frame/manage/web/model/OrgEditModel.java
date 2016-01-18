package com.kinth.frame.manage.web.model;


public class OrgEditModel {

	private String id;
	private String name;	
	private int sort;
	private String parentId;

	public void setId(String id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
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
	public String getParentId(){
		return this.parentId;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
