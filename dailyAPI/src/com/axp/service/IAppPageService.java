package com.axp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.Users;

public interface IAppPageService extends IBaseService<Users> {

	
	Map<String, Object> getHome(Integer userid, Integer zoneid, Double lat,
			Double lng, String os, String channelId, String appVersion,
			String dip, String machineCode, Integer type,
			HttpServletRequest request);
	
	Map<String, Object> getHomeNew(Integer userid, Integer zoneid, Double lat,
			Double lng, String os, String channelId, String appVersion,
			String dip, String machineCode, Integer type,
			HttpServletRequest request);

	
	Map<String, Object> scoreExchangeMall(HttpServletRequest request);
	
	Map<String, Object> scoreExchangeMall2(HttpServletRequest request);

	Map<String, Object> getSlide(HttpServletRequest request,Integer type);
	Map<String, Object> getPreferential99(HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> specialLocalProduct(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> secondKillMall(HttpServletRequest request, HttpServletResponse response);

	void getGoodsDetail(HttpServletRequest request, HttpServletResponse response);
	
	Map<String, Object> getHomeNew5142(Integer userid, Integer zoneid, Double lat,
			Double lng, String os, String channelId, String appVersion,
			String dip, String machineCode, Integer type,
			HttpServletRequest request,String pid,String pddpid,Integer pageIndex) throws Exception;
	 String findPid(Integer userid,String pid,Integer zoneId);
	 
	 
	/* Map<String, Object> getHomeNew3(Integer userid, Integer zoneid, Double lat,
				Double lng, String os, String channelId, String appVersion,
				String dip, String machineCode, Integer type,
				HttpServletRequest request,String pid);*/
	 
	 
	 
	 Map<String, Object> getTeamGoodsList(HttpServletRequest request,HttpServletResponse response,Integer zoneId,Integer typeId,Integer pageIndex,String search);
	 
	 
	 Map<String, Object> getSlidesOfBannser(HttpServletRequest request,HttpServletResponse response,Integer adminuserId);
	 
	 Map<String,Object> getTeamGoodsOfType(HttpServletRequest request);
	 
	 /**
		 * 获取热门商品关键字
		 * @param request
		 * @param response
		 * @return
		 */
	 Map<String, Object> getGoodsKeywords(HttpServletRequest request, HttpServletResponse response);

	/**
	 * @param userId
	 * @param zoneId
	 * @param lat
	 * @param lng
	 * @param os
	 * @param object
	 * @param v
	 * @param dip
	 * @param object2
	 * @param object3
	 * @param request
	 * @param object4
	 * @param object5
	 * @param i
	 * @return
	 */
	Map<String, Object> getHomeNew104(Integer userid, Integer zoneid, Double lat,
			Double lng, String os, String channelId, String appVersion,
			String dip, String machineCode, Integer type,
			HttpServletRequest request,String pid,String pddpid,Integer pageIndex) throws Exception;

	/**
	 * 
	 * 小程序首页
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getXCXHome(HttpServletRequest request,
			HttpServletResponse response) throws Exception ;
	/**
	 * 获取后台上传图片的信息 
	 * @param pid
	 * @param uri
	 * @param version  默认2
	 * @param request
	 * @return
	 */
	public Map<Integer, Map<String, Object>> getCashshopType2(String pid,
			String uri, Integer version,HttpServletRequest request);
	
	
	
	
}
