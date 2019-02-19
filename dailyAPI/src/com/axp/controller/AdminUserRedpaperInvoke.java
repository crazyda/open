package com.axp.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.dao.AdminUserRedpaperDao;

@Controller
@RequestMapping("invoke/sellers")
public class AdminUserRedpaperInvoke extends BaseController{

	@Autowired
	private AdminUserRedpaperDao adminuserRedpaperDao;
	
	@RequestMapping("/sendRedpacket")
	@ResponseBody
	public Map<String, Object> sendRedpaper(HttpServletRequest request,HttpServletResponse response){
		return adminUserRedpaperService.sendRedpaper(request, response);
		
		
	}
	
}
