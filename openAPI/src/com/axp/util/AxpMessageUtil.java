package com.axp.util;


public class AxpMessageUtil {

	public final static String template_identify_code ="SMS_77255068";//验证码短信模板code
	public final static String template_order_code="SMS_107100206";//生成订单确认发货
	public final static String template_repertory_code="SMS_78750169";//库存已空
	public final static String template_account_code ="SMS_78535155";//提现到账
	public final static String template_applyaccount_code="SMS_79485040";//成功申请提现
	public final static String template_sign_code ="SMS_79520037";//签收成功
	public final static String template_checkbackorder_code="SMS_107030171";//退单审核
	public final static String template_shouyi_code="SMS_79650038";//收益到账
	public final static String template_webbuy_code="SMS_110160046";//网页购买商品付款成功
	
	
	
	/**
	 * 提供网页购买内容
	 * @param goodsName 商品名
	 * @param orderNum  订单号   
	 * @return 内容
	 */
	public static String webMessage(String orderNum){
		String contect="{\"orderNum\":\""+orderNum+"\"}";
		return contect;
		
	}
	
	/**
	 * 提供确认订单内容
	 * @param userName  用户名
	 * @param goodsName 商品名
	 * @param orderNum  订单号   
	 * @return 内容
	 */
	public static String orderApplyMessage(String orderNum){
		String contect="{\"orderNum\":\""+orderNum+"\"}";
		return contect;
		
	}
	
	
}
