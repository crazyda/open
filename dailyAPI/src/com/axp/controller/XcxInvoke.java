/**
 * 2018-11-9
 * Administrator
 */
package com.axp.controller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.ReGoodsorderDao;
import com.axp.dao.UsersDAO;
import com.axp.domain.CashmoneyRecord;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Users;
import com.axp.domain.VisitLog;
import com.axp.service.IOrderService;
import com.axp.util.CalcUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.SSLClient;
import com.axp.util.UrlUtil;
import com.thoughtworks.xstream.XStream;
import com.weixin.bean.OrderInfo;
import com.weixin.bean.OrderReturnInfo;
import com.weixin.config.WeixinConfig;
import com.weixin.util.PayUtil;
import com.weixin.util.WeixinUtil;

/**
 * @author da
 * @data 2018-11-9上午9:56:10
 */
@Controller
@RequestMapping("invoke/xcx")
public class XcxInvoke extends BaseController{
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private ReGoodsorderDao reGoodsorderDao;
	
	@Autowired
	private DateBaseDAO dateBaseDao;
	
	protected UrlUtil urlUtil;
    @RequestMapping("/xcxLogin")
    @ResponseBody
    public Map<String, Object> xcxLogin(HttpServletResponse response, HttpServletRequest request) {
		
    	return  usersService.xcxLogin(response, request);
    }
	
    
    @RequestMapping("/scoreORmoney")
    @ResponseBody
    public Map<String,Object> scoreORmoney(HttpServletResponse response, HttpServletRequest request) {
    	Map<String,Object> statusMap = new HashMap<String,Object>();
    	
    	String type = "1";
    	if("1".equals(type)){
    		statusMap.put("score", "积分");
    		statusMap.put("isShop", 1);
    	}else{
    		statusMap.put("score", "");
    		statusMap.put("isShop", 0); //不展示店铺
    	}
    	return statusMap;
    }
  
    
    //获取首页内容 
    @RequestMapping("/xcxGetHome")
    @ResponseBody
    public Map<String, Object> xcxGetHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> statusMap = new HashMap<String,Object>();
		statusMap = appPageService.getXCXHome(request,response);
		return statusMap;
    }
    
  //获取获取商品详情
    @RequestMapping("/xcxGetGoods")
    @ResponseBody
    public Map<String,Object> getGoods(HttpServletRequest request, HttpServletResponse response){
    	return MallList.xcxGoods(request,response);
    	
    }
    
    //创建临时订单
    @RequestMapping("/createTempOrderList")
    public Map<String,Object> createTempOrderList(HttpServletRequest request, HttpServletResponse response){
    	return orderService.createTempOrderList(request,response);
    	
    }
    
    
    
    //小程序个人中心里面的用户二维码 
    @RequestMapping("/weixin/token")
    @ResponseBody
    public Map<String,Object> token(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> statusMap = usersService.getUserToken(request,response);
    	return statusMap;
    	
    }
    //生成带二维码的海报图
    @RequestMapping("/weixin/getCard")
    @ResponseBody
    public Map<String, Object> getCard(HttpServletRequest request,HttpServletResponse response) {
    	Map<String,Object> statusMap = new HashMap<>();
		statusMap = usersService.getCard(request,response);
		return statusMap;
	}

    //开店接口
    @RequestMapping("/weixin/isSeller")
    @ResponseBody
    public Map<String, Object> isSeller(HttpServletRequest request,HttpServletResponse response) {
		String userId = request.getParameter("userId")==""?"-1":request.getParameter("userId");
    	return usersService.isSeller(Integer.valueOf(userId));
	}
    
    //小程序 用户下载app
    @RequestMapping("/userApp")
    @ResponseBody
    public Map<String, Object> userApp(HttpServletRequest request,HttpServletResponse response) {
    	Map<String,Object> statusMap = new HashMap<>();
    	statusMap.put("img", "https://mtjf.518wk.cn/api/xcx/userAppImg.jpg");
    	return statusMap;
	}
    
}
