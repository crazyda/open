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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.domain.OpenClient;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.UrlUtil;

@Controller
@RequestMapping("api")
public class MallInvoke extends BaseController {

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

    //没有分佣条件的
    @RequestMapping("/goods")
    @ResponseBody
    public Map<String, Object> goods(HttpServletRequest request, HttpServletResponse response) {
        return openGoodsService.getPDDGoodsList();
    }
    
    
  
    
    //分佣条件获取
    @RequestMapping("/goodsNew")
    @ResponseBody
    public Map<String, Object> goodsNew(HttpServletRequest request, HttpServletResponse response) {
        return openGoodsService.getPDDGoods();
    }
    
    

    
    //获取商品内容
    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response) {
        return openGoodsService.update(request);
    }
    

    /**
     * 创建代理  并绑定pid---
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/createAgent")
    @ResponseBody
    public Map<String,Object> createAgent(HttpServletRequest request, HttpServletResponse response){
    	return agentService.crateAgent(request);
    }
    
    /**
     * 代理分成明细查询 暂时不用--
     * @param openId
     * @param extUserId
     * @param sign
     * @return
     */
    @RequestMapping("/agent/detail")
    @ResponseBody
    public Map<String,Object> detail(String openId ,String extUserId,String sign){
    	return agentService.detail(openId,extUserId,sign);
    	
    }
    /**
     * 代理修改	暂时不用--
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/changeAgent")
    @ResponseBody
    public Map<String,Object> changeAgent(HttpServletRequest request, HttpServletResponse response){
    	return agentService.changeAgent(request);
    	
    }
    /**
     * 订单明细查询 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/order")
    @ResponseBody
    public Map<String,Object> order(HttpServletRequest request, HttpServletResponse response){
    	return openGoodsService.order(request);
    	//return openGoodsService.orderNew(request);
    }
    /**
     * 订单数量查询,和保存已结算的列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/order/orderCount")
    @ResponseBody
    public Map<String,Object> orderCount(HttpServletRequest request, HttpServletResponse response){
    	
    	return null;
    }
    /**
     * 收入数据--
     * @param extUserId
     * @param openId
     * @param startTime
     * @param endTime
     * @param sign
     * @return
     */
    @RequestMapping("/order/income")
    @ResponseBody
    public Map<String,Object> income(String extUserId,String openId,Integer startTime,Integer endTime,String sign){
    	return openGoodsService.findByIncome(extUserId,openId,startTime,endTime,sign);
    }
    
    /**
     * 商品列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/product")
    @ResponseBody
    public Map<String,Object> product(HttpServletRequest request, HttpServletResponse response){
    	return openGoodsService.findByProduct(request);
    	//return openGoodsService.findByProductByPdd(request);
    }
    
    /**
     * 商品详情和生成海报--
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/product/poster")
    @ResponseBody
    public Map<String,Object> poster(HttpServletRequest request, HttpServletResponse response){
    	return openGoodsService.findByPoster(request);
    }
    /**
     * 上传文章, 支持多文章上传--
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/article")
    @ResponseBody
    public Map<String,Object> article(HttpServletRequest request, HttpServletResponse response){
    	
    	return openClientService.updataByArticle(request);
    }
    /**
     * 查询文章列表--
     * @param cid 类别id
     * @param search 搜索内容
     * @return
     */
    @RequestMapping("/findarticle")
    @ResponseBody
    public Map<String,Object> article(String cid,String search){
    	
    	return openClientService.findArticleByCidAndSearch(cid,search);
    }
    /**
     * 获取账户余额  
     * @param openId
     * @param extUserId
     * @param sign
     * @return
     */
    @RequestMapping("/agent/balance")
    @ResponseBody
    public Map<String,Object> balance(HttpServletRequest request, HttpServletResponse response){
    	
    	Parameter parameter = ParameterUtil.getParameter(request);
    	String openId = parameter.getClient_id();
    	String sign = parameter.getSign();
    	return openClientService.findBalanceByOpenId(openId,sign);
    }
    
    /**
     *  余额提现  一提现就是把所有的钱全部提出来
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/withdraw")
    @ResponseBody
    public Map<String,Object> withdraw(HttpServletRequest request, HttpServletResponse response){
    	return openClientService.updatawithdraw(request);
    }
    
    /**
     *  余额提现 提现列表 da
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/getWithdrawList")
    @ResponseBody
    public Map<String,Object> getWithdrawList(HttpServletRequest request, HttpServletResponse response){
    	
    	return openClientService.getWithdrawList(request,response);
    }
    
    /**
     * 生成pdd随机代理Pids  最多20个  相当于是多进宝推广位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/pddPids")
    @ResponseBody
    public Map<String,Object> pddPids(HttpServletRequest request, HttpServletResponse response){
    	
    	return openClientService.createPddPids(request);
    }
    /**
     *  公众号的pid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/agent/createPddPid")
    @ResponseBody
    public Map<String,Object> createPddPid(HttpServletRequest request, HttpServletResponse response){
    	
    	return openClientService.createPddPid(request);
    }
    
    
    
    /**
     * 查询 pid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findPddPid")
    @ResponseBody
    public Map<String,Object> findPddPid(HttpServletRequest request, HttpServletResponse response){
    	
    	return openClientService.findPddPid(request);
    }
    /**
     * 生成拼多多推广链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/OpenPddPromotionUrl")
    @ResponseBody
    public Map<String,Object> OpenPddPromotionUrl(HttpServletRequest request, HttpServletResponse response){
		//gongLIst  + [ ]  多传一个 goodid 
    	
    	return openPddPromotionUrlService.GeneratePddPromotionUrl(request);
    	
    }
    /**
     * 生成商城推广链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/OpenMallPromUrl")
    @ResponseBody
    public Map<String,Object> OpenMallPromUrl(HttpServletRequest request, HttpServletResponse response){
		
    	
    	return openPddPromotionUrlService.OpenMallPromUrl(request);
    	
    }
   /**
    * 公众号同步后 回调修改订单的状态
    * @param request
    * @param response
    * @return
    */
    @RequestMapping("/ModificationGoodsIsWithdraw")
    @ResponseBody
    public Map<String,Object> ModificationGoodsIsWithdraw(HttpServletRequest request, HttpServletResponse response){
		
    	return openGoodsService.ModificationGoodsIsWithdraw(request);
    	
    }
    
    
    @RequestMapping("/locaOrder")
    @ResponseBody
    public Map<String,Object> locaOrder(HttpServletRequest request, HttpServletResponse response){
    	return openGoodsService.locaOrder(100, 1, "1541572020");
    	
    }
    
    /**
     * 拼多多品牌
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/catsGet")
    @ResponseBody
    public Map<String,Object> catsGet(HttpServletRequest request, HttpServletResponse response){
		return openGoodsService.catsGet(request);
    	
    }
    /**
     * 商品分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getClassify")
    @ResponseBody
    public Map<String,Object> getClassify(HttpServletRequest request, HttpServletResponse response){
    	return openClassifyService.findAll(request);
    	
    }
    
   
    
    
    
    /**
     * 主题列表查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/themeList")
    @ResponseBody
    public Map<String,Object>themeList(HttpServletRequest request, HttpServletResponse response){
		return openThemeService.thmemList(request);
    	
    }
    /**
     * 分享二维码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCard")
    @ResponseBody
    public Map<String,Object> getCard(HttpServletRequest request, HttpServletResponse response){
		return openGoodsService.getCard(request);
    	
    }
    
    /**
     * pdd增量查询的 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findIncrementList")
    @ResponseBody
    public Map<String,Object> findIncrementList(HttpServletRequest request, HttpServletResponse response){
		return openGoodsService.findIncrementList(request);
    } 
    /**
     * 主题商品查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findThemeGoods")
    @ResponseBody
    public Map<String,Object> findThemeGoods(HttpServletRequest request, HttpServletResponse response){
    	String themeId = "";
		return openThemeGoodsService.findThemeGoods(request,themeId);
    } 
    

    
    /**
     * 商品分享图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getImage")
    @ResponseBody
    public Map<String,Object> getImage(HttpServletRequest request, HttpServletResponse response){
		return openGoodsService.getImage(request);
    	
    }
    
    

    /**
     * 主题推广链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/generateThemeUrl")
    @ResponseBody
    public Map<String, Object> generateThemeUrl(HttpServletRequest request, HttpServletResponse response){
		
    	return openThemeGoodsService.generateThemeUrl(request);
    	
    }
    /**
     * 商品详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/goodsDetail")
    @ResponseBody
    public Map<String, Object> goodsDetail(HttpServletRequest request, HttpServletResponse response){
		
    	return openGoodsService.goodsDetail(request);
    	
    }
    
    /**
     * 同步主题商品 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/synThemeGoods")
    @ResponseBody
    public Map<String, Object> synThemeGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openThemeService.synThmemList(request);
    	
    }
    /**
     * 获取本地商品
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getThmemGoods")
    @ResponseBody
    public Map<String, Object> getThmemGoods(HttpServletRequest request, HttpServletResponse response){
		
    	return openThemeService.getThmemGoods(request);
    	
    }
    
    /**
     * 页面跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/choiceViwe")
    public String choiceViwe(HttpServletRequest request, HttpServletResponse response){
		
    	openClassifyService.choiceViwe(request,response);
		return "/choiceViwe";
    	
    }
    @RequestMapping("/save")
    public String save( HttpServletRequest request,String selectApp,
                       @RequestParam("pddifys") List<Integer> pddifys, String start_rate,String end_rate) {
    	openClassifyService.doSave(request,selectApp,pddifys,start_rate,end_rate);
        return "redirect:choiceViwe";
    }
}
