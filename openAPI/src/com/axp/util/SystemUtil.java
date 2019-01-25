package com.axp.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SystemUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getCacheMap(HttpServletRequest request,String cacheName){
		ServletContext servlet = request.getServletContext();
		Map<String,Object> map = null;
		if(servlet.getAttribute(cacheName) == null){
			map = new HashMap<String,Object>();
			servlet.setAttribute(cacheName, map );
		}else{
			map = (Map<String, Object>) servlet.getAttribute(cacheName);
		}
		return map;
	}
	
	
	public static void setCache(HttpServletRequest request,String cacheName,String key,Object value){
		Map<String,Object> map = getCacheMap(request, cacheName);
		map.put(key, value);
	}
	
	public static Object getCache(HttpServletRequest request,String cacheName,String key){
		Map<String,Object> map = getCacheMap(request, cacheName);
		return map.get(key);
	}
	
	public static void removeCache(HttpServletRequest request,String cacheName,String key){
		Map<String,Object> map = getCacheMap(request, cacheName);
		map.remove(key);
	}
	
}
