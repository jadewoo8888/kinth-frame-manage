package com.kinth.frame.mange.web.model.extension;

import com.kinth.frame.manage.domain.User;
import com.kinth.frame.mange.web.model.UserAuthorizeModel;

public class UserAuthorizeModelExtension {
	
	public static UserAuthorizeModel toUserBindModel(User user){
		UserAuthorizeModel ret=new UserAuthorizeModel();
		ret.setLoginName(user.getLoginName());
		ret.setRealName(user.getRealName());
		
		if(user.getOrgId()!=null) {
			ret.setOrgId(user.getOrgId());
		}
		
		return ret;
	}
	
}
