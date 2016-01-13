package com.kinth.frame.mange.web.controller.interceptor;

import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kinth.frame.common.exception.PermissionException;
import com.kinth.frame.mange.web.controller.auth.AuthHelper;
import com.kinth.frame.mange.web.controller.auth.AuthPassport;
import com.kinth.frame.mange.web.controller.auth.PermissionMenu;
import com.kinth.frame.mange.web.controller.auth.UserAuth;
import com.kinth.frame.mange.web.controller.auth.UserRole;


public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
        	AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
       	 
        	//没有声明需要权限,或者声明不验证权限
       	 	if(authPassport == null || authPassport.validate() == false)
                return true;
            else{
            	
            	UserAuth userAuth=AuthHelper.getSessionUserAuth(request);
            	if(userAuth!=null)
            	{
            		boolean hasPermission=false;
            		String requestServletPath=request.getServletPath();
            		
            		for(PermissionMenu permissionMenu : userAuth.getPermissionMenus()){
            			
            			Pattern pattern = Pattern.compile(permissionMenu.getPermission(),Pattern.CASE_INSENSITIVE);
            			Matcher matcher = pattern.matcher(requestServletPath);
            			if(matcher.find()){
            				hasPermission=true;
            				AuthHelper.setRequestPermissionMenu(request, permissionMenu);
            			}
            		}
            		if(hasPermission)
            			return true;
            		else
            			throw new PermissionException("没有权限！");
            	}
            	else
            	{
            		StringBuilder urlBuilder=new StringBuilder(request.getContextPath());
            		urlBuilder.append("/account/login");
            		if(request.getServletPath()!=null && !request.getServletPath().isEmpty()){
            			urlBuilder.append("?returnUrl=");
            			
            			StringBuilder pathAndQuery=new StringBuilder(request.getServletPath());
            			if(request.getQueryString()!=null && !request.getQueryString().isEmpty()){
                			pathAndQuery.append("?");
                			pathAndQuery.append(request.getQueryString());
                		}
            			
            			urlBuilder.append(URLEncoder.encode(pathAndQuery.toString(), "UTF-8"));
            		}
            		
            		response.sendRedirect(urlBuilder.toString());
            		return false;
            	}
            	
            }
        }
        else
       	 return true;   
	 }
}