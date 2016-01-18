package com.kinth.frame.manage.web.model.extension;

import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.web.model.RoleBindModel;

public class RoleBindModelExtension {
	
	public static RoleBindModel toRoleBindModel(Role role){
		RoleBindModel ret=new RoleBindModel();
		ret.setName(role.getName());
		
		return ret;
	}
	
}
