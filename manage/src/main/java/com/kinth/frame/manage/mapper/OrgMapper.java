package com.kinth.frame.manage.mapper;

import java.util.List;

import com.kinth.frame.manage.domain.Org;

public interface OrgMapper {
    int deleteByPrimaryKey(String id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);
    
    List<Org> selectRootOrgs();
    
    List<Org> selectChildOrgs(String parentId);
}