package com.axp.service;



import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.SeLive;


public interface SeliveService extends IProfessionalService{
	//保存新增直播信息
	void del(String ids);
	void getPageList(HttpServletRequest request, HttpServletResponse response);
	void add(HttpServletRequest request, Integer id);
	void save(Integer id,Integer sId,String livename,String image,String liveUri,String sellerName,
			String sellerAddress,String sellerLogo,String remark,Timestamp begintime,Timestamp endtime,
			String imgRecommend,HttpServletRequest request,HttpServletResponse response,String name);
	
	  SeLive findById(Integer seLiveId);
	  void ajaxTop(Integer sid);
}
