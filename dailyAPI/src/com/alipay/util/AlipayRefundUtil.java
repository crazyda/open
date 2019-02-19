package com.alipay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.bean.AlipayRefund;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.alipay.sign.RSA;
import com.axp.util.DateUtil;
import com.axp.util.MD5Util;
import com.weixin.config.WeixinConfig;

/* *
 *类名：AlipayNotify
 *功能：支付宝退款接口
 *详细：处理支付宝退款业务
 *版本：3.3
 *日期：2016-09-24
 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class AlipayRefundUtil {
	
	public static String refund(String trade_no, String backCode, double fullPrice, double backPrice, String body) {
		StringBuffer result = new StringBuffer();
		try {
			BigDecimal b1 = new BigDecimal(Double.toString(fullPrice));
			BigDecimal b2 = new BigDecimal(Double.toString(backPrice));
			fullPrice =  b1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			backPrice =  b2.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			AlipayRefund refund = new AlipayRefund();
			refund.setBiz_content(body);
			refund.setTimestamp(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", DateUtil.getNow()));
//			refund.setOut_trade_no(orderCode);
			refund.setTrade_no(trade_no);
			refund.setRefund_amount(backPrice+"");
			refund.setOut_request_no(backCode);
			refund.setRefund_reason("正常退单");
			
			
			//================
			String strResponse = null;
			String alipayUrl = "https://openapi.alipay.com/gateway.do";
			AlipayClient alipayClient = new DefaultAlipayClient(alipayUrl,AlipayConfig.appid,AlipayConfig.private_key_pkcs8,"json",AlipayConfig.sign_type,AlipayConfig.ali_public_key);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent(getInfo1(refund));
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
			//================
//			
//			URL url = new URL("https://openapi.alipay.com/gateway.do");
//			URLConnection con = url.openConnection();
//			con.setDoOutput(true);
//			con.setRequestProperty("Pragma:", "no-cache");
//			con.setRequestProperty("Cache-Control", "no-cache");
//			con.setRequestProperty("Content-Type", "text/xml");
//
//			String param = sign(refund);
//			
//			OutputStreamWriter out = new OutputStreamWriter(
//					con.getOutputStream());
//			out.write(param);
//			out.flush();
//			out.close();
//			InputStreamReader in = new InputStreamReader(con.getInputStream(), "UTF-8");
//			BufferedReader br = new BufferedReader(in);
//			for (String line = br.readLine(); line != null; line = br.readLine()) {
//				result.append(line);
//			}
//			in.close();
//			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	
/*	
 *  这个方法可能是用不到的
 * 	public static String sign(AlipayRefund refund){
		// 订单
		String orderInfo = getInfo(refund);
		// 对订单做RSA 签名
		String sign = AlipaySignUtils.sign(orderInfo, AlipayConfig.private_key);

		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 完整的符合支付宝参数规范的订单信息
		return orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
	}*/
	
	public static String getInfo(Object parameter){
		Map<String,String> map = new HashMap<>();
		try{
			Field[] fields = parameter.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String key = field.getName();
				String value = field.get(parameter)==null?"":field.get(parameter).toString();
				if(!StringUtils.isEmpty(value)&&!key.equals("sign")){
					map.put(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		List<String> keyList = keyOrderByASCII(map);
		for(int i=0;i<keyList.size();i++){
			if(i==0){
				buffer.append("\""+keyList.get(i)+"\""+"="+"\""+map.get(keyList.get(i))+"\"");
			}else{
				buffer.append("&"+"\""+keyList.get(i)+"\""+"="+"\""+map.get(keyList.get(i))+"\"");
			}
		}
		return buffer.toString();
	}
	
	public static String getInfo1(Object parameter){
		Map<String,String> map = new HashMap<>();
		try{
			Field[] fields = parameter.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String key = field.getName();
				String value = field.get(parameter)==null?"":field.get(parameter).toString();
				if(!StringUtils.isEmpty(value)&&!key.equals("sign")){
					map.put(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer("{");
		List<String> keyList = keyOrderByASCII(map);
		for(int i=0;i<keyList.size();i++){
			if(i==0){
				buffer.append("\""+keyList.get(i)+"\""+":"+"\""+map.get(keyList.get(i))+"\"");
			}else{
				buffer.append(","+"\""+keyList.get(i)+"\""+":"+"\""+map.get(keyList.get(i))+"\"");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
	
	/**
	 * 将所有参数键按ASCII排序
	 * @param map
	 * @return
	 */
	public static List<String> keyOrderByASCII(Map<String,String> map){
		Collection<String> keyset= map.keySet(); 
		List<String> list=new ArrayList<String>(keyset);
		Collections.sort(list);
		return list;
	}
	
}
