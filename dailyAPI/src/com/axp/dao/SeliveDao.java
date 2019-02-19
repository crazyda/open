package com.axp.dao;

import com.axp.domain.AdminUser;
import com.axp.domain.SeLive;



public interface SeliveDao extends IBaseDao<SeLive>{
	 
	SeLive findById(Integer id);
	
	SeLive findByAdminuserId(Integer adminuserId);
	
	SeLive findBySellerId(Integer sellerId);

	SeLive findByLiveName(String livename);
	
	
	void del(String ids);
	
	SeLive findByAdminuser(AdminUser adminUser);
	
	void findByistop(boolean istop);
	
	void ajaxTop(Integer sid);
}
