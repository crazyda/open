package com.axp.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.axp.dao.DateBaseDAO;
import com.axp.domain.OpenApp;
import com.axp.domain.OpenClassify;
import com.axp.service.OpenClassifyService;
import com.axp.util.QueryModel;

@Service
public class OpenClassifyServiceImpl extends BaseServiceImpl<OpenClassify> implements OpenClassifyService{

	
	@Resource
	private DateBaseDAO dateBaseDAO;
	
	@Override
	public Map<String, Object> findAll(HttpServletRequest request) {
		QueryModel query = new QueryModel();
		List<OpenClassify> openClassify = dateBaseDAO.findLists(OpenClassify.class, query);
		
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("openClassify", openClassify);
		return statusMap;
	}

	/**
	 *展示每个平台对应的 分佣范围和分类类别
	 */
	@Override
	public Map<String, Object> choiceViwe(HttpServletRequest request,
			HttpServletResponse response) {
		QueryModel query = new QueryModel();
		List<OpenClassify> pddCliassify = dateBaseDAO.findLists(OpenClassify.class, query);
		query.clearQuery();
		query.combPreEquals("isValid", true);
		query.combPreEquals("isStop", true);
		query.combPreEquals("checkIsPass", 1);
		List<OpenApp> openApp  = dateBaseDAO.findLists(OpenApp.class, query);
		request.setAttribute("openApp", openApp);
		request.setAttribute("pddCliassify", pddCliassify);
		
		
		return null;
	}

	//针对不同的平台保存不同的分类,和分佣
	@Override
	public void doSave(HttpServletRequest request, String appId,List<Integer> pddifys,
			String start_rate, String end_rate) {
		OpenApp  openApp = openAppDAO.findById(Integer.valueOf(appId));
		openApp.setPddifys(pddifys.toString());
		openApp.setRate(start_rate+"-"+end_rate);
		openAppDAO.saveOrUpdate(openApp);
		
	}
	
	
	
	
	
}
	
