package com.axp.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {

	/**
	 * 订单编码
	 * @param orderId
	 * @return
	 */
	public static String getOrderCode(Integer orderId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());
		DecimalFormat f = new DecimalFormat("00000000");
		StringBuffer sb = new StringBuffer(format);
		sb.append(f.format(orderId));
		return sb.toString();		
	}
	
	
	/**
	 * 获取随机码
	 * @return
	 */
	public static String getCode(){
		int max = 999999;
	    int min = 100000;
	    Random random = new Random();
	    return Integer.toString(random.nextInt(max)%(max-min+1) + min);
	}
	/**
	 * 退单编号
	 * @param orderId
	 * @return
	 */
	public static String getBlackOrderCode(Integer orderId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(new Date());
		DecimalFormat f = new DecimalFormat("00000000");
		StringBuffer sb = new StringBuffer(format);
		sb.append(f.format(orderId));
		return sb.toString();		
	}
	
		//充值接口类型
	/*public static final String WALLET = "100";//钱包
	public static final String ALIPAY = "200";//支付宝
	public static final String WEIXIN = "300";//微信
	public static final String YILIANSCORE = "400";//易联积分
*/	
	public static Integer getPayTypeNum(String payType){
  		if(payType.equals("ALIPAY")){
  			return 200;
  		}else if(payType.equals("WEIXIN")){
  			return 300;
  		}else if(payType.equals("WALLET")){
  			return 100;
  		}else if(payType.equals("YILIAN")){
  			return 400;
  		}
		return -1;
  	}
}
