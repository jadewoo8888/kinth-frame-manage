package com.kinth.frame.manage.web.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.common.helper.StringHelper;
import com.kinth.frame.common.web.helper.PageListUtil;
import com.kinth.frame.common.web.helper.TreeModel;
import com.kinth.frame.manage.domain.Authority;
import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.domain.User;
import com.kinth.frame.manage.service.AuthorityService;
import com.kinth.frame.manage.service.OrgService;
import com.kinth.frame.manage.service.RoleAuthorityService;
import com.kinth.frame.manage.service.RoleService;
import com.kinth.frame.manage.service.UserService;
import com.kinth.frame.manage.web.controller.auth.AuthHelper;
import com.kinth.frame.manage.web.controller.auth.AuthPassport;
import com.kinth.frame.manage.web.controller.auth.AuthorityMenu;
import com.kinth.frame.manage.web.controller.auth.PermissionMenu;
import com.kinth.frame.manage.web.controller.auth.UserAuth;
import com.kinth.frame.manage.web.controller.auth.UserRole;
import com.kinth.frame.manage.web.model.RoleBindModel;
import com.kinth.frame.manage.web.model.UserAuthorizeModel;
import com.kinth.frame.manage.web.model.UserEditModel;
import com.kinth.frame.manage.web.model.UserLoginModel;
import com.kinth.frame.manage.web.model.UserPasswordEditModel;
import com.kinth.frame.manage.web.model.UserSearchModel;
import com.kinth.frame.manage.web.model.extension.UserAuthorizeModelExtension;
import com.kinth.frame.manage.web.model.extension.UserModelExtension;








@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {  
	
    @Resource private UserService userService;
	@Resource private RoleAuthorityService roleAuthorityService;
	@Resource private AuthorityService authorityService;
	@Resource private OrgService orgService;
	@Resource private RoleService roleService;
	//@Resource private ChainEntityService chainEntityService;
	
	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public String login(Model model){
		if(!model.containsAttribute("contentModel"))
            model.addAttribute("contentModel", new UserLoginModel());
        return "user/login";
    }
	
	
	@RequestMapping(value="/login", method = {RequestMethod.POST})
	public String login(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserLoginModel userLoginModel, BindingResult result) throws ValidatException, EntityOperateException, NoSuchAlgorithmException{
		//如果有验证错误 返回到form页面
        if(result.hasErrors()) {
        	return login(model);
        }
            
        List<Role> roleList = null;
        User user = userService.login(userLoginModel.getLoginName().trim(), userLoginModel.getPassword().trim());
        if(user == null || user.getEnable() == false){
        	if(user==null){
	        	result.addError(new FieldError("contentModel","username","用户名或密码错误。"));
	        	result.addError(new FieldError("contentModel","password","用户名或密码错误。"));
        	}
        	else if(user.getEnable()==false) {
        		result.addError(new FieldError("contentModel","username","此用户被禁用，不能登录。"));
        	}
            return login(model);
        } else {
        	roleList = roleService.selectUserRole(user.getId());
        	if (roleList == null || roleList.size() == 0) {
        		result.addError(new FieldError("contentModel","username","此用户当前未被授权，不能登录。"));
        		 return login(model);
        	}
        	if (roleList.size() == 1) {//如果是单角色
        		return selRoleLogin(request, model, roleList.get(0).getId(), user.getId(), user.getLoginName(), user.getRealName());
        	}
        }
        
        UserAuth userAuth = new UserAuth(user.getId(), user.getLoginName(), user.getRealName());
        userAuth.setRoles(roleList);
        model.addAttribute("userAuth", userAuth);
        
        return "user/roleLogin";
	}
	
	@RequestMapping(value="/role/login/{roleId}/{userId}/{loginName}/{realName}/", method = {RequestMethod.GET})
	public String selRoleLogin(HttpServletRequest request, Model model, @PathVariable String roleId,@PathVariable String userId,@PathVariable String loginName,@PathVariable String realName) throws ValidatException, EntityOperateException, NoSuchAlgorithmException{
			UserAuth userAuth=new UserAuth(userId, loginName, realName);
	    	Role role = roleService.getById(roleId);
	    	UserRole userRole=new UserRole(role.getId(), role.getName());
	    	List<AuthorityMenu> authorityMenus=new ArrayList<AuthorityMenu>();
	    	List<Authority> roleAuthorities = authorityService.getAuthoritys(roleId);
	    	
	    	for(Authority authority :roleAuthorities){
	    		if(authority.getParentId()==null){
	    			AuthorityMenu authorityMenu=new AuthorityMenu(authority.getId(), authority.getName(), authority.getItemIcon(), authority.getUrl());
	    			
	    			List<AuthorityMenu> childrenAuthorityMenus=new ArrayList<AuthorityMenu>();
	    			for(Authority subAuthority :roleAuthorities){   				
	    				if(subAuthority.getParentId()!=null && subAuthority.getParentId().equals(authority.getId()))
	    					childrenAuthorityMenus.add(new AuthorityMenu(subAuthority.getId(), subAuthority.getName(), subAuthority.getItemIcon(), subAuthority.getUrl()));
	    			}
	    			authorityMenu.setChildrens(childrenAuthorityMenus);
	    			authorityMenus.add(authorityMenu);
	    		}
	    	}
	    	
			List<PermissionMenu> permissionMenus=new ArrayList<PermissionMenu>(); 	
	    	for(Authority authority : roleAuthorities){ 	  		
	    		List<Authority> parentAuthorities=new ArrayList<Authority>();
	    		Authority tempAuthority=authority;
	    		Authority parentAuthority = null;
	    		while(tempAuthority.getParentId()!=null && !tempAuthority.getParentId().equals("")){
	    			parentAuthority = authorityService.getById(tempAuthority.getParentId());
	    			parentAuthorities.add(parentAuthority);
	    			tempAuthority=parentAuthority;
	    		}
	    		if(parentAuthorities.size()>=2)
	    			permissionMenus.add(new PermissionMenu(parentAuthorities.get(parentAuthorities.size()-1).getId(),parentAuthorities.get(parentAuthorities.size()-1).getName(),parentAuthorities.get(parentAuthorities.size()-2).getId(),parentAuthorities.get(parentAuthorities.size()-2).getName(),authority.getName(),authority.getMatchUrl()));
	    		else if(parentAuthorities.size()==1)
	    			permissionMenus.add(new PermissionMenu(parentAuthorities.get(0).getId(),parentAuthorities.get(0).getName(),authority.getId(),authority.getName(),authority.getName(),authority.getMatchUrl()));
	    		else
	    			permissionMenus.add(new PermissionMenu(authority.getId(),authority.getName(),null,null,authority.getName(),authority.getMatchUrl()));
	    	}
	    	userRole.setAuthorityMenus(authorityMenus);
	    	userRole.setPermissionMenus(permissionMenus);
	    	userAuth.setCurrRole(userRole);
	    	AuthHelper.setSessionUserAuth(request, userAuth);
	    
		    String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		    if(returnUrl==null)
		    	returnUrl="/home/index";
			return "redirect:"+returnUrl; 	
	}
	
	@RequestMapping(value="/logOut", method = {RequestMethod.GET})
    public String logOut(HttpServletRequest request){
		AuthHelper.removeSessionUserAuth(request);
		return "redirect:/home/index";
    }
	
	@AuthPassport
	@RequestMapping(value="/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model, UserSearchModel searchModel){ 			
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

        model.addAttribute("searchModel", searchModel);
        int pageNo = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
        int pageSize = ServletRequestUtils.getIntParameter(request, PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);    
        model.addAttribute("contentModel", userService.listPage(searchModel.getLoginName(), searchModel.getRealName(), pageNo, pageSize));

        return "user/list";
    }
	
	@AuthPassport
	@RequestMapping(value="/role/{id}", method = {RequestMethod.GET})
    public String role(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		
		
		User user = userService.getById(id);
		List<String> checkedRoleIds = new ArrayList<String>();
		
		List<Role> roles = roleService.selectUserRole(id);
		for(Role item : roles){
			checkedRoleIds.add(item.getId());
		}	
		
		if(!model.containsAttribute("contentModel")){
			UserAuthorizeModel userBindModel = UserAuthorizeModelExtension.toUserBindModel(user);
			String[] checkedRoleIdsArray = new String[checkedRoleIds.size()];
			checkedRoleIds.toArray(checkedRoleIdsArray);
			userBindModel.setRoleIds(checkedRoleIdsArray);
			model.addAttribute("contentModel", userBindModel);
		}
		
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		//List<TreeModel> children=TreeModelExtension.ToTreeModels(chainEntityService,chainEntityService.listChain(Authority.class), null, checkedAuthorityIds, StringHelper.toStringList( expanded, ","),Authority.class);		
		List<TreeModel> treeModels = roleService.ToTreeModels(roleService.getAllRole(), null, checkedRoleIds, StringHelper.toStringList( expanded, ","));
		//List<TreeModel> treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel(null,null,"根节点",false,false,false,children)));
		model.addAttribute(treeDataSourceName, JSON.toJSONString(treeModels));
		
        return "user/role";
    }
	
	@AuthPassport
	@RequestMapping(value="/role/{id}", method = {RequestMethod.POST})
	public String role(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserAuthorizeModel userAuthorizeModel, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{
		if(result.hasErrors())
            return role(request, model, id);

		userService.updateBind(id, userAuthorizeModel.getRoleIds(), null);       
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/org/{id}", method = {RequestMethod.GET})
    public String org(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		if(!model.containsAttribute("contentModel")){
			
			//UserAuthorizeModel UserBindModel = UserAuthorizeModelExtension.toUserBindModel(UserService.getById(id));
			User user = userService.getById(id);
			UserAuthorizeModel UserBindModel = UserAuthorizeModelExtension.toUserBindModel(user);
			/*List<Role> roleList = roleService.selectUserRole(id);
			UserBindModel.setRoleList(roleList);*/
            model.addAttribute("contentModel", UserBindModel);
        }	

		List<TreeModel> treeModels;
		UserAuthorizeModel authorizeModel=(UserAuthorizeModel)model.asMap().get("contentModel");
		String expanded = ServletRequestUtils.getStringParameter(request, "expanded", null);
		if(authorizeModel.getOrgId()!=null && !authorizeModel.getOrgId().equals("")){
			List<TreeModel> children = orgService.ToTreeModels(orgService.getOrgRoots(), authorizeModel.getOrgId(), null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,false,false,children)));
		}
		else{
			List<TreeModel> children = orgService.ToTreeModels(orgService.getOrgRoots(), null, null, StringHelper.toStringList( expanded, ","));
			treeModels=new ArrayList<TreeModel>(Arrays.asList(new TreeModel("0","0","根节点",false,true,false,children)));
		}
		model.addAttribute(treeDataSourceName, JSON.toJSONString(treeModels));
		model.addAttribute(selectDataSourceName, roleService.getAllRoleMap());
		
        return "user/org";
    }
	
	@AuthPassport
	@RequestMapping(value="/org/{id}", method = {RequestMethod.POST})
	public String org(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserAuthorizeModel userAuthorizeModel, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{
		if(result.hasErrors())
            return org(request, model, id);

		userService.updateBind(id, null, userAuthorizeModel.getOrgId());       
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/enable/{id}", method = {RequestMethod.GET})
	public String enable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserAuthorizeModel userAuthorizeModel, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{

		userService.updateEnable(id);
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/disable/{id}", method = {RequestMethod.GET})
	public String disable(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserAuthorizeModel userAuthorizeModel, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{

		userService.updateDisable(id);
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value="/delete/{id}", method = {RequestMethod.GET})
	public String delete(HttpServletRequest request, Model model, @PathVariable(value="id") String id, BindingResult result) throws ValidatException, EntityOperateException{

		userService.deleteById(id);
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl; 	
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String add(HttpServletRequest request, Model model){	
		if(!model.containsAttribute("contentModel"))
		{
			UserEditModel userEditModel=new UserEditModel();
			model.addAttribute("contentModel", userEditModel);
		}
        return "user/edit";	  
	}
	
	@AuthPassport
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String add(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserEditModel editModel, BindingResult result) throws EntityOperateException, ValidatException, NoSuchAlgorithmException {
        if(result.hasErrors())
            return add(request, model);
		
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        User user = UserModelExtension.toUser(editModel);
        userService.save(user);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl;     	
    }
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.GET})
	public String edit(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		if(!model.containsAttribute("contentModel")){
			UserEditModel userEditModel = UserModelExtension.toUserEditModel(userService.getById(id));
			model.addAttribute("contentModel", userEditModel);
			//UserEditModel editModel=(UserEditModel)model.asMap().get("contentModel");
		}
		
		return "user/edit";	 	
	}
	
	@AuthPassport
	@RequestMapping(value = "/edit/{id}", method = {RequestMethod.POST})
    public String edit(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserEditModel editModel, @PathVariable(value="id") String id, BindingResult result) throws EntityOperateException, ValidatException, NoSuchAlgorithmException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        
        User user = UserModelExtension.toUser(editModel);
        user.setId(id);
        userService.update(user);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl;      	
    }
	
	@AuthPassport
	@RequestMapping(value = "/updatePassword/{id}", method = {RequestMethod.GET})
	public String updatePassword(HttpServletRequest request, Model model, @PathVariable(value="id") String id) throws ValidatException, EntityOperateException{	
		if(!model.containsAttribute("contentModel")){
			UserPasswordEditModel editModel = new UserPasswordEditModel();
			editModel.setId(id);
			model.addAttribute("contentModel", editModel);
			//UserEditModel editModel=(UserEditModel)model.asMap().get("contentModel");
		}
		
		return "user/updatePassword";	 	
	}
	
	@AuthPassport
	@RequestMapping(value = "/updatePassword/{id}", method = {RequestMethod.POST})
    public String updatePassword(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") UserPasswordEditModel editModel, @PathVariable(value="id") String id, BindingResult result) throws EntityOperateException, ValidatException, NoSuchAlgorithmException {
        if(result.hasErrors())
            return edit(request, model, id);
        
        String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
        
        User user = new User();
        user.setId(id);
        user.setPassword(editModel.getPassword());
        userService.update(user);
        if(returnUrl==null)
        	returnUrl="user/list";
    	return "redirect:"+returnUrl;      	
    }
}  

