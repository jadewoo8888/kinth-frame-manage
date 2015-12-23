package com.kinth.frame.manage.mapper;

import java.util.List;

import com.kinth.frame.manage.domain.RoleAuthorityKey;

public interface RoleAuthorityMapper {
    int deleteByPrimaryKey(RoleAuthorityKey key);

    int insert(RoleAuthorityKey record);

    int insertSelective(RoleAuthorityKey record);
    
    int deleteRoleAuthorities(String roleId);
    
    void batchInsertRoleAuthorities(List<RoleAuthorityKey> roleAuthorities);
}