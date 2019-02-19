package com.axp.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IRedpaperService;

@Controller
@RequestMapping("invoke/users")
public class RedpaperInvoke extends BaseController {
	
	@Autowired
	IRedpaperService redpaperService;

	
	/**
	 * 获得红包配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("postSendRedPaper")
	public Map<String, Object> postSendRedPaper(HttpServletRequest request){
		return redpaperService.getConfig(request);
	}
	
	

	
	
	//打开红包
	@RequestMapping("openRedPaper")
	public String openNewRedPaper(HttpServletRequest request){
		return redpaperService.openNewRedPaper(request);
	}
	
	
	//打开红包
		@ResponseBody
		@RequestMapping("openAdminRedPaper")
		public Map<String, Object> openAdminRedPaper(HttpServletRequest request){
			return redpaperService.openAdminNewRedPaper(request);
		}
	
	
	@RequestMapping("redPaperLink")
	public String redPaperLink(HttpServletRequest request){
		String link = request.getParameter("link");
		if(link==null||StringUtils.isBlank(link)){
			link = "";
			return "newRedPaper/redpaper_link";
		}
  		if(!link.startsWith("http://")){
  			link = "http://"+link;
  		}
  		request.setAttribute("link", link);
  		return "newRedPaper/redpaper_link";
  	}
	
	@RequestMapping("moneyLink")
	public String moneyLink(HttpServletRequest request,HttpServletResponse response){
		redpaperService.moneyList(request,response);
		return "newRedPaper/mymoney";
  	}
	
	@RequestMapping("checkRedPaper")
	@ResponseBody
	public Map<String,Object> getNewRedpaperId(HttpServletRequest request){
		return redpaperService.verifyNewRedpaper(request);
	}
	
	@RequestMapping("checkAdminRedPaper")
	@ResponseBody
	public Map<String,Object> getNewRedpaperId2(HttpServletRequest request){
		return redpaperService.verifyAdminNewRedpaper(request);
	}
	
	
	//下单后获取红包
	@RequestMapping("checkOrderRedPaper")
	@ResponseBody
	public Map<String,Object> checkOrderRedPaper(HttpServletRequest request){
		return redpaperService.checkOrderRedPaper(request);
	}
	
	
	
	@RequestMapping("redPaperList")
	public String redPaperList(HttpServletRequest request,HttpServletResponse response) {
		redpaperService.redPaperList(request,response);
		
		return "newRedPaper/myred02";
	}
	
	@RequestMapping("rule")
	public String rule(HttpServletRequest request,HttpServletResponse response) {
		
		return "newRedPaper/rule";
	}
}
