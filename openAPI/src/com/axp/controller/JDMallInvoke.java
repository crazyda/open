package com.axp.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.domain.OpenClient;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.UrlUtil;

@Controller
@RequestMapping("jdapi")
public class JDMallInvoke extends BaseController {

    //获取首页内容
    @RequestMapping("/router")
    @ResponseBody
    public Map<String, Object> getConfig(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map;
    
        
        Parameter parameter = ParameterUtil.getParameter(request);
        
        String clientId = parameter.getClient_id();
        String clientSecret = parameter.getClient_secret();
        String appId = parameter.getApp_id();
        String type = parameter.getType();
        String sign = parameter.getSign();
        OpenClient oc = openClientService.getOpenClientByClientId(clientId);
        
        
        if(StringUtils.isBlank(sign) || StringUtils.isBlank(type) || StringUtils.isBlank(appId)|| StringUtils.isBlank(clientId) || StringUtils.isBlank(clientSecret)){
        	return JsonResponseUtil.getJson(-0x02, "必要参数不能为空！");
        }
        
        if(oc==null ){
        	 return JsonResponseUtil.getJson(-0x02, "client_id错误！请联系管理员");
        }else{
        	if(clientSecret.equals(oc.getClientSecret()) && appId.equals(oc.getAppId())){
        		try {
					if(MD5Util.getComp(clientId, clientSecret, type, sign, appId)){//验证通过
						return JsonResponseUtil.getJson(-0x02, "签名验证通过");
					
					}else{
						return JsonResponseUtil.getJson(-0x02, "签名验证不通过！");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}else{
        		 return JsonResponseUtil.getJson(-0x02, "client_secret或者app_id参数错误！");
        	}
        	
         }
         
        
        return JsonResponseUtil.getJson(-0x02, "client_id错误！");
        
    }

    
    
  /**
   * 获取京东拼购商品 da
   * @param request
   * @param response
   * @return
   */
    @RequestMapping("/jdgoods")
    @ResponseBody
    public Map<String, Object> jdGoods(HttpServletRequest request, HttpServletResponse response) {
        return openJDGoodsService.jdGoods();
    }
    
    /**
     * 获取京东商品分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/jdClassify")
    @ResponseBody
    public Map<String,Object> jdClassify(HttpServletRequest request, HttpServletResponse response){
    	String parentId = "";
    	String grade = "";
		return openJDClassifyService.jdClassify(parentId,grade);
    	
    }
    /**
     * 获取京东优惠商品和爆款商品  同步使用
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/coponGoods")
    @ResponseBody
    public Map<String,Object> coponGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDQueryCoponGoodsService.coponGoods();
    	
    }
    /**
     * 获取京东爆款商品 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findExplosiveGoods")
    @ResponseBody
    public Map<String,Object> findExplosiveGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDQueryCoponGoodsService.findExplosiveGoods(request,response);
    	
    }
    
    /**
     * 获取京东优惠商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findCoponGoods")
    @ResponseBody
    public Map<String,Object> findCoponGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDQueryCoponGoodsService.findCoponGoods(request,response);
    	
    }
    
    
    
    /**
     * 请求京东接口 优惠券商品查询接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchCouponGoods")
    @ResponseBody
    public Map<String,Object> searchCouponGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDQueryCoponGoodsService.searchCouponGoods(request,response);
    	
    }
    /**
     * 提供本地优惠券商品同步
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/synLocalGoods")
    @ResponseBody
    public Map<String,Object> synLocalGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDQueryCoponGoodsService.getCoponGoods(request,response);
    	
    }
    
    
    
    /**
     * 联盟微信手q通过subUnionId获取推广链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCodeBySubUnionId")
    @ResponseBody
    public Map<String,Object> getCodeBySubUnionId(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDGoodsService.getCodeBySubUnionId(request,response);
    	
    }
    
    /**
     * 优惠券领取情况查询接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/couponGetInfo")
    @ResponseBody
    public Map<String,Object> couponGetInfo(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDGoodsService.couponGetInfo(request,response);
    	
    }
    /**
     * 优惠券,商品二合一转接API-通过subUnionId获取推广链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCodeBySubUnionId2")
    @ResponseBody
    public Map<String,Object> getCodeBySubUnionId2(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDGoodsService.getCodeBySubUnionId2(request,response);
    	
    }
    /**
     * 同步优惠券商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/synCouponGoods")
    @ResponseBody
    public Map<String,Object> synCouponGoods(HttpServletRequest request, HttpServletResponse response){
    	
    	return openJDQueryCoponGoodsService.synCouponGoods();
    	
    }
    /**
     * 通过skuId 请求到商品详细情况
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/goodsInfo")
    @ResponseBody
    public Map<String,Object> goodsInfo(HttpServletRequest request, HttpServletResponse response){
		String skuId = null;
    	return openJDQueryCoponGoodsService.getgoodsInfo(request,response,skuId);
    }
    
    
    /**
     * 增量查询订单做接口请求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/queryOrderList")
    @ResponseBody
    public Map<String,Object> queryOrderList(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDGoodsService.queryOrderList(request,response);
    }
    
  /**
   * 同步订单到本地
   * @param request
   * @param response
   * @return
   */
    @RequestMapping("/synOrderListSyn")
    @ResponseBody
    public Map<String,Object> synOrderListSyn(HttpServletRequest request, HttpServletResponse response){
		
    	return openJDGoodsService.synOrderListSyn(request,response);
    }
    
   
    
    
}
