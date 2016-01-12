package com.kinth.frame.mange.web.controller.auth;

import javax.servlet.http.HttpServletRequest;

public class AuthHelper {
	
	public static void setSessionUserAuth(HttpServletRequest request, UserAuth userAuth){
		request.getSession().setAttribute("userAuth", userAuth);
	}
	
	public static UserAuth getSessionUserAuth(HttpServletRequest request){
		return (UserAuth)request.getSession().getAttribute("userAuth");
	}
	
	public static void setRequestPermissionMenu(HttpServletRequest request, PermissionMenu permissionMenu){
		request.setAttribute("permissionMenu", permissionMenu);
	}
	
	public static PermissionMenu getRequestPermissionMenu(HttpServletRequest request){
		return (PermissionMenu)request.getAttribute("permissionMenu");
	}
	
	public static void removeSessionUserAuth(HttpServletRequest request){
		request.getSession().removeAttribute("userAuth");
	}
	
}
