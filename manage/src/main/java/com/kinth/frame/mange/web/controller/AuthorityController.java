package com.kinth.frame.mange.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.common.helper.StringHelper;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Authority;
import com.kinth.frame.manage.service.AuthorityService;
import com.kinth.frame.mange.web.controller.auth.AuthPassport;
import com.kinth.frame.mange.web.model.AuthorityEditModel;
import com.kinth.frame.mange.web.model.extension.AuthorityModelExtension;

import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping(value = "/authority")
public class AuthorityController extends BaseController { 
	//@Resource private ChainEntityService chainEntityService;
	@Resource private AuthorityService authorityService;
	
	@AuthPassport
	@RequestMapping(value = "/chain", method = {RequestMethod.GET})
	public String chain(HttpServletRequest request, Model model){		
		if(!model.containsAttribute("contentModel")){		
			String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
			//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), null, null, StringHelper.toStringList( expanded, ","),Authority.class);		
			List<Authority> authorityRoots = authorityService.getAuthorityRoots();
			List<TreeModel> children = authorityService.ToTreeModels(authorityRoots, null, null, StringHelper.toStringList( expanded, ","));
			List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,false,false,children)));	
			
			//String jsonString  = JSONArray.fromObject(treeModels, new JsonConfig()).toString();
			String jsonString = JSON.toJSONString(treeModels);
			model.addAttribute("contentModel", jsonString);
		}	
		
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());
		
        return "authority/chain";		
	}
	
	@AuthPassport
	@RequestMapping(value = "/add/{id}", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model, @PathVariable(value="id") String id){	
		if(!model.containsAttribute("contentModel")){
			AuthorityEditModel authorityEditModel=new AuthorityEditModel();
			authorityEditModel.setParentId(id);
			model.addAttribute("contentModel", authorityEditModel);
		}
		
		List<TreeModel> treeModels;
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if(id!=null && !id.equals("")){
			//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), id, null, StringHelper.toStringList( expanded, ","),Authority.class);
			List<Authority> authorityRoots = authorityService.getAuthorityRoots();
			List<TreeModel> children = authorityService.ToTreeModels(authorityRoots, id, null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,false,false,children)));
		}
		else{
			//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), null, null, StringHelper.toStringList( expanded, ","),Authority.class);
			List<Authority> authorityRoots = authorityService.getAuthorityRoots();
			List<TreeModel> children = authorityService.ToTreeModels(authorityRoots, null, null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,true,false,children)));
		}
		//model.addAttribute(treeDataSourceName, JSONArray .fromObject(treeModels, new JsonConfig()).toString());
		model.addAttribute(treeDataSourceName, JSON.toJSONString(treeModels));
        return "authority/edit";	     
	}
	
	@AuthPassport
	@RequestMapping(value = "/add/{id}", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AuthorityEditModel editModel, @PathVariable(value="id") String id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return add(request, model, id);
		
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Authority authority=AuthorityModelExtension.toAuthority(editModel);
        authorityService.save(authority);
        if(returnUrl==null)
        	returnUrl="authority/chain";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException{	
		if(!model.containsAttribute("contentModel")){
			AuthorityEditModel authorityEditModel=AuthorityModelExtension.toAuthorityEditModel(authorityService.getById(id));
			model.addAttribute("contentModel", authorityEditModel);
		}

		List<TreeModel> treeModels;
		AuthorityEditModel editModel=(AuthorityEditModel)model.asMap().get("contentModel");
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if(editModel.getParentId()!=null && !editModel.getParentId().equals("")){
			//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), editModel.getParentId(), null, StringHelper.toStringList( expanded, ","),Authority.class);
			List<Authority> authorityRoots = authorityService.getAuthorityRoots();
			List<TreeModel> children = authorityService.ToTreeModels(authorityRoots, editModel.getParentId(), null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,false,false,children)));
		}
		else{
			//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), null, null, StringHelper.toStringList( expanded, ","),Authority.class);
			List<Authority> authorityRoots = authorityService.getAuthorityRoots();
			List<TreeModel> children = authorityService.ToTreeModels(authorityRoots, null, null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,true,false,children)));
		}
		//model.addAttribute(treeDataSourceName, JSONArray.fromObject(treeModels, new JsonConfig()).toString());
		model.addAttribute(treeDataSourceName, JSON.toJSONString(treeModels));
        return "authority/edit";	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AuthorityEditModel editModel, @PathVariable(value="id") String id, BindingResult result) throws EntityOperateException, ValidatException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        Authority authority=AuthorityModelExtension.toAuthority(editModel);
        authority.setId(id);
        authorityService.update(authority);
        if(returnUrl==null)
        	returnUrl="authority/chain";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		authorityService.delete(id);
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if(returnUrl==null)
        	returnUrl="authority/chain";
        return "redirect:"+returnUrl;	
	}
	
}  
