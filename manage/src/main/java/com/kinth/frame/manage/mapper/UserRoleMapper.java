package com.kinth.frame.manage.mapper;

import com.kinth.frame.manage.domain.UserRoleKey;

public interface UserRoleMapper {
	
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);
    
    void deleteUserRoles(String userId);
    
    int selRoleUserCount(String roleId);
    
}