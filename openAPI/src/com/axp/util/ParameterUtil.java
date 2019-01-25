package com.axp.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class ParameterUtil {

	
	/**
	 * 检查所给的参数中是否有空值；
	 * 规则：
	 * 1，若是字符串要求hasLength()；
	 * 2，若是其他类型，不能为null；
	 * @param param 给定的不定长参数；
	 * @return
	 */
	public static Boolean EmptyCheck(Object... param) {
		Boolean flag = true;
		for (Object each : param) {
			if (each instanceof String) {
				flag = StringUtil.hasLength((String) each);
			} else {
				flag = (each != null);
			}
			if (!flag) {
				return flag;
			}
		}
		return flag;
	}

	/**
	 * 获取通用的基础参数对象；
	 *  "userId": "1877",
	 *  "lng": "123541.1213",
	 *  "lat": "125465.5465",
	 *  "channelId": "123",
	 *  "os": "ANDROID",
	 *  "appVersion": "1",
	 *  "dip": "320",
	 *  "zoneId": "1960",
	 *  "machineCode": "465415we16416we13r",
	 *  "data": {
	 *  	 "name": "13827364839",
	 *  	 "pwd": "888888",
	 *  	 "tokenId": "asd567a7s765a7s8a",
	 *       "openId": "asfg89102gsdfws02"
	 *  }
	 *  规则：
	 *  1，获取参数放到Map集合中；
	 *  2，若是空，空字符串，全是空格的字符串，则为null；
	 *  3，data不解析内部，只作为jsonObject；
	 *  4，通过map获取一个Parameter对象，如果返回值是null，则表示参数有错误，需要在使用这个返回的Parameter对象时，要先做是否为null的判断；
	 *  
	 * @param request
	 * @return
	 */
	public static Parameter getParameter(HttpServletRequest request) {
		//先将参数提取出来，如果出错，则返回空的Parameter对象；
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			request.setCharacterEncoding("UTF-8");
		//检查参数；
		String data =  request.getParameter("data");
		if (request == null || StringUtil.isEmpty(data)) {
			return null;
		}
		//获取请求中的参数值；
		JSONObject parseObject = JSONObject.parseObject(data);
		map.put("type", getParameter(parseObject.get("type")));
		map.put("app_id", getParameter(parseObject.get("app_id")));
		map.put("client_id",getParameter(parseObject.get("client_id")));
		map.put("client_secret", getParameter(parseObject.get("client_secret")));
		map.put("sign", getParameter(parseObject.get("sign")));
		map.put("data_type", getParameter(parseObject.get("data_type")));
		map.put("timestamp", getParameter(parseObject.get("timestamp")));
		map.put("data", getParameter(parseObject.getJSONObject("data")));
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return new Parameter(map);//使用map作为参数，传递给Parameter的构造函数，对Parameter中的字段进行初始化；
	}

	/**
	 * 返回对象
	 * 1，若是空字符串或者全是由空格组成的字符串，返回null；
	 * 2，其他对象，原样返回；
	 */
	public static <T> T getParameter(T t) {
		if (t == null) {
			return null;
		}
		if (t instanceof String) {
			if (StringUtil.hasLength((String) t)) {
				return t;
			}
			return null;
		}
		if (t instanceof BigDecimal) {
			return (T)t.toString();
		}
		return t;
	}

}
