package com.kinth.frame.manage.mapper;

import java.util.List;
import java.util.Map;

import com.kinth.frame.manage.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> selectByPage(Map<String, Object> map);
    
    int selcount(Map<String, Object> map);
    
    int userIsExist(String userName);
    
    User selectByLoginNameAndPassword(User user);
}