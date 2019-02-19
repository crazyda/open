package com.axp.dao;

import java.util.List;

import com.axp.domain.Adverpool;

public interface AdverPoolDAO extends IBaseDao<Adverpool> {

	List<Adverpool> getHQAdverPool();

	List<Adverpool> getCityAdverPool(Integer zonrId,String ids);

	List<Adverpool> getAdminUserPool(Integer adminUserId,String ids);

	List<Adverpool> getHighLevelPool(Integer adminUserId);
	List<Adverpool> getSellerPool(Integer adminUserId,String ids);
	
	
}
