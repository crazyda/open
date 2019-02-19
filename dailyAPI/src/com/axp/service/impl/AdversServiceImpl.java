package com.axp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.AdminUser;
import com.axp.domain.Adverpool;
import com.axp.domain.Goods;
import com.axp.domain.ProvinceEnum;
import com.axp.service.AdversService;
import com.axp.util.CalcUtil;
import com.axp.util.GeoUtil;
import com.axp.util.StringUtil;

@Service
public class AdversServiceImpl extends BaseServiceImpl<Adverpool> implements AdversService{
	
	
	private Integer addAdverData(List<Adverpool> poolList, Integer poolIndex,
			Integer poolSize, List<Map<String, Object>> dataList,
			String basePath,String level) {
		int nextIndex = 0;
		if(poolList !=null){
			int [] arr = new int[poolSize];
			int size=poolList.size() >poolSize?poolSize:poolList.size();
			int totalSize=(int) CalcUtil.sub(poolList.size(), size);
			int totalSizes=(int) CalcUtil.sub(poolList.size(), poolIndex);
			if(poolIndex<= totalSize){//
				for(int i =0 ;i<size;i++){
					//arr[i] = poolIndex+i;
					arr[i] = (int) CalcUtil.add(poolIndex, i);
					//nextIndex = poolIndex+i;
					nextIndex = (int) CalcUtil.add(poolIndex, i);
				}
				nextIndex+=1;
			}else if(poolIndex >totalSize && poolIndex < poolList.size()){
				   for(int i = 0; i<totalSizes;i++){
					   arr[i]=poolIndex+i;
				   }
				   int j =0;
				   for(int i = totalSizes;i< size;i++){
					   arr[i]= j;
					   j++;
				   }
				   nextIndex =j;
			}else{
				for(int i =0 ;i<size;i++){
					arr[i] = i;
				}
				nextIndex =size;
			}
			for(int i = 0;i<size;i++){
				Adverpool pool = poolList.get(arr[i]);
				Goods adver = pool.getGoods();
				dataList.add(getAdverMap(adver,basePath,level));
			}
		}
		return nextIndex;
	}
	
	private Map<String, Object> getAdverMap(Goods adver, String basePath,String level){
		String imageUrl = adver.getAdverImgurls();
		if(StringUtils.isNotEmpty(imageUrl)){
			imageUrl = basePath+imageUrl;
		}
		int sellerid=0;
		if(adver.getSeller()!=null){
			sellerid=adver.getSeller().getId();
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("adverId", adver.getId());
		map.put("level", level);
		map.put("image", imageUrl);
		map.put("url", StringUtil.getNullValue(adver.getOutWebsite()));
		map.put("name", StringUtil.getNullValue(adver.getName()));
		map.put("sellerId", sellerid);
		return map;
	}
	
	
	//=======================================
	@Override
	public Map<String, Object> getAdvers(Integer userId, Integer zoneId,
			Integer pool1, Integer pool2, Integer pool3, Integer pool4,
			double lng, double lat, String basePath) {
		
		Map<String, Object> statusMap = new HashMap<String, Object>();
		statusMap.put("status", 0x01);
		statusMap.put("message", "请求成功");
		
		int allSize = 10;//总广告位
		int sellerSize = 5;//商圈位数
		int citySize = 2;//城市位数
		int provinceSize = 2;//省位数
		
		try{
			//用户所在区县的最近商圈
			int cityid=0;
			int provinceid=0;
			AdminUser sellerArea=null;
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<Map<String, Object>> dataList = new ArrayList<>();
			//总部广告
		List<Adverpool> HQPoollist = adverpoolDao.getHQAdverPool();
		
		StringBuilder hqIds=new StringBuilder();
		String hqid="";
		for (Adverpool adverpool : HQPoollist) {
			hqIds.append(",").append(adverpool.getId());
		}
		if(hqIds.length()==0){   //解决总部广告重复问题
			hqIds.append("-1");
		}else if(hqIds.length()>0){
			hqid=hqIds.replace(0, 1, "").toString();
		}
		
		
			if(zoneId>0){
		ProvinceEnum zone = provinceEnumDao.findById(zoneId);
		
		if(zone!=null){
		if(zone.getLevel2()==3||zone.getLevel()==3&&zone.getLevel2()==2){ //如果是区县镇就找商圈 
			sellerArea= adminUserDao.getSellerAreaByZoneId2(zoneId,lng,lat);
			cityid=zone.getProvinceEnumId2();
			provinceid=zone.getProvinceEnum2().getProvinceEnumId2();
		}
		if(zone.getLevel2()==2){
			cityid=zoneId;
			provinceid=zone.getProvinceEnumId2();
		}
			}
		AdminUser cityProxy = adminUserDao.getZoneAdminUser(cityid,75);
		AdminUser provinceCenter = adminUserDao.getZoneAdminUser(provinceid,85);
		
		//商圈广告
		List<Adverpool>  BAPoollist = new ArrayList<Adverpool>();//商圈广告
		List<Adverpool>  BAHighlist = new ArrayList<Adverpool>();//商家广告
		if(sellerArea!=null){
			BAPoollist = adverpoolDao.getAdminUserPool(sellerArea.getId(),hqid);//
			BAHighlist = adverpoolDao.getSellerPool(sellerArea.getId(),hqid);
			BAPoollist.addAll(BAHighlist);//
			sellerSize = BAPoollist.size()>sellerSize?sellerSize:BAPoollist.size();
		}else{
			sellerSize = 0;
		}
		//城市广告
		List<Adverpool> cityPoollist = new ArrayList<Adverpool>();
		List<Adverpool> cityHighlist = new ArrayList<Adverpool>();//公共
		if(cityProxy!=null){
			cityPoollist = adverpoolDao.getAdminUserPool(cityProxy.getId(),hqid);
			
		}
		if(zone.getLevel2()==3){ 
			cityHighlist = adverpoolDao.getCityAdverPool(cityid,hqid);
		}
		if(cityHighlist!=null){
			cityPoollist.addAll(cityHighlist);
			citySize = cityPoollist.size()>citySize?citySize:cityPoollist.size();
			if(cityPoollist.size()==0){
				citySize=0;
			}
		}else{
			citySize=0;
		}
		
		//运营中心广告
		List<Adverpool> provincePoollist = new ArrayList<Adverpool>();
		List<Adverpool> provinceHighlist = new ArrayList<Adverpool>();//公共
		if(provinceCenter!=null){
			provincePoollist = adverpoolDao.getAdminUserPool(provinceCenter.getId(),hqid);
		}
		if(provincePoollist!=null){
			provinceHighlist = adverpoolDao.getCityAdverPool(provinceid,hqid);//省
			provincePoollist.addAll(provinceHighlist);
			provinceSize = provincePoollist.size()>provinceSize?provinceSize:provincePoollist.size();
			if(provincePoollist.size()==0){
				provinceSize=0;
			}
		}else{
			provinceSize=0;
		}
		
		int nextIndex1 = addAdverData(BAPoollist,pool1,sellerSize,dataList,basePath,"1");
		int nextIndex2 = addAdverData(cityPoollist,pool2,citySize,dataList,basePath,"2");
		int nextIndex3 = addAdverData(provincePoollist,pool3,provinceSize,dataList,basePath,"3");
		dataMap.put("pool1", nextIndex1);
		dataMap.put("pool2", nextIndex2);
		dataMap.put("pool3", nextIndex3);
		}
		
		//总部最终广告位数
		int HQSize = allSize - sellerSize - citySize - provinceSize;
		int nextIndex4 = addAdverData(HQPoollist,pool4,HQSize,dataList,basePath,"4");
	
		dataMap.put("pool4", nextIndex4);
		dataMap.put("dataList", dataList);
		statusMap.put("data", dataMap);
		}catch(Exception e){
			e.printStackTrace();
			statusMap.put("status", -0x01);
			statusMap.put("message", "位置错误");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return statusMap;
	}
	
}