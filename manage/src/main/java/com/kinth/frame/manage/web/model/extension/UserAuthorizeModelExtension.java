package com.kinth.frame.manage.web.model.extension;

import com.kinth.frame.manage.domain.User;
import com.kinth.frame.manage.web.model.UserAuthorizeModel;

public class UserAuthorizeModelExtension {
	
	public static UserAuthorizeModel toUserBindModel(User user){
		UserAuthorizeModel ret=new UserAuthorizeModel();
		ret.setLoginName(user.getLoginName());
		ret.setRealName(user.getRealName());
		ret.setId(user.getId());
		if(user.getOrgId()!=null) {
			ret.setOrgId(user.getOrgId());
		}
		
		return ret;
	}
	
}
