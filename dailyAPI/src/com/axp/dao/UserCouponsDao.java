package com.axp.dao;

import java.util.List;

import com.axp.domain.UserCoupons;
import com.axp.domain.Users;

public interface UserCouponsDao extends IBaseDao<UserCoupons>{

	 boolean updateCouponsStatus(List<UserCoupons> list, Users users);
}
