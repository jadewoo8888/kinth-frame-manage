package com.kinth.frame.manage.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.common.helper.StringHelper;
import com.kinth.frame.common.web.helper.PageList;
import com.kinth.frame.common.web.helper.PageListUtil;
import com.kinth.frame.manage.config.SysConfig;
import com.kinth.frame.manage.domain.User;
import com.kinth.frame.manage.domain.UserRoleKey;
import com.kinth.frame.manage.mapper.RoleMapper;
import com.kinth.frame.manage.mapper.UserMapper;
import com.kinth.frame.manage.mapper.UserRoleMapper;

@Service("userService")
public class UserService {

	@Resource UserMapper userMapper;
	@Resource UserRoleMapper userRoleMapper;
	
	public PageList<User> listPage(String name, String username, int pageNo,
			int pageSize) {

		Map<String,Object> param = new HashMap<String,Object>();
		
		if (name != null && !name.isEmpty()) {
			param.put("name", name);
		}
		if (username != null && !username.isEmpty()) {
			param.put("username", username);
		}

		param.put("startIndex", (pageNo - 1) * pageSize);
		param.put("pageSize", pageSize);
		
		int count = userMapper.selcount(param);
		List<User> items = userMapper.selectByPage(param);

		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}
	
	public boolean userIsExist(String loginName) {
    	Integer count = userMapper.userIsExist(loginName);
    	if(count != null && count > 0) {
    		return true;
    	} else {
    		return false;
    	}
	}
	
/*	public void createUser(User user) throws EntityOperateException, ValidatException, NoSuchAlgorithmException {
		user.setPassword(StringHelper.md5(user.getLoginName()+user.getPassword()));
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		userMapper.insert(user);
	}*/
	
	public User login(String loginName, String password) throws NoSuchAlgorithmException {
		User queryUser = new User();
		queryUser.setLoginName(loginName);
		queryUser.setPassword(StringHelper.md5(loginName+password));
		
		User ret = userMapper.selectByLoginNameAndPassword(queryUser);
		
		return ret;
	}
	
	public void updateBind(String id, String roleId, String orgId) throws ValidatException, EntityOperateException {
		User dbUser = userMapper.selectByPrimaryKey(id);
		if(roleId != null && !roleId.equals("")) {
			//dbUser.setRoleId(roleId);
			UserRoleKey userRoleKey = new UserRoleKey();
			userRoleKey.setRoleId(roleId);
			userRoleKey.setUserId(id);
			userRoleMapper.insert(userRoleKey);
		}
		
		if(orgId != null && !orgId.equals("")){
			dbUser.setOrgId(orgId);
		}
		
		userMapper.updateByPrimaryKey(dbUser);
	}
	
	public User getById(String id) throws EntityOperateException {
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}
	
	public void update(User user) throws EntityOperateException, NoSuchAlgorithmException {
		User dbUser = userMapper.selectByPrimaryKey(user.getId());
		if (user.getOrgId() != null && !user.getOrgId().equals("")) { 
			dbUser.setOrgId(user.getOrgId());
		}
		if (user.getEmail() != null && !user.getEmail().equals("")) {
			dbUser.setEmail(user.getEmail());
		}
		if (user.getRealName() != null && !user.getRealName().equals("")) {
			dbUser.setRealName(user.getRealName());
		}
		if (user.getPassword() != null && !user.getPassword().equals("")) {
			dbUser.setPassword(StringHelper.md5(user.getRealName()+user.getPassword()));
		}
		/*if (account.getUsername() != null && !account.getUsername().equals("")) {
			dbAccount.setUsername(account.getUsername());
		}*/
		if (user.getTelphone() != null && !user.getTelphone().equals("")) {
			dbUser.setTelphone(user.getTelphone());
		}
		
		userMapper.updateByPrimaryKey(dbUser);
	}
	
	public void deleteById(String id) throws EntityOperateException{
		userMapper.deleteByPrimaryKey(id);
	}
	
	public void save(User user) throws EntityOperateException, ValidatException, NoSuchAlgorithmException {
		
		user.setPassword(StringHelper.md5(user.getLoginName()+SysConfig.DEFAULT_PASSWORD));
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setId(UUID.randomUUID().toString());
		userMapper.insert(user);
		
	}
	
	public void updateEnable(String id) throws EntityOperateException {
		User dbUser = getById(id);
		dbUser.setEnable(true);
		userMapper.updateByPrimaryKey(dbUser);
	}
	
	public void updateDisable(String id) throws EntityOperateException {
		User dbUser = getById(id);
		dbUser.setEnable(false);
		userMapper.updateByPrimaryKey(dbUser);
	}
	
}
