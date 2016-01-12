package com.kinth.frame.mange.web.model.extension;

import java.util.UUID;

import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.service.model.RoleSearch;
import com.kinth.frame.mange.web.model.RoleEditModel;
import com.kinth.frame.mange.web.model.RoleSearchModel;

public class RoleModelExtension {
	public static RoleSearch toRoleSearch(RoleSearchModel searchModel){
		RoleSearch ret=new RoleSearch();
		ret.setName(searchModel.getName());
		
		return ret;
	}
	
	public static Role toRole(RoleEditModel editModel){
		Role role=new Role();
		role.setId(UUID.randomUUID().toString());
		role.setName(editModel.getName());
		//role.setVersion(1);
		//role.setEnable(false);
		return role;
	}
	
	public static RoleEditModel toRoleEditModel(Role model){
		RoleEditModel ret = new RoleEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		return ret;
	}
	
}
