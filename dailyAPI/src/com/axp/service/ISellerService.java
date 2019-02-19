package com.axp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.SeLive;
import com.axp.domain.Seller;

public interface ISellerService {

	Map<String, Object> getSellerMainPageDate(HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getSellerInfo(HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object>saveSellerAddress(HttpServletRequest request,
			HttpServletResponse response);
	
	
	Map<String, Object>applyGoods(HttpServletRequest request,
			HttpServletResponse response);
	
	
	Map<String, Object> getSellerListDate(Integer pageIndex, Integer typeId,
			String local, Integer userId,Integer zoneId, Integer areaId, String search,
			Double lat, Double lng, HttpServletRequest request,
			HttpServletResponse response);

	
	Map<String, Object> getSellerListDateForNew(Integer pageIndex, Integer typeId,
			String local, Integer userId,Integer zoneId, Integer areaId, String search,
			Double lat, Double lng, HttpServletRequest request,
			HttpServletResponse response);
	
	Map<String, Object> getSellerListBySearch(String xcx, Integer pageIndex, Integer typeId,
			String local, Integer userId,Integer zoneId, Integer areaId, String search,
			Double lat, Double lng, HttpServletRequest request,
			HttpServletResponse response);
	

	
	 public Seller getSellerByAdmin(Integer current_user_id);
	    
		public SeLive getSeliveBySeller(Integer current_seller_id);
		
		public Seller getSellerByUsersId(Integer usersId);

		public Seller getSellerByUsersId2(Integer usersId);
	    /**
	     * 根据id获取对象；
	     *
	     * @param sellerId
	     * @return
	     */
	    Seller findById(Integer sellerId);

	    /**
		 * 店铺管理
		 */
	   Map<String, Object> shopManage(HttpServletRequest request, HttpServletResponse response);
	    
	    
	    
	    
	   /**
	    * @author ZhangLu
		  * 店铺管理信息
		  * @param request
		  * @param response
		  * @return
		  */
	    Map<String, Object> getstoreInfo(HttpServletRequest request,
				HttpServletResponse response);
	    
	    /**
	     * @author ZhangLu
		  * 删除推广
		  * @param request
		  * @param response
		  * @return
		  */
	    Map<String, Object> delMallOfGoods(HttpServletRequest request,HttpServletResponse response);
	    
	    
	    /**
	     * @author ZhangLu
		  * 补充库存
		  * @param request
		  * @param response
		  * @return
		  */
	    Map<String, Object> reStock(HttpServletRequest request,HttpServletResponse response);
	    
	    /**
	     * 商品首页
	     * @param request
	     * @param response
	     * @return
	     */
	    Map<String, Object> getGodosListByStatus(HttpServletRequest request,
				HttpServletResponse response);
	    
	    /**
		  * 发布商品
		  * @param request
		  * @param response
		  * @return
		  */
		 Map<String, Object> publishGoods(HttpServletRequest request, HttpServletResponse response);
		 
		 /**
		  * @author ZhangLu
		  * 下架商品
		  * @param request
		  * @param response
		  * @return
		  */
		 Map<String, Object> soldOutGoods(HttpServletRequest request,HttpServletResponse response);
		 
		 
		 
		 /**
		  * @author ZhangLu
		  * 上架商品
		  * @param request
		  * @param response
		  * @return
		  */
		 
		 Map<String, Object> soldUpGoods(HttpServletRequest request,HttpServletResponse response);
		 
		 
		 /**
		  * @author ZhangLu
		  * 我的推广
		  * @param request
		  * @param response
		  * @return
		  */
		Map<String, Object> getMyextension(HttpServletRequest request,HttpServletResponse response);
		
		 /**
		  * @author ZhangLu
		  * 获取规格
		  * @param request
		  * @param response
		  * @return
		  */
		Map<String, Object> getStandardDetails(HttpServletRequest request,HttpServletResponse response);
		 /**
		  * @author ZhangLu
		  * 获取时间
		  * @param request
		  * @param response
		  * @return
		  */
		Map<String, Object> getCashOfTime(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 转推广获取规格
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> getStandardDetail(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 获取已发布的商品信息 用于编辑发布的商品
		 * @param request
		 * @param response
		 * @return
		 */
		
		Map<String, Object> getGoodsdetails(HttpServletRequest request,HttpServletResponse response);
		
		
		/**
		 * 获取推广商品数据回显
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> getExtensionGoods(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 获取开通合伙人/商家信息
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> obtainUserIdentity(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 商家活动版块
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> getSellerActivities(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 所有积分商品
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String,Object> getSendScoreGoods(HttpServletRequest request,HttpServletResponse response);
		/**
		 * 通过经纬度获取百度的实际地址名称
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String,Object> getMapBaiDu(HttpServletRequest request,HttpServletResponse response);
		
		/**
		 * 给首页使用的
		 * @param pageIndex
		 * @param typeId
		 * @param local
		 * @param userId
		 * @param zoneId
		 * @param areaId
		 * @param search
		 * @param lat
		 * @param lng
		 * @param request
		 * @param response
		 * @return
		 */
		 List<Map<String,Object>> getSellerListBySearch2(Integer pageIndex,
				Integer typeId, String local, Integer userId, Integer zoneId,
				Integer areaId, String search, Double lat, Double lng,
				HttpServletRequest request, HttpServletResponse response);

		/**
		 * 查询商店分类,和城市列表
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> getSellerTypeAndCity(HttpServletRequest request,
				HttpServletResponse response);
		/**
		 * 获取商家信息
		 * @param request
		 * @param response
		 * @return
		 */
		Map<String, Object> getSeller(HttpServletRequest request, HttpServletResponse response);
		
}
