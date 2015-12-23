package com.kinth.frame.manage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.manage.domain.RoleAuthorityKey;
import com.kinth.frame.manage.mapper.RoleAuthorityMapper;


@Service("roleAuthorityService")
public class RoleAuthorityService {

	private @Resource RoleAuthorityMapper roleAuthorityMapper;
	
	
	
	public void saveAuthorize(String roleId, String[] authorityIds) throws ValidatException, EntityOperateException {
		roleAuthorityMapper.deleteRoleAuthorities(roleId);
		List<RoleAuthorityKey> roleAuthorities = new ArrayList<RoleAuthorityKey>();
		if(authorityIds.length>0){	
			RoleAuthorityKey record = null;
			for(String authId : authorityIds){
				record = new RoleAuthorityKey();
				record.setRoleId(roleId);
				record.setAuthId(authId);
				roleAuthorities.add(record);
			}
			roleAuthorityMapper.batchInsertRoleAuthorities(roleAuthorities);
		}
		
	}
}
