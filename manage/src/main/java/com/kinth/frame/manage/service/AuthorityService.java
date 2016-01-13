package com.kinth.frame.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.common.web.helper.PageList;
import com.kinth.frame.common.web.helper.PageListUtil;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Authority;
import com.kinth.frame.manage.mapper.AuthorityMapper;
import com.kinth.frame.manage.service.model.AuthoritySearch;


@Service("authorityService")
public class AuthorityService {

	@Resource private AuthorityMapper authorityMapper;
	
	public Authority getById(String id) {
		Authority authority = authorityMapper.selectByPrimaryKey(id);
		return authority;
	}
	
	public List<Authority> getAuthorityRoots() {
		List<Authority> list = authorityMapper.selectRootAuthoritys();
		return list;
	}
	
	public List<Authority> getChildren(String parentId) {
		List<Authority> list = authorityMapper.selectChildAuthoritys(parentId);
		return list;
	}
	
	public void update(Authority model) throws ValidatException, EntityOperateException {
		Authority dbModel=getById(model.getId());
		dbModel.setName(model.getName());
		dbModel.setUrl(model.getUrl());
		dbModel.setMatchUrl(model.getMatchUrl());
		dbModel.setItemIcon(model.getItemIcon());
		dbModel.setParentId(model.getParentId());
		dbModel.setAuthObjectType(model.getAuthObjectType());
		authorityMapper.updateByPrimaryKey(dbModel);
	}
	
	public PageList<Authority> listPage(AuthoritySearch search, int pageNo, int pageSize) {		
		Map<String,Object> paramMap = new HashMap<>();
		
        if(search!=null){
        	if(search.getName()!=null && !search.getName().isEmpty()){
        		paramMap.put("name", search.getName());
        	}
        }
        paramMap.put("startIndex", (pageNo-1)*pageSize); 
        paramMap.put("pageSize", pageSize);
        List<Authority> items = authorityMapper.selectByPage(paramMap);
        Integer count=authorityMapper.selcount(paramMap);
        return PageListUtil.getPageList(count, pageNo, items, pageSize);
    }
	
	public void save(Authority entity) throws EntityOperateException, ValidatException {
	    authorityMapper.insert(entity);
	}
	public void delete(String id) throws EntityOperateException, ValidatException {
	    authorityMapper.deleteByPrimaryKey(id);
	}
	
	public List<Authority> getAuthorityTree(Authority parent) {
		
		List<Authority> allAuthority = new ArrayList<Authority>();
		List<Authority> children = this.getChildren(parent.getId());
		for (Authority child : children) {
			this.getAuthorityTree(child);
		}
		
		allAuthority.add(parent);
		
		return allAuthority;
	}
	
	public List<Authority> getAuthoritys(String roleId) {
		List<Authority> authList = authorityMapper.selRoleAuthorities(roleId);
		return authList;
	}
	
	public List<TreeModel> ToTreeModels(List<Authority> parentList,String selectedId, List<String> checkedIdList,List<String> expandedIdList) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		
		for (Authority authority : parentList) {
			boolean checked = false;
			boolean selected = false;
			boolean collpase = true;
			List<TreeModel> children = null;
			
			if (selectedId != null && selectedId.equals(authority.getId())) {
				selected = true;
			}
			if (checkedIdList != null && !checkedIdList.isEmpty()) {
				checked = checkedIdList.contains(authority.getId());
			}
			if (expandedIdList != null && !expandedIdList.isEmpty()) {
				collpase = !expandedIdList.contains(authority.getId());
			}

			List<Authority> authorityChildren = getChildren(authority.getId());
			if (authorityChildren != null && authorityChildren.size() > 0) {
				children = ToTreeModels(authorityChildren, selectedId,checkedIdList, expandedIdList);
			}

			treeModels.add(new TreeModel(authority.getId(), authority.getId(), authority.getName(), checked,
					selected, collpase, children));
		}
		return treeModels;
		
	}
}
