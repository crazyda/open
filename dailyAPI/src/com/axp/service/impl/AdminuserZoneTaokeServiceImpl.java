package com.axp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.axp.domain.AdminuserZoneTaoke;
import com.axp.service.AdminuserZoneTaokeService;
import com.axp.util.QueryModel;

@Service
public class AdminuserZoneTaokeServiceImpl extends BaseServiceImpl<AdminuserZoneTaoke> implements AdminuserZoneTaokeService{

	@Override
	public AdminuserZoneTaoke getAdminuserZoneTaokeByZone(Integer zoneid) {
		// TODO Auto-generated method stub
		QueryModel queryModel=new QueryModel();
		queryModel.clearQuery();
		queryModel.combEquals("isValid", 1);
		//queryModel.combPreEquals("provinceEnum.id", zoneid,"zoneId");
		//List<AdminuserZoneTaoke> aztlist=dateBaseDAO.findPageList(AdminuserZoneTaoke.class, queryModel,0, 100);
		queryModel.combPreEquals("provinceEnum.id", zoneid,"zoneId");
		List<AdminuserZoneTaoke> aztlist = dateBaseDAO.findLists(AdminuserZoneTaoke.class, queryModel);
		AdminuserZoneTaoke azt =null;
		if(aztlist!=null && aztlist.size()>0){
			azt=aztlist.get(0);
		}
		
		return azt;
	}

	
}