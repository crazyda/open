package com.axp.util;


public class AxpMessageUtil {

	public final static String template_identify_code ="SMS_145290736";//验证码短信模板code  da
	public final static String template_order_code="SMS_145295664";//生成订单确认发货
	public final static String template_repertory_code="SMS_145295669";//库存已空
	public final static String template_account_code ="SMS_145295674";//提现到账
	public final static String template_applyaccount_code="SMS_145290741";//成功申请提现--
	public final static String template_sign_code ="SMS_145295683";//签收成功
	public final static String template_checkbackorder_code="SMS_145290745";//退单审核
	public final static String template_shouyi_code="SMS_145295685";//收益到账
	public final static String template_webbuy_code="SMS_145290786";//网页购买商品付款成功
	
	
	
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
