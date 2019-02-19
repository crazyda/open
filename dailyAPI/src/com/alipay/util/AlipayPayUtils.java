package com.alipay.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.config.AlipayConfig;





/**
 * 支付宝支付工具
 * @author xu
 *
 */
public class AlipayPayUtils {
	
	public static Map<String,String> getParams(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		//获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		
		params.put("out_trade_no", out_trade_no);
		params.put("trade_no", trade_no);
		params.put("trade_status", trade_status);
//		System.out.println("params"+params);
		
		
		return params;
	}
	
	
	public static Map<String,String> getParams2 (HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		//获取支付宝POST过来反馈信息
				Map<String, String> params = new HashMap<String, String>();
				Map<String, String[]> requestParams = request.getParameterMap();
				for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
					String name = iter.next();
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
					//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
					params.put(name, valueStr);
				}
				//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
				//商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
				//支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				
				params.put("out_trade_no", out_trade_no);
				params.put("trade_no", trade_no);
				params.put("trade_status", trade_status);
//				System.out.println("params"+params);
				
		return params;
	}
	
	
	
	
	
	
	
	
	/**
	 * 支付
	 * @param activity
	 * @param subject  商品名称
	 * @param orderId  订单
	 * @param price     支付的金额
	 * @param callbackUrl     支付成功后支付宝回调的接口
	 * @param listener 支付完成后调用的事件
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public static String getOrder(String goodsName ,double goodsPrice, String body, String notifyUrl){
		String sign = "";
		sign = AlipaySignUtils.sign(goodsName , body, goodsPrice+"", notifyUrl);
		return sign;
	}


	
	
	
	/**创建支付宝订单信息*/
	public static String getOrderInfo(String subject, String body, String price, String callbackUrl) {
		
		// 商品详情1
		String orderInfo = "body=" + "\"" + body + "\"";
		// 参数编码， 固定值
				orderInfo += "&input_charset=\"utf-8\"";
		// 设置未付款交易的超时时间2
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + callbackUrl +"\"";
		// 商户网站唯一订单号3
		orderInfo += "&out_trade_no=" + "\"" + AlipaySignUtils.getOutTradeNo() + "\"";
		// 签约合作者身份ID4
		orderInfo += "&partner=" + "\"" + AlipayConfig.partner + "\"";

		// 支付类型， 固定值5
		orderInfo += "&payment_type=\"1\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		// 签约卖家支付宝账号6
		orderInfo += "&seller_id=" + "\"" + AlipayConfig.seller_email + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";


		// 商品名称7
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";


		


		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		//orderInfo += "&userId=" + "\""+
		
		return orderInfo;
	}
	
	

}
