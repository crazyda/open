package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IOrderService;
import com.axp.service.IReBackOrderService;
import com.axp.service.OrderCommentService;

@Controller
@RequestMapping("invoke/order")
public class OrderInvoke extends BaseController{
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IReBackOrderService reBackOrderService;
	@Autowired
	private OrderCommentService orderCommentService;
	
	//生成临时订单列表
	@RequestMapping("/createTempOrderList")
	@ResponseBody
	public Map<String,Object> createTempOrderList(HttpServletRequest request, HttpServletResponse response){		
		return orderService.createTempOrderList(request,response);
		
	}
	
	//提交订单
	@RequestMapping("/confirmOrder")
	@ResponseBody
	public Map<String,Object> confirmOrder(HttpServletRequest request, HttpServletResponse response){	
			return orderService.confirmOrder(request,response);
	}
	//订单列表
	@RequestMapping("/getOrderList")
	@ResponseBody
	public Map<String,Object> getOrderList(HttpServletRequest request, HttpServletResponse response){	
			return orderService.getOrderList(request,response);
	}
	
	//获得订单明细
	@RequestMapping("/getOrder")
	@ResponseBody
	public Map<String,Object> getOrder(HttpServletRequest request, HttpServletResponse response){	
			return orderService.getOrder(request,response);
	}
	
	//取消订单
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public Map<String,Object> cancelOrder(HttpServletRequest request, HttpServletResponse response){	
			return orderService.cancelOrder(request,response);
	}
	//获取评论内容
	@RequestMapping("/getComment")
	@ResponseBody
	public Map<String, Object> getComment(HttpServletRequest request, HttpServletResponse response){
			return orderCommentService.getComment(request, response);
	}
	//获取评论列表
	@RequestMapping("/getCommentList")
	@ResponseBody
	public Map<String, Object> getCommentList(HttpServletRequest request, HttpServletResponse response){
			return orderCommentService.getCommentList(request, response);
	}
	//提交评论列表
	@RequestMapping("/putCommentList")
	@ResponseBody
	public Map<String, Object> putCommentList(HttpServletRequest request, HttpServletResponse response){
		return orderCommentService.putCommentList(request, response);
	}
	//获取商家订单列表
	@RequestMapping("/getSellerOrderList")
	@ResponseBody
	public Map<String,Object> getSellerOrderList(HttpServletRequest request, HttpServletResponse response){
		return orderService.getSellerOrderList(request,response);
	}
	
	//商家确认兑换
	@RequestMapping("/sellerConfirmExchange")
	@ResponseBody
	public Map<String,Object> sellerConfirmExchange(HttpServletRequest request, HttpServletResponse response){
		return orderService.sellerConfirmExchange(request,response);
	}
	
	//商家确认发货
	@RequestMapping("/sellerConfirmReceipt")
	@ResponseBody
	public Map<String,Object> sellerConfirmReceipt(HttpServletRequest request, HttpServletResponse response){
		return orderService.sellerConfirmReceipt(request,response);
	}
	
	//商家确认订单
	@RequestMapping("/sellerConfirmOrder")
	@ResponseBody
	public Map<String,Object> sellerConfirmOrder(HttpServletRequest request, HttpServletResponse response){
		return orderService.sellerConfirmOrder(request,response);
	}
	
	//商家退单审核
	@RequestMapping("/sellerBackOrderVerify")
	@ResponseBody
	public Map<String,Object> sellerBackOrderVerify(HttpServletRequest request, HttpServletResponse response){
		return orderService.sellerBackOrderVerify(request,response);
	}
	
	//获取商家的退单列表
	@RequestMapping("/getSellerBackOrderList")
	@ResponseBody
	public Map<String,Object> getSellerBackOrderList(HttpServletRequest request, HttpServletResponse response){
		return orderService.getSellerBackOrderList(request,response);
	}
	
	//用户申请退单
	@RequestMapping("/applyBackOrder")
	@ResponseBody
	public Map<String, Object> applyBackOrder(HttpServletRequest request, HttpServletResponse response) {
		return reBackOrderService.applyBackOrder(request);
	}
	
	//退单详情
	@RequestMapping("/getBackOrder")
	@ResponseBody
	public Map<String, Object> getBackOrder(HttpServletRequest request, HttpServletResponse response) {
		return reBackOrderService.getBackOrder(request);
	}
	
	// 粉丝退单列表
	@RequestMapping("/getBackOrderList")
	@ResponseBody
	public Map<String, Object> getBackOrderList(HttpServletRequest request, HttpServletResponse response) {
		return reBackOrderService.getBackOrderList(request);
	}
	
	//添加商品评论
	@RequestMapping("/putComment")
	@ResponseBody
	public Map<String, Object> putComment(HttpServletRequest request, HttpServletResponse response) {
		String xcx = request.getParameter("xcx");
		if(xcx != null){
			return orderCommentService.putCommentXCX(request, response);
		}else{
			return orderCommentService.putComment(request,response);
		}
	}
	
	// 粉丝确认收货
	@RequestMapping("/confirmReceipt")
	@ResponseBody
	public Map<String, Object> confirmReceipt(HttpServletRequest request, HttpServletResponse response) {
		return orderService.confirmReceipt(request);
	}
	
	// 拼团分享页面数据
	@RequestMapping("/teamShare")
	@ResponseBody
	public Map<String, Object> teamShare(HttpServletRequest request, HttpServletResponse response){
		return orderService.getTeamShare(request, response);
	}
	
	// 更新pdd订单 da
	@RequestMapping("/findIncrement")
	@ResponseBody
	public Map<String, Object> findIncrement(HttpServletRequest request, HttpServletResponse response){
		return openIncrementGoodsService.findIncrementList();
	}
}
