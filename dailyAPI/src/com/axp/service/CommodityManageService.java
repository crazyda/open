package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommodityManageService {

	/**
	 * @author zhangLu
	 * 商品管理列表
	 * */
	 Map<String, Object> getCommodityList (HttpServletRequest request,HttpServletResponse response);
}
