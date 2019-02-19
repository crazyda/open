package com.axp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.auth.STSAssumeRoleSessionCredentialsProvider;
import com.axp.dao.DateBaseDAO;
import com.axp.domain.AdminUser;
import com.axp.service.ISellerService;
import com.axp.util.CityUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.StringUtil;
import com.axp.util.UrlUtil;

@Controller
@RequestMapping("invoke/mall")
public class SellerInvoke extends BaseController {

	@Autowired
	private DateBaseDAO dateBaseDAO;
	@Autowired
	private ISellerService sellerService;
	
	@RequestMapping("/getSellerList")
	@ResponseBody
	public Map<String, Object> getSellerList(HttpServletRequest request, HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		Integer userId = -1;
		Integer pageIndex = 1;
		Integer typeId = null;
		String type = "LIST";
		Integer areaId = 0;
		Double lat = 0.0;
		Double lng = 0.0;
		String search = null;
		String cityId = "";
		String shopcategoryId = "";
		Integer zoneId = 0;
		
		
		if(xcx != null){
			userId = StringUtils.isEmpty(request.getParameter("userId"))?-1:Integer.valueOf(request.getParameter("userId"));
			pageIndex = Integer.valueOf(request.getParameter("pageIndex")==""?"1":request.getParameter("pageIndex"));
			lat = Double.valueOf(request.getParameter("lat")==""?"0.0":request.getParameter("lat"));
			lng = Double.valueOf(request.getParameter("lng")==""?"0.0":request.getParameter("lng"));
			zoneId = Integer.valueOf(request.getParameter("zoneId"));
			//Map<String,Double> map = CityUtil.Convert_BD09_To_GCJ02(lat, lng);
			Map<String,Double> map = CityUtil.map_tx2bd(lat, lng);
			lat = map.get("lat");
			lng = map.get("lng");
		}else{
			
			Parameter parameter = ParameterUtil.getParameter(request);
			if (parameter == null) {
				return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
			}
			userId = StringUtils.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
			pageIndex = parameter.getData().getInteger("pageIndex");//分页页码，从1开始
			typeId = parameter.getData().getInteger("typeId");//商家分类	
			type = parameter.getData().getString("type");//商家分类	
			areaId = parameter.getData().getInteger("areaId");//商圈Id	
			lat = StringUtils.isEmpty(parameter.getLat())?null:Double.parseDouble(parameter.getLat());
			lng = StringUtils.isEmpty(parameter.getLng())?null:Double.parseDouble(parameter.getLng());
			search = StringUtils.isBlank(parameter.getData().getString("search"))?null:parameter.getData().getString("search");//搜索	
			cityId = parameter.getData().getString("cityId");//搜索地区
			shopcategoryId = parameter.getData().getString("shopcategoryId");//搜索地区
			zoneId = Integer.valueOf(parameter.getZoneId());
		}
		
		try{
		if(StringUtils.isNotBlank(cityId)){
			zoneId = Integer.parseInt(cityId);
		}
		
		if(StringUtils.isNotBlank(shopcategoryId)){
			typeId = Integer.parseInt(shopcategoryId);
		}
		if(pageIndex<1){
			pageIndex=1;
		}
		
			return sellerService.getSellerListBySearch(xcx ,pageIndex, typeId, type, userId,zoneId, areaId, search, lat, lng, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/getSellerTypeAndCity")
	@ResponseBody
	public Map<String,Object> getSellerTypeAndCity(HttpServletRequest request, HttpServletResponse response){
		
		return sellerService.getSellerTypeAndCity(request, response);
		
	}
	
	@RequestMapping("/getSellerListForNew")
	@ResponseBody
	public Map<String, Object> getSellerListNew(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		Integer zoneId =0;
		if(StringUtils.isNotBlank(parameter.getZoneId())){
			 zoneId = Integer.parseInt(parameter.getZoneId());
		}
		
		Integer userId = StringUtils.isEmpty(parameter.getUserId())?-1:Integer.parseInt(parameter.getUserId());
		Integer pageIndex = parameter.getData().getInteger("pageIndex");//分页页码，从1开始
		Integer typeId = parameter.getData().getInteger("typeId");//商家分类	
		String local = parameter.getData().getString("type");//商家分类	
		Integer areaId = parameter.getData().getInteger("areaId");//商圈Id	
		Double lat = StringUtils.isEmpty(parameter.getLat())?null:Double.parseDouble(parameter.getLat());
		Double lng = StringUtils.isEmpty(parameter.getLng())?null:Double.parseDouble(parameter.getLng());
		String search = StringUtils.isBlank(parameter.getData().getString("search"))?null:parameter.getData().getString("search");//搜索	
		String os = parameter.getOs();
		String v = parameter.getAppVersion();
		if(pageIndex<1){
			pageIndex=1;
		}
		return sellerService.getSellerListBySearch(null,pageIndex, typeId, local, userId,zoneId, areaId, search, lat, lng, request, response);
	}
	
	

	@RequestMapping("/getSeller")
	@ResponseBody
	public Map<String, Object> getSellerInfo(HttpServletRequest request, HttpServletResponse response) {
		return sellerService.getSellerMainPageDate(request, response);
	}
	
	@RequestMapping("/getSellerInfoById")
	@ResponseBody
	public Map<String, Object> getSellerInfoById(HttpServletRequest request, HttpServletResponse response) {
		
		return sellerService.getSellerMainPageDate(request, response);
	}
	
	
	
	@RequestMapping("/postSellerAddress")
	@ResponseBody
	public Map<String, Object> postSellerAddress(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.saveSellerAddress(request, response);
	}
	

	//推广
	@RequestMapping("/applyGoods")
	@ResponseBody
	public Map<String, Object> applyGoods(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.applyGoods(request, response);
	}
	
	
	

	
	/**
	 * 店铺管理
	 */
	@RequestMapping("/shopManage")
	@ResponseBody
	public Map<String, Object> shopManage(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.shopManage(request, response);
	}
	
	/**
	 * @author ZhangLu
	 * 店铺管理--已编辑状态
	 * */
	@RequestMapping("/getstoreInfo")
	@ResponseBody
	public Map<String, Object> getstoreInfo(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter =ParameterUtil.getParameter(request);
		if (parameter==null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.getstoreInfo(request, response);
	}
	
	/**
	 * 商品管理首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getGodosListByStatus")
	@ResponseBody
	public Map<String, Object> getGodosListByStatus(HttpServletRequest request,
			HttpServletResponse response){
		return sellerService.getGodosListByStatus(request, response);
	}

	/**
	 * @author ZhangLu
	 * 删除推广
	 * */
	@RequestMapping("/delMallOfGoods")
	@ResponseBody
	public Map<String, Object> delMallOfGoods(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.delMallOfGoods(request, response);
	}
	
	
	/**
	 * @author ZhangLu
	 * 补充库存
	 * */
	@RequestMapping("/reStock")
	@ResponseBody
	public Map<String, Object> reStock(HttpServletRequest request,HttpServletResponse response){
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02, "参数data不是合法的json字符串");
		}
		return sellerService.reStock(request, response);
	}
	
	
	/**
	 * 商品发布
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/publishGoods")
	@ResponseBody
	public  Map<String, Object> publishGoods(HttpServletRequest request,
			HttpServletResponse response){
		return sellerService.publishGoods(request, response);
	}

	/**
	 * @author ZhangLu
	 * 商品下架
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/soldOutGoods")
	@ResponseBody
	public Map<String, Object> soldOutGoods (HttpServletRequest request,HttpServletResponse response){
		return sellerService.soldOutGoods(request, response);
	}
	
	/**
	 * @author ZhangLu
	 * 商品上架
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/soldUpGoods")
	@ResponseBody
	public Map<String, Object> soldUpGoods (HttpServletRequest request,HttpServletResponse response){
		return sellerService.soldUpGoods(request, response);
	}
	
	/**
	 * @author ZhangLu
	 * 我的推广
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMyextension")
	@ResponseBody
	public Map<String, Object> getMyextension(HttpServletRequest request,HttpServletResponse response){
		return sellerService.getMyextension(request, response);
	}

	
	/**
	 * @author ZhangLu
	 * 获取规格
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getStandardDetails")
	@ResponseBody
	public Map<String, Object> getStandardDetails (HttpServletRequest request,HttpServletResponse response){
		return sellerService.getStandardDetails(request, response);
	}
	/**
	 * @author ZhangLu
	 * 获取时间段
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCashOfTime")
	@ResponseBody
	public Map<String, Object> getCashOfTime (HttpServletRequest request,HttpServletResponse response){
		return sellerService.getCashOfTime(request, response);
	}
	
	
	/**
	 * 获取转推广的规格
	 * chy
	 */
	@RequestMapping("/getStandardDetail")
	@ResponseBody
	public Map<String, Object> getStandardDetail (HttpServletRequest request,HttpServletResponse response){
		return sellerService.getStandardDetail(request, response);
	}
	
	/**
	 * 获取商家版商品信息
	 */
	@RequestMapping("/getGoodsdetails")
	@ResponseBody
	public Map<String, Object> getGoodsdetails (HttpServletRequest request,HttpServletResponse response){
		return sellerService.getGoodsdetails(request, response);
	}
	
	
	/*
	 * 获取商品分类  商家主页微信小程序模块,目前暂时隐藏
	 * */
	@RequestMapping("/getWechatTypes")
	@ResponseBody
	public Map<String, Object> getWechatTypes(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminUserId = parameter.getAdminuserId();
			String pageIndex = parameter.getData().getString("pageIndex");
			if (StringUtils.isNotBlank(adminUserId)) {
				statusMap=UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/getWechatTypes?admin_id="+adminUserId+"&pageIndex="+pageIndex);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statusMap;
	}
	
	
	/*
	 * 新建商品分类
	 * */
	@RequestMapping("/createType")
	@ResponseBody
	public Map<String, Object> createType(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String typeName = parameter.getData().getString("typeName");
			String typeDistinction = parameter.getData().getString("typeDistinction");
			String parentTypeId = parameter.getData().getString("parentTypeId");
			if (StringUtils.isNotBlank(adminuserId)) {
				statusMap = UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/createType?admin_id="+adminuserId+"&typeName="+typeName+"&typeDistinction="+typeDistinction+"&parentTypeId="+parentTypeId);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statusMap;
	}
	
	/*
	 * 编辑商品分类
	 * */
	@RequestMapping("/editType")
	@ResponseBody
	public Map<String, Object> editType(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String typeDistinction = parameter.getData().getString("typeDistinction");
			String parentTypeId = parameter.getData().getString("parentTypeId");
			String typeName = parameter.getData().getString("typeName");
			String secTypeId = parameter.getData().getString("secTypeId");
			if (StringUtils.isNotBlank(adminuserId)) {
				statusMap = UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/editType?admin_id="+adminuserId+
						"&parentTypeId="+parentTypeId+"&secTypeId="+secTypeId+"&typeName="+typeName+"&typeDistinction="+typeDistinction);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！"); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}
	
	/*
	 * 删除商品分类
	 * */
	@RequestMapping("/delType")
	@ResponseBody
	public Map<String, Object> delType(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String typeDistinction = parameter.getData().getString("typeDistinction");
			String parentTypeId = parameter.getData().getString("parentTypeId");
			String secTypeId = parameter.getData().getString("secTypeId");
			if(StringUtils.isNotBlank(adminuserId)){
				statusMap=UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/delType?admin_id="+adminuserId+
						"&typeDistinction="+typeDistinction+"&parentTypeId="+parentTypeId+"&secTypeId="+secTypeId);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}
	
	/*
	 *  获取商品信息
	 * */
	@RequestMapping("/getWechatGoodsByStatus")
	@ResponseBody
	public Map<String, Object> getWechatGoodsByStatus(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String typeId = parameter.getData().getString("typeId");
			String status = parameter.getData().getString("status");
			String pageIndex = parameter.getData().getString("pageIndex");
			if(StringUtils.isNotBlank(adminuserId)){
				statusMap=UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/getWechatGoodsByStatus?admin_id="+adminuserId+
						"&typeId="+typeId+"&status="+status+"&pageIndex="+pageIndex);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}
	
	
	/*
	 *  选择商品添加分类
	 * */
	@RequestMapping("/postSelectedGoods")
	@ResponseBody
	public Map<String, Object> postSelectedGoods(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String typeId = parameter.getData().getString("typeId");
			String selectedGoodsId = parameter.getData().getString("selectedGoodsId");
			if(StringUtils.isNotBlank(adminuserId)){
				statusMap=UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/postSelectedGoods?admin_id="+adminuserId+
						"&typeId="+typeId+"&selectedGoodsId="+selectedGoodsId);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}
	
	
	/*
	 *   删除选择该分类的商品
	 * */
	@RequestMapping("/delSelectedGoods")
	@ResponseBody
	public Map<String, Object> delSelectedGoods(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> statusMap = new HashMap<String, Object>();
		try {
			Parameter parameter = ParameterUtil.getParameter(request);
			String adminuserId = parameter.getAdminuserId();
			String selectedGoodsId = parameter.getData().getString("selectedGoodsId");
			if(StringUtils.isNotBlank(adminuserId)){
				statusMap=UrlUtil.getTaoKeToMap("http://seller.aixiaoping.com/Json/Index/delSelectedGoods?admin_id="+adminuserId+"&selectedGoodsId="+selectedGoodsId);
			}else{
				return JsonResponseUtil.getJson(-2, "请先登录账号！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMap;
	}
	
	
	
	/*
	 * 申请合伙人界面
	 * */
	@RequestMapping("/obtainUserIdentity")
	@ResponseBody
	public Map<String, Object> obtainUserIdentity(HttpServletRequest request,HttpServletResponse response){
		return sellerService.obtainUserIdentity(request, response);
	}
	
	
	
	/**
	 * 查询商家 活动 da
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSellerActivities")
	@ResponseBody
	public Map<String, Object> getSellerActivities(HttpServletRequest request,HttpServletResponse response){
		return sellerService.getSellerActivities(request, response);
	}
	/**
	 * 所有送积分商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSendScoreGoods")
	@ResponseBody
	public Map<String ,Object> getSendScoreGoods(HttpServletRequest request,HttpServletResponse response){
		
		
		return sellerService.getSendScoreGoods(request,response);
		
	}
	
	/**
	 *   da
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMapBaiDu")
	@ResponseBody
	public Map<String,Object> getMapBaiDu(HttpServletRequest request,HttpServletResponse response){
		return sellerService.getMapBaiDu(request,response);
		
	}
	/**
	 * 开店必读
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/aboutGuide")
	public String aboutGuide(HttpServletRequest request,HttpServletResponse response){
		return  "messageCenter/aboutGuide";
		
	}
	
	@RequestMapping("/getSellers")
	@ResponseBody
	public Map<String,Object> getSeller(HttpServletRequest request,HttpServletResponse response){
		return  sellerService.getSeller(request, response);
		
	}
	
}
