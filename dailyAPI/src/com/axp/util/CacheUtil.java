package com.axp.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.axp.domain.ProvinceEnum;

public class CacheUtil {

	private CacheUtil (){
		
	}
	
	private static Map<String,List<ProvinceEnum>> zoneAll  =new HashMap<String, List<ProvinceEnum>>();
	
	private static Map<String, Object> map=new HashMap<String, Object>();
	
	public static Map<String,List<ProvinceEnum>> getZoneAll(){
		
		return zoneAll;
		
	}
	
	public static boolean containsKey(String name){
		return map.containsKey(name);
	}
	
	public static Object getCacheByName(String name){
		
		if(map.containsKey(name)){
			return map.get(name);
		}else{
			return null;
		}
	}
	
	public static void setCache(String name,Object obj){
		map.put(name, obj);
	}
	
}
