package com.axp.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.CommodityType;
import com.axp.domain.Seller;
import com.axp.domain.ShopCategory;
import com.axp.domain.Users;
import com.axp.service.ShopCategoryService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;


@Service
public class ShopCategoryServiceImpl extends BaseServiceImpl<ShopCategory> implements ShopCategoryService{

	@Override
	public Map<String, Object> getShopcategoryInfo(HttpServletRequest request,
			HttpServletResponse response) {
			Map<String, Object> map = new HashMap<>();
			
			try {
				Map<String, Object> data = new HashMap<String, Object>();
				map.put("status", 1);
				map.put("message", "请求成功");
				map.put("data", data);
				map.put("data", getType());
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", -2);
				map.put("message", "请求失败");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return map;
	}
	
	  public List<Map<String,Object>> getType(){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			List<ShopCategory> sclist = shopCategoryDao.getList();//所有分类
			List<ShopCategory> sclist1=new ArrayList<ShopCategory>();
			List<ShopCategory> sclist2=new ArrayList<ShopCategory>();
			
			for(ShopCategory sc1 : sclist){
				if(sc1.getLevel()==1){
					sclist1.add(sc1);
				}else{
					sclist2.add(sc1);
				}
			}
			
			for(ShopCategory sc : sclist1){
				List<Map<String,Object>> lv2List = new ArrayList<Map<String,Object>>();
				Map<String,Object> lv1map = new HashMap<String, Object>();
				lv1map.put("categoryId", sc.getId());
				lv1map.put("categoryName", sc.getName());
				lv1map.put("categoryItems", lv2List);
				for(ShopCategory sc2 : sclist2){
					if(sc2.getShopCategory().getId()==sc.getId()){
						Map<String,Object> lv2map = new HashMap<String, Object>();
						lv2map.put("categoryId",sc2.getId() );
						lv2map.put("categoryName", sc2.getName());
						lv2List.add(lv2map);
					}
				}
				list.add(lv1map);
			}
			return list;
		}
	  
	  public Map<String, Object> getPublishGoodsType(HttpServletRequest request,HttpServletResponse response){
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> dataMap=new HashMap<>();
			dataMap.put("status", 1);
			dataMap.put("message", "操作成功");
			
			List<CommodityType> sclist = commodityTypeDao.getAllType();//所有分类
			List<CommodityType> sclist1=new ArrayList<CommodityType>();
			List<CommodityType> sclist2=new ArrayList<CommodityType>();
			
			for(CommodityType sc1 : sclist){
				if(sc1.getLevel()==1){
					if (!sc1.getName().equals("全部")) {
						sclist1.add(sc1);
					}
				}else{
					sclist2.add(sc1);
				}
				
			}
			
			for(CommodityType sc : sclist1){
				List<Map<String,Object>> lv2List = new ArrayList<Map<String,Object>>();
				Map<String,Object> lv1map = new HashMap<String, Object>();
				lv1map.put("categoryId", sc.getId());
				lv1map.put("categoryName", sc.getName());
				lv1map.put("categoryItems", lv2List);
				for(CommodityType sc2 : sclist2){
					if(sc2.getCommodityType().getId()==sc.getId()){
						Map<String,Object> lv2map = new HashMap<String, Object>();
						lv2map.put("categoryId",sc2.getId() );
						lv2map.put("categoryName", sc2.getName());
						lv2List.add(lv2map);
					}
				}
				list.add(lv1map);
			}
				dataMap.put("data",list);
			return dataMap;
		}

	
	@Override
	public Map<String, Object> getShopTypeInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String type2 = request.getParameter("type2");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> dataMap=new HashMap<>();
		
		QueryModel model = new QueryModel();
		if("1".equals(type)){
			model.clearQuery();
			model.combPreEquals("isValid", true);
			model.combPreEquals("level", 1);
			List<ShopCategory> shopType_1 = dateBaseDAO.findLists(ShopCategory.class, model);
			dataMap.put("shopType_1", shopType_1);
		}else{
			model.clearQuery();
			model.combPreEquals("isValid", true);
			model.combPreEquals("level", 2);
			model.combPreEquals("shopCategory.id", Integer.valueOf(type2),"parentId");
			List<ShopCategory> shopType_1 = dateBaseDAO.findLists(ShopCategory.class, model);
			dataMap.put("shopType_1", shopType_1);
		}
		
		return dataMap;
	}
	
}