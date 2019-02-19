package com.axp.service;


import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.Seller;

public interface ReGoodsOfTeamMallService  extends IBaseService<ReGoodsOfTeamMall>{

	 ReGoodsOfTeamMall buildReGoodsOfTeamMall(ReGoodsOfTeamMall teamMall,String name,ReGoodsOfBase baseGoods
				,ReGoodsOfSellerMall reGoodsOfSellerMall,Integer teamNum,Integer transportationType,Double transportationPrice,Integer repertory,String startTime,String endTime,Seller seller,
				Boolean isChecked, ReGoodsSnapshot reGoodsSnapshot,Boolean isRestrict,Integer  restrictNum,Double discountPrice);
	
}
