package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.Aritcles;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenClient;
import com.axp.service.OpenAppService;
import com.axp.service.OpenClientService;
import com.axp.util.JsonResponseUtil;
import com.axp.util.MD5Util;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;
import com.axp.util.UrlUtil;

@Service
public class OpenAppServiceImpl extends BaseServiceImpl<OpenApp> implements OpenAppService{

	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Override
	public List<String> findOpenAppUrl() {
		QueryModel model = new QueryModel();
		model.combPreEquals("checkIsPass", 1);
		model.combNotEqual("domain");
		List<OpenApp> openAppList = dateBaseDAO.findLists(OpenApp.class, model);
		List<String> returnUrl = new ArrayList<String>();
		for(OpenApp a : openAppList){
			returnUrl.add(a.getDomain());
		}
		return returnUrl;
	}
	
	
	
	


}
	
