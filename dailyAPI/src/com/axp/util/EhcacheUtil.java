package com.axp.util;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {
	
	
	final static String deafultCacheName="axpCache";
	
	 public static CacheManager manager = CacheManager.create();  
	  
	    public static Object get(Object key) {  
	        Cache cache = manager.getCache(deafultCacheName);  
	        if (cache != null) {  
	            Element element = cache.get(key);  
	            if (element != null) {  
	                return element.getObjectValue();  
	            }  
	        }  
	        return null;  
	    }  
	  
	    public static void put( Object key, Object value) {  
	        Cache cache = manager.getCache(deafultCacheName);  
	        if (cache != null) {  
	            cache.put(new Element(key, value));  
	        }  
	    }  
	  
	    public static boolean remove( Object key) {  
	        Cache cache = manager.getCache(deafultCacheName);  
	        if (cache != null) {  
	            return cache.remove(key);  
	        }  
	        return false;  
	    }  
	  
	    
	    public static boolean containsKey(Object key ){
	    	 Cache cache = manager.getCache(deafultCacheName);  
	    	 Element element = cache.get(key);  
	            if (element != null) {  
	                return true;
	            }  
		        return false; 
	    }
}
