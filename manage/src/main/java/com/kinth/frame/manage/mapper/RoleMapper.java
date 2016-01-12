package com.kinth.frame.manage.mapper;

import java.util.List;
import java.util.Map;

import com.kinth.frame.manage.domain.Role;

public interface RoleMapper {
	int deleteByPrimaryKey(String id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	List<Role> selectAll();

	List<Role> selectByPage(Map<String, Object> paramMap);

	int selcount(Map<String, Object> paramMap);
	
	List<Role> selectUserRole(String userId);
}