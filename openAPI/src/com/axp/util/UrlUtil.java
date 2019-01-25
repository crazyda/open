package com.axp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UrlUtil {


	
	
	
	
	
	
	public static List<Map<String, Object>> jsonObjectToList(String rsContent)
			throws Exception {
		rsContent=rsContent.replaceAll("null", "0");
		
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			Map<String, Object> map = new HashMap<String, Object>();
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				
				String key = (String) iter.next();
				Object str ="";
				try{
				str =  jsonObject.get(key);
				map.put(key, str);
				}catch(Exception e){
					
					e.printStackTrace();
					 str ="";
					 map.put(key, str);
				}
			
				
			}
			rsList.add(map);
		}
		return rsList;
	}
	
	
	public static Map<String, Object> getTaoKeToMapForNew(String url) {
		URL yahoo;
		Map<String, Object> map = null;
		try {
			yahoo = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yahoo.openStream(),"utf-8"));

			String inputLine;
			String json = "";
			while ((inputLine = in.readLine()) != null) {
				json += inputLine;
			}
			json=json.trim();
			if(!json.startsWith("{")){
				json=json.substring(1);
			}
			
			json = json.replaceAll("%", "%25"); 
			if ("".equals(json)) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	

	

	

	
	public static  List<Map<String, Object>> jsonObjectToMap(String rsContent,String type)
			throws Exception {
		
		List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		JSONArray arry = JSONArray.fromObject(rsContent);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				//if("goods_search_response".equals(key)){
				
				if(type.equals(key)){
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					datamap = jsonObjectToMap2(datavalue);
				}
				
			}
		}
		return datamap;
	}
	
	public static  List<Map<String, Object>> jsonObjectToMapNew(String rsContent,String type)
			throws Exception {
		
		List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		JSONArray arry = JSONArray.fromObject(rsContent);
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
					if(type.equals(key)){
					String datavalue=  "["+jsonObject.get(key)+"]";//把datavalue取出来
					//datamap = jsonObjectToMap2New(datavalue);
					datamap = jsonObjectToList(datavalue);
				}
					
				
			}
		}
		return datamap;
	}
	
	
	public static List<Map<String, Object>> jsonObjectToMap2New(String rsContent)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if("p_id_list".equals(key) ){
					
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
					}
				}else if("goods_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("order_list_get_response".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("goods_promotion_url_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}
			}	
			
		}
		return datamap1;
	}
	
	
	public static List<Map<String, Object>> jsonObjectToMapForGoodsList(String rsContent)
			throws Exception {
		
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if("goods_search_response".equals(key)){
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					datamap1=jsonObjectToMapForList(datavalue);
				}
				
			}
		}
		return datamap1;
	}
	

	public static List<Map<String, Object>>  jsonObjectToMapForList (String rsContent)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if("goods_list".equals(key) ){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=	 jsonObjectToList(datavalue);
					
					}
					
				}
				
				
			}
			
		}
		return datamap1;
	}
	
	
	
	public static List<Map<String, Object>> jsonObjectToMap2(String rsContent)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if("p_id_list".equals(key) ){
					
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
					}
				}else if("goods_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("order_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("goods_promotion_url_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("order_list_get_response".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("goods_cats_list".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}else if("queryPromotionGoodsByParam_result".equals(key)){   //----da  处理京东数据的
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					
					if(StringUtils.isNotBlank(datavalue)){
						datamap1 = jsonObjectToMap2(datavalue);
					}
				}else if("querygoodscategory_result".equals(key)){   //----da  处理京东数据的
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					
					if(StringUtils.isNotBlank(datavalue)){
						datamap1 = jsonObjectToMap2(datavalue);
					}
				}else if("data".equals(key)){			//-----da 处理queryPromotionGoodsByParam_result 结构中取到的data数据
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
				
					}
				}
			}	
			
		}
		return datamap1;
	}
	
	
	
	
    private static String sendPostForPdd(String url, String param){
    	 PrintWriter out = null;
         BufferedReader in = null;
         String result = "";
       
         try {
             URL realUrl = new URL(url);
             // 打开和URL之间的连接
             URLConnection conn = realUrl.openConnection();
             // 设置通用的请求属性
             conn.setRequestProperty("accept", "*/*");
             conn.setRequestProperty("connection", "Keep-Alive");
             conn.setRequestProperty("user-agent",
                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
             // 发送POST请求必须设置如下两行
             conn.setDoOutput(true);
             conn.setDoInput(true);
             // 获取URLConnection对象对应的输出流
             out = new PrintWriter(conn.getOutputStream());
             // 发送请求参数
             out.print(param);
             // flush输出流的缓冲
             out.flush();
             // 定义BufferedReader输入流来读取URL的响应
             in = new BufferedReader(
                     new InputStreamReader(conn.getInputStream(),"utf-8"));
             String line;
             while ((line = in.readLine()) != null) {
                 result += line;
             }
            
         } catch (Exception e) {
             System.out.println("发送 POST 请求出现异常！"+e);
             e.printStackTrace();
         }
         //使用finally块来关闭输出流、输入流
         finally{
             try{
                 if(out!=null){
                     out.close();
                 }
                 if(in!=null){
                     in.close();
                 }
             }
             catch(IOException ex){
                 ex.printStackTrace();
             }
         }
         return result;
    }
    
    
	
	 /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static List<Map<String, Object>>  sendPostForList(String url, String param,String type) {
        
    	String result = sendPostForPdd( url,  param);
    	
    	result = result.replaceAll("%(?![0-9a-fA-F]{2})", "%25"); 
      
    	List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		try {
			datamap1 = jsonObjectToMap(URLDecoder.decode("["+result+"]", "UTF-8"),type);

			


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return datamap1;
    }    

public static List<Map<String, Object>>  sendPostForListNew(String url, String param,String type) {
        
    	String result = sendPostForPdd( url,  param);
    	result = result.replaceAll("%(?![0-9a-fA-F]{2})", "%25"); 
    	List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		try {
			datamap1 = jsonObjectToMapNew(URLDecoder.decode("["+result+"]", "UTF-8"),type);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return datamap1;
    } 
    
    
    
    
    
    
    
    
    /*
     * 读取流
     * @param inStream
     * @return 字节数组
     * @throws Exception
     * */
    public static byte[] readStream(InputStream inStream) throws Exception{
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	byte[] buffer = new byte[1024];
    	int len = -1;
    	while ((len= inStream.read(buffer))!= -1) {
			outputStream.write(buffer,0,len);
		}
    	outputStream.close();
    	inStream.close();
    	return outputStream.toByteArray();
    }
 
//----da
	public static Map<String, Object> getTaoKeToMap(String url) {
		URL yahoo;
		Map<String, Object> map = null;
		try {
			yahoo = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yahoo.openStream(),"UTF-8"));
			String inputLine;
			String json = "";
			while ((inputLine = in.readLine()) != null) {
				json += inputLine;
			}
			System.out.println(inputLine);
			json=json.trim();
			if(!json.startsWith("{")){
				json=json.substring(1);
			}
			
			json = json.replaceAll("%", "%25"); 
			if ("".equals(json)) {
				return null;
			}
			map = jsonObjectToMap(URLDecoder.decode("["+json+"]", "UTF-8"));
			System.out.println(map);
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	
	
	public static Map<String, Object> jsonObjectToMap(String rsContent)
			throws Exception {
		
		JSONArray arry = JSONArray.fromObject(rsContent);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				Object value = jsonObject.get(key)==null?"":jsonObject.get(key);
				map.put(key, value);
				
				
			}
		}
		return map;
	}


	/**
	 * @param url  京东请求连接
	 * @param param
	 * @param string
	 * @param string2
	 * @return
	 */
	public static List<Map<String, Object>> jdsendPostForList(String url,
		String param, String type, String type2) {
		String result = sendPostForPdd( url,  param);
    	
    	result = result.replaceAll("%(?![0-9a-fA-F]{2})", "%25"); 
      
    	List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		try {
			datamap1 = jdjsonObjectToMap(URLDecoder.decode("["+result+"]", "UTF-8"),type,type2);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return datamap1;
		
	}
	
	public static  List<Map<String, Object>> jdjsonObjectToMap(String rsContent,String type,String type2)
			throws Exception {
		
		List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		JSONArray arry = JSONArray.fromObject(rsContent);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if(type.equals(key)){
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					datamap = jdjsonObjectToMap2(datavalue,type2);
				}
				
			}
		}
		return datamap;
	}
	
	public static List<Map<String, Object>> jdjsonObjectToMap2(String rsContent,String type2)
			throws Exception {
		JSONArray arry = JSONArray.fromObject(rsContent);
		List<Map<String, Object>> datamap1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				if("data".equals(key)){			//-----da 
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
					}
				}else if(type2.equals(key)){   //----da  处理京东数据的
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					if(StringUtils.isNotBlank(datavalue)){
						datamap1 = jdjsonObjectToMap2(datavalue,"data");
					}
				
				}else if("urlList".equals(key)){
					String datavalue= "["+jsonObject.get(key).toString()+"]";
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
					}
				}else if("result".equals(key)){
					String datavalue= jsonObject.get(key).toString();
					if(StringUtils.isNotBlank(datavalue)){
						datamap1=jsonObjectToList(datavalue);
					}
				}
			}	
			
		}
		return datamap1;
	}
	
}
