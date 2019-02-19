package com.axp.dao;

import java.util.List;

import com.axp.domain.Seller;

public interface SellerDAO extends IBaseDao<Seller>{

	//或取分店ID
	List<Integer> getBrachesIds(Integer id);

	Seller findByUserId(Integer userId);

	Seller findByName(String name);
	
	List<Seller> getSellerListByAdminId(Integer userId);
}
