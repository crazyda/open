package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;

@Controller
@RequestMapping("invoke/sellers")
public class SellerLoginInvoke extends BaseController {
	

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
		 try {
			 	response.setContentType("text/json"); 
	            /*设置字符集为'UTF-8'*/
	            response.setCharacterEncoding("UTF-8"); 
	            Parameter parameter = ParameterUtil.getParameter(request);      
	            if (parameter == null) {//错误的参数；
	                return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
	            }
	            String loginname = null;
	            String password = null; 
	            try {
	            	loginname = parameter.getData().getString("loginname");
	            	password = parameter.getData().getString("password");
	            } catch (Exception e) {
	                e.printStackTrace();
	                return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
	            }


	            //检查loginname,password；
	            Boolean emptyCheck = ParameterUtil.EmptyCheck(loginname, password);
	            if (!emptyCheck) {
	                return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
	            }
	            
	            //处理登录逻辑，返回json；
	            try {
	            	System.out.println("商家版登录");
	                return adminUserService.getAdminUser(loginname, password);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            //未知错误；
	            return JsonResponseUtil.getJson(-1, "未知错误");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	
	@RequestMapping("/getSellerMall")
	@ResponseBody
	public Map<String, Object> getSellerInfoById(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter=ParameterUtil.getParameter(request);
		if (parameter==null) {
			return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
		}
//		int adminuserId= parameter.getAdminuserId();
//		int sellerId= parameter.getSellerId();
		String sellerId=parameter.getSellerId();
		String adminuserId=parameter.getAdminuserId();
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		return adminUserService.getSellerInfoById(adminuserId, sellerId, basePath);
	}
	
	
	/*
	 * 通用版首页接口
	 * */
	@RequestMapping("/getSellerHomepage")
	@ResponseBody
	public Map<String, Object> getSellerHomepageInfo(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter=ParameterUtil.getParameter(request);
		if (parameter==null) {
			return JsonResponseUtil.getJson(-2,"参数data不是合法的json字符串");
		}
		String sellerId=parameter.getSellerId();
		String adminuserId=parameter.getAdminuserId();
		String userId=parameter.getUserId();
		double lng = StringUtil.isEmpty(parameter.getLng())?0:Double.parseDouble(parameter.getLng());
		double lat = StringUtil.isEmpty(parameter.getLat())?0:Double.parseDouble(parameter.getLat());
		String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		return adminUserService.getSellerHomepageInfo(userId, adminuserId, sellerId, basePath);
	}
	
	/*
	 * 获取商家个人资料
	 * */
	@RequestMapping("/getAdminInfoById")
	@ResponseBody
	public Map<String, Object> getsellerInfoById(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter=ParameterUtil.getParameter(request);
		if (parameter==null) {
			return JsonResponseUtil.getJson(-2, "参数data不是合法的json字符串");
		}
		String userId=parameter.getUserId();
		String sellerId=parameter.getSellerId();
		String adminuserId=parameter.getAdminuserId();
		String basePath=request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		return adminUserService.getAdminUserInfoById(userId,adminuserId, sellerId, basePath);
	}
	
	
	
	/*
	 * 店铺审核资料填写
	 * */
	
	@RequestMapping("/updateStoreInfo")
	@ResponseBody
	public Map<String,Object> updateStoreInfo(HttpServletRequest request,HttpServletResponse response){
		return adminUserService.updateStoreInfo(request, response);
	}
	
	/*
	 * 店铺资料审核
	 * */
	@RequestMapping("/storeVerifyStatus")
	@ResponseBody
	public Map<String, Object> storeVerifyStatus(HttpServletRequest request,HttpServletResponse response){
		return adminUserService.storeVerifyStatus(request, response);
	}
	
	
	/*
	 * 用户审核状态确认返回
	 * */
	@RequestMapping("/returnCheckstatus")
	@ResponseBody
	public Map<String, Object> returnCheckstatus(HttpServletRequest request,HttpServletResponse response){
		return  adminUserService.returnCheckstatus(request, response);
	}
	
	/*
	 * 店铺分类
	 * */
	@RequestMapping("/getShopcategoryInfo")
	@ResponseBody
	public Map<String, Object> getShopcategoryInfo(HttpServletRequest request,HttpServletResponse response){
		return shopCategoryService.getShopcategoryInfo(request, response);
	}
	
	
	/*
	 * 店铺分类
	 * */
	@RequestMapping("/getServiceTime")
	@ResponseBody
	public Map<String, Object> getServiceTime(HttpServletRequest request,HttpServletResponse response){
		return shopCategoryService.getShopcategoryInfo(request, response);
	}
	/*
	 * 小程序店铺分类
	 * */
	@RequestMapping("/getShopTypeInfo")
	@ResponseBody
	public Map<String,Object> getShopTypeInfo(HttpServletRequest request,HttpServletResponse response){
		return shopCategoryService.getShopTypeInfo(request, response);
		
	}
	/**
	 * 发布商品分类
	 */
	@RequestMapping("/getPublishGoodsType")
	@ResponseBody
	public Map<String, Object> getPublishGoodsType(HttpServletRequest request,HttpServletResponse response){
		return shopCategoryService.getPublishGoodsType(request, response);
	}
	
}
