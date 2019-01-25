package com.axp.interceptor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.util.ConstantUtil;
import com.axp.util.StringUtil;

/**
 * 用于检测用户是否登陆的过滤器，如果未登录或超时，则重定向到指的登录页面
 * <p>
 */

public class SystemContextFilter extends HttpServlet implements Filter {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 String encoding=null;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html;charset="+encoding);

		
		if(encoding!=null){
	        //设置request字符编码
	            request.setCharacterEncoding(encoding);
	         //设置response字符编码
	            response.setContentType("text/html;charset="+encoding);
	     }
		
		
		
		if(request.getServletContext().getAttribute("BASEPATH_IN_SESSION")==null){
			String path = req.getContextPath();
			String basePath = req.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + path + "/";
			
			req.getSession().getServletContext()
					.setAttribute("BASEPATH_IN_SESSION", basePath);
			ConstantUtil.BASE = basePath;// 给base赋初始值；
			ConstantUtil.BASE_PATH = ConstantUtil.BASE + "uploadPic"
					+ File.separatorChar;// 给basePath赋值；

			// 装载用户等级
			Map<String, Integer> levelMap = new HashMap<>();
			levelMap.put("SUPERADMIN", StringUtil.SUPERADMIN);// 总部
			levelMap.put("ADMIN", StringUtil.ADMIN);// 总部
			levelMap.put("CENTER", StringUtil.ADVERCENTER);// 运营中心
			levelMap.put("PROXY", StringUtil.ADVERONE);// 城市代理
			levelMap.put("ALLIANCE", StringUtil.ADVERTWO);// 联盟商圈
			req.getSession().getServletContext()
					.setAttribute("USER_LEVEL_MAP", levelMap);
		}
		
		//装载资源服务器url
		if(request.getServletContext().getAttribute("RESOURCE_LOCAL_URL") == null){
			//String path="/aixiaopingAPI";
			String path = "/aixiaopingRes/";
			//String basePath = req.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
			//String basePath = "http://192.168.200.201:8080"+path+"/";
			String basePath=req.getScheme()+"://www.aixiaoping.com:8080"+path; 
			
			request.getServletContext().setAttribute("RESOURCE_LOCAL_URL", basePath);
		}
		
		if(req.getCookies()==null){
			Cookie cookie = new Cookie("JSESSIONID", req.getSession().getId());
			cookie.setMaxAge(2592000);//30天
			res.addCookie(cookie);
			
			req.getSession().setAttribute("PAY_STATUS", false);//支付状态
			req.getSession().setAttribute("RE_ORDERS", null);//临时订单列表
			
		}
		
		
		
		
		filterChain.doFilter(request, response);
	}

	public void destroy() {

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		  encoding=filterConfig.getInitParameter("encoding");
	}
}