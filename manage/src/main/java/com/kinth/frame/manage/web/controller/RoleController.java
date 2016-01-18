package com.kinth.frame.manage.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.common.helper.StringHelper;
import com.kinth.frame.common.web.helper.PageListUtil;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Authority;
import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.service.AuthorityService;
import com.kinth.frame.manage.service.RoleAuthorityService;
import com.kinth.frame.manage.service.RoleService;
import com.kinth.frame.manage.web.controller.auth.AuthPassport;
import com.kinth.frame.manage.web.model.RoleBindModel;
import com.kinth.frame.manage.web.model.RoleEditModel;
import com.kinth.frame.manage.web.model.RoleSearchModel;
import com.kinth.frame.manage.web.model.extension.RoleBindModelExtension;
import com.kinth.frame.manage.web.model.extension.RoleModelExtension;


@Controller
//@Scope("prototype")// 设置返回实例的方式 prototype or singleton
@RequestMapping(value = "/role")
public class RoleController extends BaseController {  
	@Resource private RoleService roleService;
	@Resource private RoleAuthorityService roleAuthorityService;
	//@Resource private ChainEntityService chainEntityService;
	@Resource private AuthorityService authorityService;
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, @ModelAttribute RoleSearchModel searchModel){
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

        model.addAttribute(searchModelName, searchModel);
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);      
        model.addAttribute("contentModel", roleService.listPage(RoleModelExtension.toRoleSearch(searchModel), pageNo, pageSize));
        return "role/list";
    }
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			RoleEditModel roleEditModel=new RoleEditModel();
			model.addAttribute("contentModel", roleEditModel);
		}
        return "role/edit";	  
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return add(request, model);
		
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Role role=RoleModelExtension.toRole(editModel);
        roleService.save(role);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;     	
    }
	
	@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.GET})
	public String bind(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException{
		Role role=roleService.getById(id);
		List<String> checkedAuthorityIds = new ArrayList<String>();
		
		List<Authority> roleAuthorities = authorityService.getAuthoritys(id);
		//List<Authority> roleAuthorities=role.getAuthorities();
		for(Authority item : roleAuthorities){
			checkedAuthorityIds.add(item.getId());
		}	
		
		if(!model.containsAttribute("contentModel")){
			RoleBindModel roleBindModel=RoleBindModelExtension.toRoleBindModel(role);
			String[] checkedAuthorityIdsArray=new String[checkedAuthorityIds.size()];
			checkedAuthorityIds.toArray(checkedAuthorityIdsArray);
			roleBindModel.setAuthorityIds(checkedAuthorityIdsArray);
			model.addAttribute("contentModel", roleBindModel);
		}
		
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), null, checkedAuthorityIds, StringHelper.toStringList( expanded, ","),Authority.class);		
		List<TreeModel> children = authorityService.ToTreeModels(authorityService.getAuthorityRoots(), null, checkedAuthorityIds, StringHelper.toStringList( expanded, ","));
		List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null,null,"根节点",false,false,false,children)));
		model.addAttribute(treeDataSourceName, JSON.toJSONString(treeModels));

		return "role/bind";
	}
	
	@AuthPassport
	@RequestMapping(value="/bind/{id}", method = {RequestMethod.POST})
	public String bind(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleBindModel roleBindModel, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{
        if(result.hasErrors())
            return bind(request, model, id);
        
        roleAuthorityService.saveAuthorize(id, roleBindModel.getAuthorityIds());      
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl; 
	}  
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		if(!model.containsAttribute("contentModel")){
			RoleEditModel roleEditModel = RoleModelExtension.toRoleEditModel(roleService.getById(id));
			model.addAttribute("contentModel", roleEditModel);
			//RoleEditModel editModel=(RoleEditModel)model.asMap().get("contentModel");
		}
		
        return "role/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") RoleEditModel editModel, @PathVariable(value="id") String id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        
        Role role =RoleModelExtension.toRole(editModel);
        role.setId(id);
        roleService.update(role);
        if(returnUrl==null)
        	returnUrl="role/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value="/delete", method = {RequestMethod.POST})
	@ResponseBody
	public String delete(String ids) throws ValidatException, EntityOperateException {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			int count = roleService.selRoleUserCount(id.trim());
			if (count > 0) {
				return id.trim();
			} 
		}
		
		for (String id : idArr) {
			roleService.deleteById(id.trim());
		}
		return "";
	}
	
	
}  
