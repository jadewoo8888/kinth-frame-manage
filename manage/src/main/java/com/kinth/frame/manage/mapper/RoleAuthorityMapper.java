package com.kinth.frame.manage.mapper;

import com.kinth.frame.manage.domain.RoleAuthorityKey;

public interface RoleAuthorityMapper {
    int deleteByPrimaryKey(RoleAuthorityKey key);

    int insert(RoleAuthorityKey record);

    int insertSelective(RoleAuthorityKey record);
}