package com.kinth.frame.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.web.helper.PageList;
import com.kinth.frame.common.web.helper.PageListUtil;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Org;
import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.mapper.RoleAuthorityMapper;
import com.kinth.frame.manage.mapper.RoleMapper;
import com.kinth.frame.manage.mapper.UserRoleMapper;
import com.kinth.frame.manage.service.model.RoleSearch;


@Service("roleService")
public class RoleService {

	@Resource private RoleMapper roleMapper;
	@Resource private RoleAuthorityMapper roleAuthorityMapper;
	@Resource private UserRoleMapper userRoleMapper;
	
	public Map<String, String> getAllRoleMap() {
		Map<String, String> ret = new HashMap<String, String>();
		List<Role> roleList = roleMapper.selectAll();
		for(Role entity : roleList){
			ret.put(entity.getId(), entity.getName());
		}
		return ret;
	}
	
	public Role getById(String id) {
		Role role = roleMapper.selectByPrimaryKey(id);
		return role;
	}
	
	public PageList<Role> listPage(RoleSearch search, int pageNo, int pageSize) {		
		Map<String,Object> paramMap = new HashMap<>();
        if(search!=null){
        	if(search.getName()!=null && !search.getName().isEmpty()){
        		paramMap.put("name", search.getName());
        	}
        }
       
        paramMap.put("startIndex", (pageNo-1)*pageSize); 
        paramMap.put("pageSize", pageSize);
        
        List<Role> items = roleMapper.selectByPage(paramMap);
        Integer count=roleMapper.selcount(paramMap);
        return PageListUtil.getPageList(count, pageNo, items, pageSize);
    }
	
	public void save(Role role) throws EntityOperateException {
		roleMapper.insert(role);
		
	}
	
	public void update(Role role) throws EntityOperateException {
		roleMapper.updateByPrimaryKey(role);
	}
	
	public void deleteById(String id) throws EntityOperateException {
		roleMapper.deleteByPrimaryKey(id);
		roleAuthorityMapper.deleteRoleAuthorities(id);
	}
	
	public List<Role> selectUserRole(String userId) {
		return roleMapper.selectUserRole(userId);
	}
	
	public List<Role> getAllRole() {
		return roleMapper.selectAll();
	}
	
	public int selRoleUserCount(String roleId) {
		return  userRoleMapper.selRoleUserCount(roleId);
	}
	
	public List<TreeModel> ToTreeModels(List<Role> allRole,String selectedId, List<String> checkedIdList,List<String> expandedIdList) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		
		for (Role role : allRole) {
			boolean checked = false;
			boolean selected = false;
			boolean collpase = true;
			List<TreeModel> children = null;
			
			if (selectedId != null && selectedId.equals(role.getId())) {
				selected = true;
			}
			if (checkedIdList != null && !checkedIdList.isEmpty()) {
				checked = checkedIdList.contains(role.getId());
			}
			if (expandedIdList != null && !expandedIdList.isEmpty()) {
				collpase = !expandedIdList.contains(role.getId());
			}

			treeModels.add(new TreeModel(role.getId(), role.getId(), role.getName(), checked,
					selected, collpase, children));
		}
		return treeModels;
		
	}
}
