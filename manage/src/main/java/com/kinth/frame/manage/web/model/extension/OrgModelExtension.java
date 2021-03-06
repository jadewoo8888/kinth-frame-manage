package com.kinth.frame.manage.web.model.extension;

import com.kinth.frame.manage.domain.Org;
import com.kinth.frame.manage.web.model.OrgEditModel;

public class OrgModelExtension {
	
	public static Org toOrg(OrgEditModel editModel){
		Org ret =new Org();
		ret.setId(editModel.getId());
		ret.setName(editModel.getName());
		ret.setSort(editModel.getSort());
		//ret.setPosition(editModel.getPosition());
		//ret.setTheValue(editModel.getTheValue());
		
		if(editModel.getParentId()!=null && !editModel.getParentId().equals("")){
			ret.setParentId(editModel.getParentId());
		}
		
		return ret;
	}
	
	public static OrgEditModel toOrgEditModel(Org model){
		OrgEditModel ret=new OrgEditModel();
		ret.setId(model.getId());
		ret.setName(model.getName());
		ret.setSort(model.getSort());
		//ret.setPosition(model.getPosition());
		//ret.setTheValue(model.getTheValue());
		
		if(model.getParentId()!=null)
			ret.setParentId(model.getParentId());
		
		return ret;
	}
	
}