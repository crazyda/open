package com.axp.service;

import java.util.List;

import com.axp.domain.UserCoupons;
import com.axp.domain.Users;

public interface UserCouponsService  extends IBaseService<UserCoupons>{


	
	/**
	 * 查询用户所有优惠券
	 * @param usersId
	 * @return
	 */
	List<UserCoupons> getUserAllCouponsList(Integer usersId);
	
	/**
	 * 查询用户使用的优惠券
	 */
	List<UserCoupons> findCouponsByUserIsUse(Users users,String orderIds);
	
	/**
	 * 更改用户优惠券使用状态
	 */
	
	boolean updateCouponsStatus(List<UserCoupons> list,Users users);
	 
	/**
	 * 得到用户的优惠券
	 * @param users
	 * @param goodsId
	 * @return
	 */
	 List<UserCoupons> getUserCouponsListByGoodsId(Users users, String goodsId) ;
	 
	 List<UserCoupons> getUserCoupons(String ids,Users users);
}
