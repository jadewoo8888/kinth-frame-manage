/**
 * 
 */
package com.kinth.frame.manage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Org;
import com.kinth.frame.manage.mapper.OrgMapper;


/**
 * @author topwoo
 *
 */
@Service("orgService")
public class OrgService {

	@Resource private OrgMapper orgMapper;
	
	public List<Org> getOrgRoots() {
		List<Org> list = orgMapper.selectRootOrgs();
		return list;
	}
	
	public List<Org> getChildren(String parentId) {
		List<Org> list = orgMapper.selectChildOrgs(parentId);
		return list;
	}
	
	public void save(Org org) {
		org.setId(UUID.randomUUID().toString());
		orgMapper.insert(org);
	}
	
	public Org getById(String id) throws EntityOperateException {
		return orgMapper.selectByPrimaryKey(id);
	}
	
	public void delete(String id) throws EntityOperateException {
		orgMapper.deleteByPrimaryKey(id);
	}
	
	public void update(Org org) throws EntityOperateException {
		orgMapper.updateByPrimaryKey(org);
	}
	
	public List<TreeModel> ToTreeModels(List<Org> parentList,String selectedId, List<String> checkedIdList,List<String> expandedIdList) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		
		for (Org org : parentList) {
			/*if (org.getId().equals("4028b20e50d576d50150d57921180001")) {
				System.out.println(org.getId());
			}*/
			boolean checked = false;
			boolean selected = false;
			boolean collpase = true;
			List<TreeModel> children = null;
			
			if (selectedId != null && selectedId.equals(org.getId())) {
				selected = true;
			}
			if (checkedIdList != null && !checkedIdList.isEmpty()) {
				checked = checkedIdList.contains(org.getId());
			}
			if (expandedIdList != null && !expandedIdList.isEmpty()) {
				collpase = !expandedIdList.contains(org.getId());
			}

			List<Org> orgChildren = getChildren(org.getId());
			if (orgChildren != null && orgChildren.size() > 0) {
				children = ToTreeModels(orgChildren, selectedId,checkedIdList, expandedIdList);
			}

			treeModels.add(new TreeModel(org.getId(), org.getId(), org.getName(), checked,
					selected, collpase, children));
		}
		return treeModels;
		
	}
}
