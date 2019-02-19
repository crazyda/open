package com.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.axp.domain.Users;
import com.axp.util.DateUtil;
import com.axp.util.MD5Util;
import com.axp.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.weixin.bean.OrderInfo;
import com.weixin.bean.PayParameter;
import com.weixin.bean.SignInfo;
import com.weixin.config.WeixinConfig;
import com.weixin.sign.MD5;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class WeixinUtil{
	
	
	public static Map<String, String> getParams(HttpServletRequest request,HttpServletResponse respone) {
		try {
			ServletInputStream inputStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder result = new StringBuilder();
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				result.append(line);
			}
			
			
			return xmlElements(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public static String notifySign(Map<String,String> map){
		StringBuffer signSB = new StringBuffer();
		List<String> keys = WeixinUtil.keyOrderByASCII(map);
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(key.equals("sign")||key.equals("body")){
				continue;
			}
			signSB.append(key+"="+map.get(key));
			if(it.hasNext()){
				signSB.append("&");
			}
		}
		signSB.append("&key="+WeixinConfig.api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	public static String notifySignXCX(Map<String,String> map){
		StringBuffer signSB = new StringBuffer();
		List<String> keys = WeixinUtil.keyOrderByASCII(map);
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(key.equals("sign")||key.equals("body")){
				continue;
			}
			signSB.append(key+"="+map.get(key));
			if(it.hasNext()){
				signSB.append("&");
			}
		}
		signSB.append("&key="+WeixinConfig.xcx_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	public static String notifySignSeller(Map<String,String> map){
		StringBuffer signSB = new StringBuffer();
		List<String> keys = WeixinUtil.keyOrderByASCII(map);
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			if(key.equals("sign")||key.equals("body")){
				continue;
			}
			signSB.append(key+"="+map.get(key));
			if(it.hasNext()){
				signSB.append("&");
			}
		}
		signSB.append("&key="+WeixinConfig.s_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	
	/**
	 * 编辑预付信息xml
	 * @param attach
	 * @param body
	 * @param totalPrice
	 * @param orderCode
	 * @param baseIp
	 * @param notifyUrl
	 * @param nonce_str
	 * @return
	 */
	public static String setXmlStr(PayParameter parameter) {
		String signChar = getSign(parameter);
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		xml.addElement("appid").addText(WeixinConfig.appid);
		xml.addElement("attach").addCDATA(parameter.getAttach());
		xml.addElement("body").addCDATA(parameter.getBody());
		xml.addElement("mch_id").addText(WeixinConfig.mch_id);
		xml.addElement("nonce_str").addText(parameter.getNonce_str());
		xml.addElement("notify_url").addCDATA(parameter.getNotifyUrl());
		xml.addElement("out_trade_no").addCDATA(parameter.getOrderCode());
		xml.addElement("spbill_create_ip").addCDATA(parameter.getBaseIp());
		xml.addElement("total_fee").addCDATA(parameter.getTotalPrice());
		xml.addElement("trade_type").addText("APP");
		xml.addElement("sign").addText(signChar);
		String output = dom.asXML();
		return output;
	}
	
	
	/**
	 * 编辑预付信息xml
	 * @param attach
	 * @param body
	 * @param totalPrice
	 * @param orderCode
	 * @param baseIp
	 * @param notifyUrl
	 * @param nonce_str
	 * @return
	 */
	public static String setXmlStrByHtml(PayParameter parameter,String trade_type) {
		String signChar = getSignByHtml(parameter,trade_type);
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		xml.addElement("appid").addText(WeixinConfig.appid);
		xml.addElement("attach").addCDATA(parameter.getAttach());
		xml.addElement("body").addCDATA(parameter.getBody());
		xml.addElement("mch_id").addText(WeixinConfig.mch_id);
		xml.addElement("nonce_str").addText(parameter.getNonce_str());
		xml.addElement("notify_url").addCDATA(parameter.getNotifyUrl());
		xml.addElement("out_trade_no").addCDATA(parameter.getOrderCode());
		xml.addElement("spbill_create_ip").addCDATA(parameter.getBaseIp());
		xml.addElement("total_fee").addCDATA(parameter.getTotalPrice());
		xml.addElement("trade_type").addText(trade_type);
		/*if("MWEB".equals(trade_type)){
			xml.addElement("scene_info").addCDATA("{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"http://www.aixiaoping.com\",\"wap_name\":\"axp\"}}");
		}*/
		
		xml.addElement("sign").addText(signChar);
		String output = dom.asXML();
		return output;
	}
	
	
	/**
	 * 编辑预付信息xml
	 * @param attach
	 * @param body
	 * @param totalPrice
	 * @param orderCode
	 * @param baseIp
	 * @param notifyUrl
	 * @param nonce_str
	 * @return
	 */
	public static String setXmlStrByGzh(PayParameter parameter,String trade_type) {
		String signChar = getSignByGzh(parameter,trade_type);
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		xml.addElement("appid").addText(WeixinConfig.gzh_appid);
		xml.addElement("attach").addCDATA(parameter.getAttach());
		xml.addElement("body").addCDATA(parameter.getBody());
		xml.addElement("mch_id").addText(WeixinConfig.gzh_mch_id);
		xml.addElement("nonce_str").addText(parameter.getNonce_str());
		xml.addElement("notify_url").addCDATA(parameter.getNotifyUrl());
		xml.addElement("out_trade_no").addCDATA(parameter.getOrderCode());
		xml.addElement("spbill_create_ip").addCDATA(parameter.getBaseIp());
		xml.addElement("total_fee").addCDATA(parameter.getTotalPrice());
		xml.addElement("trade_type").addText(trade_type);
		/*if("MWEB".equals(trade_type)){
			xml.addElement("scene_info").addCDATA("{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"http://www.aixiaoping.com\",\"wap_name\":\"axp\"}}");
		}*/
		
		xml.addElement("sign").addText(signChar);
		String output = dom.asXML();
		return output;
	}
	
	
	
	/**
	 * 编辑预付信息xml
	 * @param attach
	 * @param body
	 * @param totalPrice
	 * @param orderCode
	 * @param baseIp
	 * @param notifyUrl
	 * @param nonce_str
	 * @return
	 */
	public static String setXmlStrBySeller(PayParameter parameter) {
		String signChar = getSignBySeller(parameter);
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		xml.addElement("appid").addText(WeixinConfig.s_appid);
		xml.addElement("attach").addCDATA(parameter.getAttach());
		xml.addElement("body").addCDATA(parameter.getBody());
		xml.addElement("mch_id").addText(WeixinConfig.s_mch_id);
		xml.addElement("nonce_str").addText(parameter.getNonce_str_seller());
		xml.addElement("notify_url").addCDATA(parameter.getNotifyUrl());
		xml.addElement("out_trade_no").addCDATA(parameter.getOrderCode());
		xml.addElement("spbill_create_ip").addCDATA(parameter.getBaseIp());
		xml.addElement("total_fee").addCDATA(parameter.getTotalPrice());
		xml.addElement("trade_type").addText("APP");
		xml.addElement("sign").addText(signChar);
		String output = dom.asXML();
		return output;
	}
	
	
	public static String setCloseXmlStr(PayParameter parameter) {
		
		String sign = getCloseSign(parameter);
		
		StringBuffer sbf = new StringBuffer();
		sbf.append("<xml>\n");
		sbf.append("<appid>"+WeixinConfig.appid+"</appid>\n");
		sbf.append("<mch_id>"+WeixinConfig.mch_id+"</mch_id>\n");
		sbf.append("<nonce_str>"+parameter.getNonce_str()+"</nonce_str>\n");//随机字符串，不大于32位
		sbf.append("<out_trade_no>"+parameter.getOrderCode()+"</out_trade_no>\n");//商户内部订单号，32位内
		sbf.append("<sign>"+sign+"</sign>\n");
		sbf.append("</xml>");
		
		return sbf.toString();
	}
	
	public static String getCloseSign(PayParameter parameter){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.appid);
		signSB.append("&mch_id="+WeixinConfig.mch_id);
		signSB.append("&nonce_str="+getNonceStr(parameter.getAttach(),parameter.getBody()));
		signSB.append("&out_trade_no="+parameter.getOrderCode());
		signSB.append("&key="+WeixinConfig.api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getPaySign(PayParameter parameter, String prepayId,long timeStamp){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.appid);
		signSB.append("&noncestr="+parameter.getNonce_str());
		signSB.append("&package=Sign=WXPay");
		signSB.append("&partnerid="+WeixinConfig.mch_id);
		signSB.append("&prepayid="+prepayId);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	public static String getPaySignXCX(PayParameter parameter, String prepayId,long timeStamp){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.xcx_appid);
		signSB.append("&noncestr="+parameter.getNonce_str());
		signSB.append("&package=Sign=WXPay");
		signSB.append("&partnerid="+WeixinConfig.xcx_mch_id);
		signSB.append("&prepayid="+prepayId);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.xcx_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getPaySignGzh(PayParameter parameter, String prepayId,long timeStamp){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.gzh_appid);
		signSB.append("&noncestr="+parameter.getNonce_str());
		signSB.append("&package=Sign=WXPay");
		signSB.append("&partnerid="+WeixinConfig.gzh_mch_id);
		signSB.append("&prepayid="+prepayId);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.gzh_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getPaySignBySeller(PayParameter parameter, String prepayId,long timeStamp){
		
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.s_appid);
		signSB.append("&noncestr="+parameter.getNonce_str_seller());
		signSB.append("&package=Sign=WXPay");
		signSB.append("&partnerid="+WeixinConfig.s_mch_id);
		signSB.append("&prepayid="+prepayId);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.s_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	
	public static String getPaySignNoAppId(PayParameter parameter, String prepayId,long timeStamp){
		StringBuffer signSB = new StringBuffer();
		signSB.append("noncestr="+parameter.getNonce_str());
		signSB.append("&package=Sign=WXPay");
		signSB.append("&partnerid="+WeixinConfig.mch_id);
		signSB.append("&prepayid="+prepayId);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getSign(PayParameter parameter){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.appid);
		signSB.append("&attach="+parameter.getAttach());
		signSB.append("&body="+parameter.getBody());
		signSB.append("&mch_id="+WeixinConfig.mch_id);
		signSB.append("&nonce_str="+getNonceStr(parameter.getAttach(),parameter.getBody()));
		signSB.append("&notify_url="+parameter.getNotifyUrl());
		signSB.append("&out_trade_no="+parameter.getOrderCode());
		signSB.append("&spbill_create_ip="+parameter.getBaseIp());
		signSB.append("&total_fee="+parameter.getTotalPrice());
		signSB.append("&trade_type="+"APP");
		signSB.append("&key="+WeixinConfig.api_key);
		
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getSignByHtml(PayParameter parameter,String trade_type){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.appid);
		signSB.append("&attach="+parameter.getAttach());
		signSB.append("&body="+parameter.getBody());
		signSB.append("&mch_id="+WeixinConfig.mch_id);
		signSB.append("&nonce_str="+getNonceStr(parameter.getAttach(),parameter.getBody()));
		signSB.append("&notify_url="+parameter.getNotifyUrl());
		signSB.append("&out_trade_no="+parameter.getOrderCode());
		signSB.append("&spbill_create_ip="+parameter.getBaseIp());
		signSB.append("&total_fee="+parameter.getTotalPrice());
		signSB.append("&trade_type="+trade_type);
		
		signSB.append("&key="+WeixinConfig.api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getSignByGzh(PayParameter parameter,String trade_type){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.gzh_appid);
		signSB.append("&attach="+parameter.getAttach());
		signSB.append("&body="+parameter.getBody());
		signSB.append("&mch_id="+WeixinConfig.gzh_mch_id);
		signSB.append("&nonce_str="+getNonceStr(parameter.getAttach(),parameter.getBody()));
		signSB.append("&notify_url="+parameter.getNotifyUrl());
		signSB.append("&out_trade_no="+parameter.getOrderCode());
		signSB.append("&spbill_create_ip="+parameter.getBaseIp());
		signSB.append("&total_fee="+parameter.getTotalPrice());
		signSB.append("&trade_type="+trade_type);
		signSB.append("&key="+WeixinConfig.gzh_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	public static String getSignBySeller(PayParameter parameter){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.s_appid);
		signSB.append("&attach="+parameter.getAttach());
		signSB.append("&body="+parameter.getBody());
		signSB.append("&mch_id="+WeixinConfig.s_mch_id);
		signSB.append("&nonce_str="+getNonceStrBySeller(parameter.getAttach(),parameter.getBody()));
		signSB.append("&notify_url="+parameter.getNotifyUrl());
		signSB.append("&out_trade_no="+parameter.getOrderCode());
		signSB.append("&spbill_create_ip="+parameter.getBaseIp());
		signSB.append("&total_fee="+parameter.getTotalPrice());
		signSB.append("&trade_type="+"APP");
		signSB.append("&key="+WeixinConfig.s_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	/**
	 * 发送预付信息
	 * @param urlStr 微信API地址
	 * @param xml 预付信息Xml
	 * @return
	 */
	public static String sendXml(String urlStr, String xml) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			out.write(new String(xml.getBytes("UTF-8")));
			out.flush();
			out.close();
			InputStreamReader in = new InputStreamReader(con.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(in);
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				result.append(line);
			}
			in.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	/**
	 * 获得预付订单id
	 * @param attach 附加信息
	 * @param body 主体信息
	 * @param totalPrice 总价
	 * @param orderCode 内部订单序号
	 * @param baseIp 本地服务ip
	 * @param basePath 本地服务根目录
	 * @return
	 */
	public static String getPrepayId(PayParameter parameter){
		String prepayId = "";
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_VERIFY_URL;
		String xml = setXmlStr(parameter);
		System.out.println("xml:"+xml);
		
		try{
		String  newnml = new String (xml.getBytes(),"ISO8859-1");
	
		String responseResult = sendXml(urlStr,newnml);
		
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		Map<String, String> responseMap = xmlElements(responseResult);
		
		if("SUCCESS".equals(responseMap.get("result_code"))){
			String nonceStr = responseMap.get("nonce_str");
			prepayId = responseMap.get("prepay_id");
		}
		System.out.println("responseMap"+responseMap.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		return prepayId;
	}
	
	/**
	 * 获得预付订单id
	 * @param attach 附加信息
	 * @param body 主体信息
	 * @param totalPrice 总价
	 * @param orderCode 内部订单序号
	 * @param baseIp 本地服务ip
	 * @param basePath 本地服务根目录
	 * @return
	 */
	public static String[] getPrepayIdByHtml(PayParameter parameter,String trade_type){
		String [] prepayId = new String [4];
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_VERIFY_URL;
		String xml = setXmlStrByHtml(parameter,trade_type);
		try{
		String  newnml = new String (xml.getBytes(),"ISO8859-1");
		String responseResult = sendXml(urlStr,newnml);
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		Map<String, String> responseMap = xmlElements(responseResult);
		
		if("SUCCESS".equals(responseMap.get("result_code"))){
			prepayId[0] = responseMap.get("nonce_str");
			prepayId[1] = responseMap.get("prepay_id");
			prepayId[2] = responseMap.get("code_url")==null?"":responseMap.get("code_url");
			prepayId[3] = responseMap.get("mweb_url")==null?"":responseMap.get("mweb_url");
		}

		}catch (Exception e){
			e.printStackTrace();
		}
		return prepayId;
	}
	
	/**
	 * 获得预付订单id
	 * @param attach 附加信息
	 * @param body 主体信息
	 * @param totalPrice 总价
	 * @param orderCode 内部订单序号
	 * @param baseIp 本地服务ip
	 * @param basePath 本地服务根目录
	 * @return
	 */
	public static String[] getPrepayIdByGzh(PayParameter parameter,String trade_type){
		String [] prepayId = new String [4];
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_VERIFY_URL;
		String xml = setXmlStrByGzh(parameter,trade_type);
		try{
		String  newnml = new String (xml.getBytes(),"ISO8859-1");
		String responseResult = sendXml(urlStr,newnml);
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		Map<String, String> responseMap = xmlElements(responseResult);
		
		if("SUCCESS".equals(responseMap.get("result_code"))){
			prepayId[0] = responseMap.get("nonce_str");
			prepayId[1] = responseMap.get("prepay_id");
			prepayId[2] = responseMap.get("code_url")==null?"":responseMap.get("code_url");
			prepayId[3] = responseMap.get("mweb_url")==null?"":responseMap.get("mweb_url");
		}

		}catch (Exception e){
			e.printStackTrace();
		}
		return prepayId;
	}
	
	
	
	
	
	/**
	 * 获得预付订单id
	 * @param attach 附加信息
	 * @param body 主体信息
	 * @param totalPrice 总价
	 * @param orderCode 内部订单序号
	 * @param baseIp 本地服务ip
	 * @param basePath 本地服务根目录
	 * @return
	 */
	public static String getPrepayIdBySeller(PayParameter parameter){
		String prepayId = "";
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_VERIFY_URL;
		String xml = setXmlStrBySeller(parameter);
		try{
		String  newnml = new String (xml.getBytes(),"ISO8859-1");
	
		String responseResult = sendXml(urlStr,newnml);
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		
		Map<String, String> responseMap = xmlElements(responseResult);
		if("SUCCESS".equals(responseMap.get("result_code"))){
			String nonceStr = responseMap.get("nonce_str");
			prepayId = responseMap.get("prepay_id");
		}

		}catch (Exception e){
			e.printStackTrace();
		}
		return prepayId;
	}
	
	
	/**
	 * 关闭订单
	 * @param parameter
	 * @return
	 */
	public static String closeOrder(PayParameter parameter){
		String prepayId = "";
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_CLOSE_URL;
		String xml = setCloseXmlStr(parameter);
		String responseResult = sendXml(urlStr,xml);
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		Map<String, String> responseMap = xmlElements(responseResult);
		if(responseMap.get("return_code").equals("SUCCESS")){
			prepayId = responseMap.get("prepay_id");
		}
		return prepayId;
	}
	
	public static String getNonceStr(String attach,String body){
		return MD5.getNonceStr("attach="+attach+"&body="+body+"&device_info="+WeixinConfig.device_info+"&mch_id="+WeixinConfig.mch_id+"&key="+WeixinConfig.api_key);
	}
	
	public static String getNonceStrBySeller(String attach,String body){
		return MD5.getNonceStr("attach="+attach+"&body="+body+"&device_info="+WeixinConfig.device_info+"&mch_id="+WeixinConfig.s_mch_id+"&key="+WeixinConfig.s_api_key);
	}
	
	
	/**
	 * 解析服务器返回的数据
	 * @param xmlDoc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlElements(String xmlDoc) {
		Map<String, String> map = new HashMap<>();
		try {
			Document document = DocumentHelper.parseText(xmlDoc);
			Element nodeElement = document.getRootElement();
			List<Element> node = nodeElement.elements();
			for (Iterator<Element> it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				map.put(elm.getName(), elm.getText());
				elm = null;
			
			}
			node = null;
			nodeElement = null;
			document = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String getNonceStr(Object parameter){
		Map<String,String> map = new HashMap<>();
		try{
			Field[] fields = parameter.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String key = field.getName();
				String value = field.get(parameter)==null?"":field.get(parameter).toString();
				if(StringUtils.isNotEmpty(value)&&!key.equals("sign")){
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
				buffer.append(keyList.get(i)+"="+map.get(keyList.get(i)));
			}else{
				buffer.append("&"+keyList.get(i)+"="+map.get(keyList.get(i)));
			}
		}
		return MD5.getNonceStr(buffer.toString());
	}
	
	public static String getParameterSign(Object parameter){
		Map<String,String> map = new HashMap<>();
		try{
			Field[] fields = parameter.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String key = field.getName();
				String value = field.get(parameter)==null?"":field.get(parameter).toString();
				if(StringUtils.isNotEmpty(value)&&!key.equals("sign")){
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
				buffer.append(keyList.get(i)+"="+map.get(keyList.get(i)));
			}else{
				buffer.append("&"+keyList.get(i)+"="+map.get(keyList.get(i)));
			}
		}
		buffer.append("&key=" + WeixinConfig.api_key);
		return MD5Util.GetMD5Code(buffer.toString()).toUpperCase();
	}
	
	public static String getXmlStr(Object parameter) {
		Map<String,String> map = new HashMap<>();
		try{
			Field[] fields = parameter.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String key = field.getName();
				String value = field.get(parameter)==null?"":field.get(parameter).toString();
				if(StringUtils.isNotEmpty(value)){
					map.put(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		List<String> keyList = keyOrderByASCII(map);
		for(int i=0;i<keyList.size();i++){
			xml.addElement(keyList.get(i)).addCDATA(map.get(keyList.get(i)));
		}
		return dom.asXML();
	}
	
	/**
	 * 发送预付信息（需要双向证书）
	 * @param urlStr 微信API地址
	 * @param xml 预付信息Xml
	 * @return
	 */
	public static String sendXmlWithSSL(String urlStr, String xml) {
		StringBuffer result = new StringBuffer();

		try{
			KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		    FileInputStream instream = new FileInputStream(new File(WeixinConfig.SSLPath));
		    try {
	            keyStore.load(instream, WeixinConfig.mch_id.toCharArray());
	        } finally {
	            instream.close();
	        }
	        // Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom()
	                .loadKeyMaterial(keyStore, WeixinConfig.mch_id.toCharArray())
	                .build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        CloseableHttpClient httpclient = HttpClients.custom()
	                .setSSLSocketFactory(sslsf)
	                .build();
		    
			try {
				
				HttpPost httpost = new HttpPost(urlStr); // 设置响应头信息  
				httpost.addHeader("Connection", "keep-alive");  
	            httpost.addHeader("Accept", "*/*");  
	            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
	            httpost.addHeader("Host", "api.mch.weixin.qq.com");  
	            httpost.addHeader("X-Requested-With", "XMLHttpRequest");  
	            httpost.addHeader("Cache-Control", "max-age=0");  
	            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
	            httpost.setEntity(new StringEntity(xml, "UTF-8"));  
	            CloseableHttpResponse response = httpclient.execute(httpost);  
				
	            try {
		            HttpEntity entity = response.getEntity();
		            if (entity != null) {
		            	InputStreamReader in = new InputStreamReader(entity.getContent(), "UTF-8");
		    			BufferedReader br = new BufferedReader(in);
		    			for (String line = br.readLine(); line != null; line = br.readLine()) {
		    				result.append(line);
		    			}
		            }
		            EntityUtils.consume(entity);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }finally{
	            	response.close();
	            }
			} catch (Exception e) {
				e.printStackTrace();
			} finally { 
				httpclient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	
	//WEB支付签名
	public static String  getWebSign(Integer userId,Integer type,String orderIds,String  timeStamp){
		StringBuffer signSB = new StringBuffer();
		signSB.append("&package=Sign=WEBPAY");
		signSB.append("&userId="+userId);
		signSB.append("&type="+type);
		signSB.append("&orderIds="+orderIds);
		signSB.append("&timestamp="+timeStamp);
		signSB.append("&key="+WeixinConfig.gzh_api_key);
		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
	}
	
	//验证签名
	public static Map<String, Object> checkSgin(String webSign,String originSign,String timeStamp ){
		
		 Map<String, Object>  statusMap=new HashMap<String, Object>();
		 statusMap.put("status", 1);
		 
		 Date startTime=new Date(Long.parseLong(timeStamp));
			Long diffSeconds = DateUtil.getDiffSeconds(startTime, new Date());
		if(diffSeconds>((60*1000))*5){
				statusMap.put("status", -2);
				statusMap.put("message", "签名已失效");
		
		}else if (!webSign.equals(originSign)){
			statusMap.put("status", -3);
			statusMap.put("message", "签名验证失败");
		
		}
		return statusMap;
	
	}
	public static Map<String,Object> pay(PayParameter parameter,Users user ,String out_trade_no,Double total_fee){
		return weixinApp( parameter, user , out_trade_no, total_fee);
	}
	public static Map<String,Object> weixinApp(PayParameter parameter,Users user ,String out_trade_no,Double total_fee){
		return null;
		
	}
	public Map<String,Object> unifiedorder(PayParameter parameter,Users user ,String out_trade_no,Double total_fee){
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		List<Map<String,String>> parameters = new ArrayList<Map<String,String>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appid", WeixinConfig.xcx_appid);
		map.put("mch_id", WeixinConfig.xcx_mch_id);
		map.put("nonce_str", "");
		map.put("body", parameter.getBody());
		map.put("out_trade_no", out_trade_no);
		map.put("total_fee", total_fee);
		map.put("spbill_create_ip", "");
		map.put("notify_url", parameter.getNotifyUrl());
		map.put("openid", user.getOpenId());
		map.put("trade_type", "JSAPI");
		///PayParameter payParameter = returnParameter(request, notify, orderjson, orderCode, totalPrice+"");
		return map;
	}
	
	/**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
            	String name = f.getName();
            	XStreamAlias anno = f.getAnnotation(XStreamAlias.class);
            	if(anno != null)
            		name = anno.value();
                list.add(name + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WeixinConfig.xcx_api_key;
        result = MD5Util.MD5Encode(result).toUpperCase();
        return result;
    }
    
    public static String getSign(SignInfo signInfo){
    	
    	StringBuffer signSB = new StringBuffer();
        signSB.append("appId="+signInfo.getAppId());
        signSB.append("&nonceStr="+signInfo.getNonceStr());
        signSB.append("&package="+signInfo.getRepay_id());
  		signSB.append("&signType="+signInfo.getSignType());
  		signSB.append("&timeStamp="+signInfo.getTimeStamp());
  		signSB.append("&key="+WeixinConfig.xcx_api_key);
  		return MD5Util.GetMD5Code(signSB.toString()).toUpperCase();
    	
    }
    
    
    //传输超时时间，默认30秒	//连接超时时间，默认10秒
    private static final int socketTimeout = 10000;
 
    //传输超时时间，默认30秒
    private static final int connectTimeout = 30000;
	
	/**
	 * post请求
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	public static String sendPost(String url, Object xmlObj) throws ClientProtocolException, IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		
        
		HttpPost httpPost = new HttpPost(url);
		//解决XStream对出现双下划线的bug   
//		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        XStream xStreamForRequestPostData = new XStream(new DomDriver());
        xStreamForRequestPostData.alias("xml", xmlObj.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
 
        //设置请求器的配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
	}

	
	/**
	 * 获得预付订单id
	 * @param attach 附加信息
	 * @param body 主体信息
	 * @param totalPrice 总价
	 * @param orderCode 内部订单序号
	 * @param baseIp 本地服务ip
	 * @param basePath 本地服务根目录
	 * @return
	 */
	public static String getPrepayId(OrderInfo order){
		String prepayId = "";
		// 回调接口url
		String urlStr = WeixinConfig.HTTPS_VERIFY_URL;
		String xml = setXmlStr(order);
		try{
		String  newnml = new String (xml.getBytes(),"ISO8859-1");
	
		String responseResult = sendXml(urlStr,newnml);
		
		if(!StringUtil.hasLength(responseResult)){
			return prepayId;
		}
		Map<String, String> responseMap = xmlElements(responseResult);
		
		if("SUCCESS".equals(responseMap.get("result_code"))){
			String nonceStr = responseMap.get("nonce_str");
			prepayId = responseMap.get("prepay_id");
		}
		}catch (Exception e){
			e.printStackTrace();
		}
		return prepayId;
	}

	/**
	 * 编辑预付信息xml
	 * @param attach
	 * @param body
	 * @param totalPrice
	 * @param orderCode
	 * @param baseIp
	 * @param notifyUrl
	 * @param nonce_str
	 * @return
	 */
	public static String setXmlStr(OrderInfo order) {
		System.out.println("进入setXmlStr");
		Document dom = DocumentHelper.createDocument();//创建xml文件
		Element xml = dom.addElement("xml");
		xml.addElement("appid").addText(WeixinConfig.xcx_appid);
		xml.addElement("body").addCDATA(order.getBody());
		xml.addElement("mch_id").addText(WeixinConfig.xcx_mch_id);
		xml.addElement("nonce_str").addText(order.getNonce_str());
		xml.addElement("notify_url").addCDATA(order.getNotify_url());
		xml.addElement("openid").addCDATA(order.getOpenid());
		xml.addElement("out_trade_no").addCDATA(order.getOut_trade_no());
		xml.addElement("sign_type").addCDATA(order.getSign_type());
		xml.addElement("spbill_create_ip").addCDATA(order.getSpbill_create_ip());
		xml.addElement("total_fee").addCDATA(String.valueOf(order.getTotal_fee()));
		xml.addElement("trade_type").addText("JSAPI");
		xml.addElement("sign").addText(order.getSign());
		String output = dom.asXML();
		return output;
	}
	public static String getSign(OrderInfo order){
		StringBuffer signSB = new StringBuffer();
		signSB.append("appid="+WeixinConfig.xcx_appid);
		signSB.append("&body="+order.getBody());
		signSB.append("&mch_id="+WeixinConfig.xcx_mch_id);
		signSB.append("&nonce_str="+order.getNonce_str());
		signSB.append("&notify_url="+order.getNotify_url());
		signSB.append("&openid="+order.getOpenid());
		signSB.append("&out_trade_no="+order.getOut_trade_no());
		signSB.append("&sign_type="+order.getSign_type());
		signSB.append("&spbill_create_ip="+order.getSpbill_create_ip());
		signSB.append("&total_fee="+order.getTotal_fee());
		signSB.append("&trade_type="+"JSAPI");
		signSB.append("&key="+WeixinConfig.xcx_api_key);
		return MD5Util.MD5Encode(signSB.toString()).toUpperCase();
	}
	
	
	
	/** 
     *  
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br> 
     * 实现步骤: <br> 
     *  
     * @param paraMap   要排序的Map对象 
     * @param urlEncode   是否需要URLENCODE 
     * @param keyToLower    是否需要将Key转换为全小写 
     *            true:key转化成小写，false:不转化 
     * @return 
     */  
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower){  
        String buff = "";  
        Map<String, String> tmpMap = paraMap;  
        try  
        {  
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());  
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）  
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()  
            {  
                @Override  
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)  
                {  
                    return (o1.getKey()).toString().compareTo(o2.getKey());  
                }  
            });  
            // 构造URL 键值对的格式  
            StringBuilder buf = new StringBuilder();  
            for (Map.Entry<String, String> item : infoIds)  
            {  
                if (StringUtils.isNotBlank(item.getKey()))  
                {  
                    String key = item.getKey();  
                    String val = item.getValue();  
                    if (urlEncode)  
                    {  
                        val = URLEncoder.encode(val, "utf-8");  
                    }  
                    if (keyToLower)  
                    {  
                        buf.append(key.toLowerCase() + "=" + val);  
                    } else  
                    {  
                        buf.append(key + "=" + val);  
                    }  
                    buf.append("&");  
                }  
   
            }  
            buff = buf.toString();  
            if (buff.isEmpty() == false)  
            {  
                buff = buff.substring(0, buff.length() - 1);  
            }  
        } catch (Exception e)  
        {  
           return null;  
        }  
        return buff;  
    }

	/**
	 * 获取小程序二维码
	 * @param access_token
	 * @param request
	 * @return
	 */
	public static String getminiqrQr(String access_token,HttpServletRequest request,Users user) {
		String path = "pages/index/index";
		String twoCodeUrl=user.getMycode()+".png";
//		String codeUrl = "E:/webapps/dailyAPI/xcx/images/"+user.getOpenId();
    	String codeUrl = "E:/xampp/tomcat/webapps/dailyAPI/xcx/image/"+user.getOpenId();
    	 try
         {
             URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+access_token);
             HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
             httpURLConnection.setRequestMethod("POST");// 提交模式
             // conn.setConnectTimeout(10000);//连接超时 单位毫秒
             // conn.setReadTimeout(2000);//读取超时 单位毫秒
             // 发送POST请求必须设置如下两行
             httpURLConnection.setDoOutput(true);
             httpURLConnection.setDoInput(true);
             // 获取URLConnection对象对应的输出流
             PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
             // 发送请求参数
             JSONObject paramJson = new JSONObject();
             paramJson.put("scene", user.getMycode());
             paramJson.put("path", path);
             paramJson.put("width", 430);
             paramJson.put("is_hyaline", true);
             printWriter.write(paramJson.toString());
             // flush输出流的缓冲
             printWriter.flush();
             
             BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
             File file = new File(codeUrl);
             if (!file.exists()) {
            	 file.mkdirs();
     		}
             FileOutputStream os = new FileOutputStream(file+"/"+twoCodeUrl);
             int len;
             byte[] arr = new byte[1024];
             while ((len = bis.read(arr)) != -1)
             {
                 os.write(arr, 0, len);
                 os.flush();
             }
             os.close();
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
		return twoCodeUrl;
    }
	
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    } 
	
	
}