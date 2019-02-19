package com.axp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.axp.service.IChangeOrderService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;

@Controller
@RequestMapping("invoke/change")
public class ChangeInvoke extends BaseController {

	@RequestMapping("/getAdver")
	@ResponseBody
	public Map<String, Object> getAdverImgs(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		
		Integer type = parameter.getData().getInteger("type")==null?90:parameter.getData().getInteger("type");
		
		return appPageService.getSlide(request,type);
	}
	
	@RequestMapping("/getGoodsType")
	@ResponseBody
	public Map<String, Object> getGoodsType(HttpServletRequest request, HttpServletResponse response) {
		
		return MallList.getGoodsType(request, response);
	}
		
	
	@RequestMapping("/getGoodsList")
	@ResponseBody
	public Map<String, Object> getGoodsList(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return MallList.getGoodsListForChange(request, response);
	}
	
	
	@RequestMapping("/getGoodsListBySeach")
	@ResponseBody
	public Map<String, Object> getGoodsListBySeach(HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = ParameterUtil.getParameter(request);
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		return MallList.getGoodsListForChange(request, response);
	}
	
	
	/**
     * 获取商品详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getGoods")
    @ResponseBody
    public Map<String, Object> getGoods(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getGoodsForChange(request, response);
    }
    
    /**
     * 换回会一级列表 (我的换货)
     */
    @RequestMapping("/myChangeList")
    @ResponseBody
    public Map<String, Object> myChangeList(HttpServletRequest request, HttpServletResponse response) {
        return MallList.myChangeList(request, response);
    }
  /*  
    *//**
     * 获取商品详情
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(HttpServletRequest request, HttpServletResponse response) {
        return MallList.updateStatus(request, response);
    }*/
    
    /**
     * 获取换货商品详情
     */
    @RequestMapping("/getGoodsDetail")
    @ResponseBody
    public Map<String, Object> getGoodsDetail(HttpServletRequest request, HttpServletResponse response) {
    			//添加访问量
    		    MallList.addPageView(request, response);
        return MallList.getGoodsMapForChange(request, response);
    }
    
    /**
     * 换货交易
     */
    @RequestMapping("/putChangeOrder")
    @ResponseBody
    public Map<String, Object> putChangeOrder(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.putChangeOrder(request, response);
    }
    
    
    /**
	 *同意换货协议 
	 */
    @RequestMapping("/agreeAgreement")
    @ResponseBody
	public  Map<String, Object>  agreeAgreement(HttpServletRequest request, HttpServletResponse response){
		 return changeOrderService.agreeAgreement(request, response);
	}
    
    /**
     * 换货协议
     */
    @RequestMapping("/changeAgreement")
    @ResponseBody
    public Map<String, Object> changeAgreement(HttpServletRequest request, HttpServletResponse response) {
    	return changeOrderService.changeAgreement(request, response);
    }
    
    
    /**
     * 修改订单状态
     */
    @RequestMapping("/updateChangeOrderStatus")
    @ResponseBody
    public Map<String, Object> updateChangeOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.updateChangeOrderStatus(request, response);
    }
    
    /**
     * 查询我发起的邀约我的订单明细
     */
    @RequestMapping("/getChangeOrderDetail")
    @ResponseBody
    public Map<String, Object> getChangeOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.getChangeOrderDetail(request, response);
    }
    
    /**
     * 确认交易或删除
     */
    @RequestMapping("/confirmChangeOrder")
    @ResponseBody
    public Map<String, Object> confirmChangeOrder(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.confirmChangeOrder(request, response);
    }
    
    /**
     * 换货发起与邀约列表
     */
    @RequestMapping("/sendOrInviteChangeList")
    @ResponseBody
    public Map<String, Object> sendOrInviteChangeList(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.sendOrInviteChangeList(request, response);
    }
    
    
    /**
     * 交易中列表
     */
    @RequestMapping("/getTradeChangeList")
    @ResponseBody
    public Map<String, Object> getTradeChangeList(HttpServletRequest request, HttpServletResponse response) {
        return changeOrderService.getTradeChangeList(request, response);
    }
    
    /**
     *我的商品列表
     */
    @RequestMapping("/getUserChangeGoodsList")
    @ResponseBody
    public Map<String, Object> getUserChangeGoodsList(HttpServletRequest request, HttpServletResponse response) {
        return MallList.getUserChangeGoodsList(request, response);
    }
    
    /*
     * 换货会首页列表
     * */
    @RequestMapping("/getChangeMallList")
    @ResponseBody
    public Map<String, Object> getChangeMallList(HttpServletRequest request,HttpServletResponse response){
    	return reGoodsOfChangeMallService.getChangeMallList(request, response);
    }
    
    /*
     * 
     * 获取热门搜索关键字
     * */
    @RequestMapping("/getChangeKeyWord")
    @ResponseBody
    public Map<String, Object> getChangeKeyWord(HttpServletRequest request,HttpServletResponse response){
    	return reGoodsOfChangeMallService.getChangeKeyWord(request, response);
    }
    
    /*
     * 换货会首页帖子列表
     * */
    @RequestMapping("/getChangeReplys")
    @ResponseBody
    public Map<String, Object> getChangeReplys(HttpServletRequest request,HttpServletResponse response){
    	return changeNoteService.getChangeReplys(request, response);
    }
    
    
    
    /*
     * 我的帖子
     * */
    @RequestMapping("/getMyChangeReplys")
    @ResponseBody
    public Map<String, Object> getMyChangeReplys(HttpServletRequest request,HttpServletResponse response){
    	return changeNoteService.getMyChangeReplys(request, response);
    }
    
    
    /*
     * 发帖子
     * */
    @RequestMapping("/sendReplys")
    @ResponseBody
    public Map<String, Object> sendReplys(HttpServletRequest request,HttpServletResponse response ){
    	return changeNoteService.sendReplys(request, response);
    }
    
    /*
     * 我的换货商品
     * */
    @RequestMapping("/getMyChangeGoods")
    @ResponseBody
    public Map<String, Object> getMyChangeGoods(HttpServletRequest request,HttpServletResponse response ){
    	return changeNoteService.getMyChangeGoods(request, response);
    }
    
    
    /*
     * 删除帖子
     * */
    @RequestMapping("/delNote")
    @ResponseBody
    public Map<String, Object> delNote(HttpServletRequest request,HttpServletResponse response){
    	return changeNoteService.delNote(request, response);
    }
    
    /*
     * 顶帖
     * */
    @RequestMapping("/noteIstop")
    @ResponseBody
    public Map<String, Object> noteIstop(HttpServletRequest request,HttpServletResponse response){
    	return changeNoteService.noteIstop(request, response);
    }
}
