package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.SeliveDao;
import com.axp.domain.Adverpool;
import com.axp.domain.ProvinceEnum;
import com.axp.domain.SeLive;
import com.axp.domain.Seller;
import com.axp.service.LiveService;
import com.axp.util.CalcUtil;
import com.axp.util.JsonResponseUtil;
import com.axp.util.Parameter;
import com.axp.util.ParameterUtil;
import com.axp.util.QueryModel;

@Service
public class LiveServiceImpl extends BaseServiceImpl<Adverpool> implements LiveService{
	

	@Override
	public Map<String, Object> getTopList(Integer userId, Integer zoneId,
			String basePath) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
			
		//粉丝当前所在城市
		ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
		//获取离粉丝最近的商圈
		//AdminUser closestBA = adminUserDao.getZoneAdminUser(zone.getId());
	
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", "技术直播");
		dataMap.put("imgae", "http://39.104.160.116:8080/jupinhuiAPI/1.png");
		dataMap.put("uri", "");
		Map<String, Object> dataMap2 = new HashMap<String, Object>();
		dataMap2.put("name", "每天积分直播");
		dataMap2.put("imgae", "");
		dataMap2.put("uri", "http://39.104.160.116:8080/jupinhuiAPI/2.png");
		Map<String, Object> dataMap3 = new HashMap<String, Object>();
		dataMap3.put("name", "518直播");
		dataMap3.put("imgae", "http://39.104.160.116:8080/jupinhuiAPI/3.png");
		dataMap3.put("uri", "");
		Map<String, Object> dataMap4 = new HashMap<String, Object>();
		dataMap4.put("name", "换货会直播");
		dataMap4.put("imgae", "http://39.104.160.116:8080/jupinhuiAPI/4.png");
		dataMap4.put("uri", "");
		 
		dataList.add(dataMap);
		dataList.add(dataMap2);
		dataList.add(dataMap3);
		dataList.add(dataMap4);
		
		statusMap.put("data", dataList);
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "位置错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}

	@Override
	public Map<String, Object> getList(Integer userId, Integer zoneId,
			String basePath,Integer pageIndex) {
		
		
			Map<String, Object> statusMap = new HashMap<String, Object>();
			
		
			try{
		//粉丝当前所在城市
				ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
				//获取离粉丝最近的商圈
				//AdminUser closestBA = adminUserDao.getZoneAdminUser(zone.getId());
				//获得商圈和商家的直播列表
				QueryModel queryModel = new QueryModel();
				queryModel.combPreEquals("adminUser.provinceEnum.provinceEnum.id", zone.getId(),"adminUser");
				queryModel.combPreEquals("isvalid", true);
				
				int pageSum=(int) CalcUtil.sub(pageIndex, 1);
				int totalPage=(int) CalcUtil.mul(pageSum, 6);
				List<SeLive> seliveList = dateBaseDAO.findPageList(SeLive.class, queryModel, totalPage, 6);
				//获得代理的直播列表
				queryModel.clearQuery();
				queryModel.combPreEquals("adminUser.provinceEnum.id", zone.getId(),"adminUser");
				queryModel.combPreEquals("isvalid", true);
				Map<String, Object> data = new HashMap<String, Object>();
				List<SeLive> seliveList2 = dateBaseDAO.findPageList(SeLive.class, queryModel, totalPage, 6);
				List<Map<String, Object>> dataList = new ArrayList<>();
				for(SeLive sl :seliveList2){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("liveid", sl.getId());
					dataMap.put("name", sl.getLivename());
					dataMap.put("imgae",basePath+sl.getImage());
					dataMap.put("uri", sl.getLiveUri());
					dataMap.put("logo", sl.getSellerLogo());
					dataMap.put("livename",sl.getSellerName());
					dataList.add(dataMap);
				}
				
				for(SeLive sl :seliveList){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("liveid", sl.getId());
					dataMap.put("name", sl.getLivename());
					dataMap.put("imgae",basePath+sl.getImage());
					dataMap.put("uri", sl.getLiveUri());
					dataMap.put("logo", basePath+sl.getSellerLogo());
					dataMap.put("livename",sl.getSellerName());
					dataList.add(dataMap);
				}
				data.put("dataList", dataList);
				
				statusMap.put("data", data);
				
				
				statusMap.put("status", 0x01);
				statusMap.put("message", "请求成功");
				}catch(Exception e){
					e.printStackTrace();
					statusMap.put("status", -0x01);
					statusMap.put("message", "位置错误");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				return statusMap;
	}

	@Override
	public Map<String, Object> getListById(Integer userId, Integer zoneId,
			String basePath,Integer liveid) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		
		try{
	//粉丝当前所在城市
			
			if(liveid==null){
				statusMap.put("status", -0x02);
				statusMap.put("message", "无此直播");
			}
			
			//ProvinceEnum zone = provinceEnumDao.getCurrentCity(zoneId);
			//获取离粉丝最近的商圈
			//AdminUser closestBA = adminUserDao.getZoneAdminUser(zone.getId());
			
			SeLive sl  = seliveDao.findById(liveid);
		
			
			List<Map<String, Object>> dataList = new ArrayList<>();
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("liveid", sl.getId());
			dataMap.put("name", sl.getLivename());
			dataMap.put("imgae",basePath+sl.getImage());
			dataMap.put("uri", sl.getLiveUri());
			dataMap.put("logo", basePath+sl.getSellerLogo());
			dataMap.put("livename",sl.getSellerName());
			dataMap.put("viewname",sl.getRemark());
			dataMap.put("sellerId", sl.getSeller()==null?"":sl.getSeller().getId().toString());
			dataList.add(dataMap);
			data.put("dataList", dataList);
			
			statusMap.put("data", data);
			
			statusMap.put("status", 0x01);
			statusMap.put("message", "请求成功");
			}catch(Exception e){
				e.printStackTrace();
				statusMap.put("status", -0x01);
				statusMap.put("message", "位置错误");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return statusMap;
	}

	//

	/*public Map<String, Object> getSeLivelist(HttpServletRequest request,
			HttpServletResponse response) {
		Parameter parameter=ParameterUtil.getParameter(request);
		String basePath = request.getServletContext().getAttribute("RESOURCE_LOCAL_URL").toString();
		if (parameter == null) {
			return JsonResponseUtil.getJson(-0x02,"参数data不是合法的json字符串");
		}
		Integer pageIndex = parameter.getData().getInteger("pageIndex");
		String userId = parameter.getUserId();
		String id = (String)request.getAttribute("live_id");
		
		String zoneid = parameter.getZoneId();
		
			
		QueryModel queryModel = new QueryModel();
		queryModel.combPreEquals("users.id", Integer.parseInt(userId),"userId");
		queryModel.combPreEquals("isvalid", true);
		Seller seller = (Seller) dateBaseDAO.findOne(Seller.class, queryModel);
		
		queryModel.clearQuery();
		queryModel.combPreEquals("id", id );
		queryModel.combPreEquals("isValid", true);
		queryModel.combPreEquals("seller.id",seller.getId(),"sellerId");

		int count = dateBaseDAO.findCount(SeLive.class, queryModel);
		List<SeLive> seliveList = dateBaseDAO.findPageList(SeLive.class, queryModel, (pageIndex-1)*8, 8);
		List<Map<String,Object>> seliveDataList = new ArrayList<>();
		Map<String,Object> seliveMap = null;
		
		for(SeLive selive : seliveList){
			seliveMap = new HashMap<>();
			queryModel.clearQuery();
			queryModel.combPreEquals("isValid", true);
			queryModel.combPreEquals("selive.id", selive.getId(),"id");
			
		}
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("dataList", seliveDataList);
		dataMap.put("pageSize", count%8==0?count/8:(count/8+1));
		dataMap.put("pageIndex", pageIndex);
		dataMap.put("pageItemCount", 8);
		Map<String,Object> bigDataMap = new HashMap<>();
		bigDataMap.put("data", dataMap);
		bigDataMap.put("message", "请求成功");
		return bigDataMap;
	}*/
	}
	
