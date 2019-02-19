package com.axp.dao;

import java.util.List;

import com.axp.domain.SellerMainPage;


public interface SellerMainPageDAO extends IBaseDao<SellerMainPage> {

	SellerMainPage findOneBySellerId(Integer sellerId);

	List<SellerMainPage> getSellerMainPageIds(String ids);

}
