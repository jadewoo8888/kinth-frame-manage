package com.kinth.frame.mange.web.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kinth.frame.common.exception.EntityOperateException;
import com.kinth.frame.common.exception.ValidatException;
import com.kinth.frame.manage.domain.Role;
import com.kinth.frame.manage.service.RoleService;
import com.kinth.frame.mange.web.controller.auth.AuthPassport;


@Controller
@RequestMapping(value = "/home")
public class HomeController extends BaseController {  
	@Resource private RoleService roleService;
	//@AuthPassport
    @RequestMapping(value = "/hello")
    public ModelAndView hello() throws EntityOperateException, ValidatException, SQLException { 

    	//UserService userService=new UserService();
    	
    	//userService.delete(2);
    	throw new SQLException("数据库链接错误。");
    	
        //1、收集参数  
        //2、绑定参数到命令对象  
        //3、调用业务对象  
        //4、选择下一个页面  
        /*ModelAndView mv = new ModelAndView();  
        //添加模型数据 可以是任意的POJO对象  
        mv.addObject("message", "Hello World!");  
        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面,如果不指定具体得视图，则会根据URL寻找对应的视图
        mv.setViewName("home/hello");  
        return mv;*/
    }  
    
    @AuthPassport
    @RequestMapping(value = "/index")
    public String index() { 	

        return "home/index";
    }  
    
    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() { 
    	
    	ModelAndView mv = new ModelAndView();  
    	mv.setViewName("404");  
    	
    	return mv;  
    }
    @RequestMapping(value="/test", method = {RequestMethod.POST})
    @ResponseBody
    public Map test(@RequestBody Map<String,String> map){
    	Map<String, String> res = new HashMap<String, String>();
    	res.put("a", map.get("a"));
    	res.put("b", map.get("b"));
    	return res;
    }
    
    @RequestMapping(value="/test2", method = {RequestMethod.POST})
    @ResponseBody
    public String test2(@RequestBody Map<String,String> map){
    	
    	return map.toString();
    }
    @RequestMapping(value="/{id}")
    @ResponseBody
    public Role test2(@PathVariable String id){
    	
    	return roleService.getById(id);
    }
}  
