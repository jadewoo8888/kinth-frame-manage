package com.kinth.frame.manage.web.model.extension;


import com.kinth.frame.manage.domain.User;
import com.kinth.frame.manage.web.model.UserEditModel;

public class UserModelExtension {

	public static User toUser(UserEditModel editModel) {
		User user = new User();
		//account.setId(editModel.getId() == null ? UUID.randomUUID().toString() : editModel.getId());
		user.setEmail(editModel.getEmail());
		user.setEnable(false);
		user.setLoginName(editModel.getLoginName());
		//account.setOrgId(null);
		user.setPassword(editModel.getPassword());
		//account.setRegisterTime(Calendar.getInstance().getTime());
		user.setRealName(editModel.getRealName());
		//account.setRoleId(null);
		return user;
	}
	
	public static UserEditModel toUserEditModel(User model){
		UserEditModel ret = new UserEditModel();
		ret.setId(model.getId());
		ret.setLoginName(model.getLoginName());
		ret.setEmail(model.getEmail());
		ret.setPassword(model.getPassword());
		ret.setRealName(model.getRealName());
		return ret;
	}
}
