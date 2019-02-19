package com.axp.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.ShopCategory;

public interface ShopCategoryService extends IBaseService<ShopCategory>{

	Map<String, Object> getShopcategoryInfo(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 发布商品分类接口
	 * @return
	 */
	Map<String,Object> getPublishGoodsType(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 小程序商铺分类
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> getShopTypeInfo(HttpServletRequest request,
			HttpServletResponse response);
	
	
}