package com.axp.dao;

import com.axp.domain.ReBackOrder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.Users;

public interface IReBackOrderDao extends IBaseDao<ReBackOrder>{

	Integer getNotHandledCountBySeller(Integer sellerId);

	void saveBackOrder(ReGoodsorderItem item, Users user, String reason,
			Integer type, String imageJson,Integer backstate);

}
