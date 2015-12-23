package com.kinth.frame.manage.mapper;

import java.util.List;
import java.util.Map;

import com.kinth.frame.manage.domain.Authority;

public interface AuthorityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<Authority> selectRootAuthoritys();
    
    List<Authority> selectChildAuthoritys(String parentId);
    
    List<Authority> selectByPage(Map<String,Object> paramMap);
    
    int selcount(Map<String,Object> paramMap);
    
    List<Authority> selRoleAuthorities(String roleId);
}