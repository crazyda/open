package com.axp.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenClassify;
import com.axp.domain.OpenJDClassify;
import com.axp.service.OpenClassifyService;
import com.axp.service.OpenJDClassifyService;
import com.axp.util.DateUtil;
import com.axp.util.MD5Util;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;

@Service
public class OpenJDClassifyServiceImpl extends BaseServiceImpl<OpenJDClassify> implements OpenJDClassifyService{

	
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	
	public static  String url = "https://api.jd.com/routerjson"; //jd请求
	public static  String v = "2.0";//京东版本
	public static String app_key = "5D0A058C5A4D0FD677A5010B01B34554";
	public static String app_secret = "4b15ca55a6f04b62928f3075d8f7336e";
	public static String access_token = "8eee2fd5-4a91-4b04-ba04-0068f5d3f72d";
	public static String http = "http://img14.360buyimg.com/n1/";
	
	
	@Override
	public Map<String, Object> findAll(HttpServletRequest request) {
		QueryModel query = new QueryModel();
		List<OpenJDClassify> openJDClassify = dateBaseDAO.findLists(OpenJDClassify.class, query);
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("openJDClassify", openJDClassify);
		return statusMap;
	}

	@Override
	public Map<String, Object> jdClassify(String parentId,String grade) {
		
		if(!(parentId != "" && parentId != null)){
			parentId = "0";
		}
		if(!(grade != "" && grade != null)){
			grade = "0";
		}
		String method = "jingdong.union.search.goods.category.query";
		String timestamp = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("method", method);
		map.put("v", v);
		map.put("timestamp",timestamp+"");
		map.put("app_key", app_key);
		map.put("access_token", access_token);
		map.put("parent_id", parentId);//父级 类目级别
		map.put("grade", grade);
		String sign = MD5Util.getSign(map,app_secret);
		
		String url = "https://api.jd.com/routerjson";
		String param = "v=2.0&method="+method+"&app_key="+app_key+"&access_token="+access_token+"&360buy_param_json={\"parent_id\":\""+parentId+"\",\"grade\":\""+grade+"\"}&timestamp="+timestamp+"&sign="+sign;
		List<Map<String, Object>> classifyList = UrlUtil.sendPostForList(url, param,"jingdong_union_search_goods_category_query_responce");
		
		List <String> parentIds = new ArrayList<String>(); // 一级对应下来的二级类目 将作为下级类目查询的父级
		parentIds.clear();
		
		if(classifyList.size()>0 && classifyList != null){
			for(int i = 0;i<classifyList.size();i++){
				Map<String, Object> gmap = classifyList.get(i);
				OpenJDClassify jd = new OpenJDClassify();
				jd.setCatId((Integer)gmap.get("id"));
				jd.setGrade((Integer)gmap.get("grade"));
				jd.setParentId((Integer)gmap.get("parentId"));
				
				String name = (String)gmap.get("name") ;
				jd.setName(name);
				
				openJDClassifyDAO.saveOrUpdate(jd);
				
				parentIds.add(gmap.get("id").toString());//通过 0 0 得到一级类目对应的Id   在请求接口,得到二 三级类目 
				
			}
		}
		if("0".equals(grade)){
			for(int j=0;j<parentIds.size();j++){
				jdClassify(parentIds.get(j),"1");
			}
		}
		if("1".equals(grade)){
			for(int j=0;j<parentIds.size();j++){
				jdClassify(parentIds.get(j),"2");
			}
		}
		
		
		
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("classifyList", classifyList);
		return dataMap;
	}
	
	
	
	
	
}
	
