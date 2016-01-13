package com.kinth.frame.mange.web.model.extension;

import java.util.UUID;

import com.kinth.frame.manage.domain.Authority;
import com.kinth.frame.manage.service.model.AuthoritySearch;
import com.kinth.frame.mange.web.model.AuthorityEditModel;
import com.kinth.frame.mange.web.model.AuthoritySearchModel;

public class AuthorityModelExtension {
	public static AuthoritySearch toAuthoritySearch(AuthoritySearchModel searchModel){
		AuthoritySearch ret=new AuthoritySearch();
		ret.setName(searchModel.getName());
		
		return ret;
	}
	
	public static Authority toAuthority(AuthorityEditModel editModel){
		Authority ret =new Authority();
		ret.setId(UUID.randomUUID().toString());
		ret.setName(editModel.getName());
		//ret.setPosition(editModel.getPosition());
		//ret.setTheValue(editModel.getTheValue());
		ret.setUrl(editModel.getUrl());
		ret.setMatchUrl(editModel.getMatchUrl());
		ret.setItemIcon(editModel.getItemIcon());
		//ret.setEnable(true);
		//ret.setVersion(0);
		if(editModel.getParentId()!=null && !editModel.getParentId().equals("") ){
			/*Authority parent=new Authority();
			parent.setId(editModel.getParentId());
			ret.setParent(parent);*/
			ret.setParentId(editModel.getParentId());
		}
		
		return ret;
	}
	
	public static AuthorityEditModel toAuthorityEditModel(Authority model){
		AuthorityEditModel ret=new AuthorityEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		//ret.setPosition(model.getPosition());
		//ret.setTheValue(model.getTheValue());
		ret.setUrl(model.getUrl());
		ret.setMatchUrl(model.getMatchUrl());
		ret.setItemIcon(model.getItemIcon());
		ret.setSort(model.getSort());
		if(model.getParentId()!=null)
			ret.setParentId(model.getParentId());
		
		return ret;
	}
}