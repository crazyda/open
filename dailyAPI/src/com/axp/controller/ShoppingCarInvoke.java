package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("invoke/order")
public class ShoppingCarInvoke extends BaseController {

	/**
	 * 获取购物车列表接口；
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getShoppingCarList")
	@ResponseBody
	public Map<String, Object> getShoppingCarList(HttpServletRequest request, HttpServletResponse response) {
		return reShoppingCarService.getShoppingCarList(request, response);
	}

	/**
	 * 向购物车中添加数据接口；
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/putShoppingCar")
	@ResponseBody
	public Map<String, Object> putShoppingCar(HttpServletRequest request, HttpServletResponse response) {
		return reShoppingCarService.putShoppingCar(request, response);
	}

	/**
	 * 编辑已存在的购物车对象的数量；
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateShoppingCar")
	@ResponseBody
	public Map<String, Object> updateShoppingCar(HttpServletRequest request, HttpServletResponse response) {
		return reShoppingCarService.updateShoppingCar(request, response);
	}

	/**
	 * 删除已经存在的购物车对象；
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/removeShoppingCar")
	@ResponseBody
	public Map<String, Object> removeShoppingCar(HttpServletRequest request, HttpServletResponse response) {
		return reShoppingCarService.removeShoppingCar(request, response);
	}

}
