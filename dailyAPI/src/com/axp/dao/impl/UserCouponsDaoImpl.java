package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.UserCouponsDao;
import com.axp.domain.UserCoupons;
import com.axp.domain.Users;
@Repository("userCouponsDao")
public class UserCouponsDaoImpl extends BaseDaoImpl<UserCoupons> implements UserCouponsDao{

	/**
	 * 修改用户优惠券使用状态
	 */
	@Override
	public boolean updateCouponsStatus(List<UserCoupons> list, Users users) {
		Session session = sessionFactory.getCurrentSession();
		for(int i=0;i<list.size();i++){
			Query query = session.createQuery("update UserCoupons set  isUse=:isUse  where id=:id and isValid=:valid");
			query.setParameter("isUse", true);
			query.setParameter("id", list.get(i).getId());
			query.setParameter("valid", true);
			query.executeUpdate();
		}
		return true;
	}
}
