package com.kinth.frame.manage.web.model;


public class RoleBindModel {

	private String name;	
	private String[] authorityIds; 
	//private Integer[] authorityIds;

	public void setName(String name){
		this.name=name;
	}		
	public void setAuthorityIds(String[] authorityIds){
		this.authorityIds=authorityIds;
	}
		
	public String getName(){
		return this.name;
	}
	public String[] getAuthorityIds(){
		return this.authorityIds;
	}
	
	/*public String getAuthorityIdsString(){
		return ArrayHelper.toString(authorityIds, ",");
	}*/
	
}
