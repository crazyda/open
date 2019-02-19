package com.axp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axp.dao.UserCouponsDao;
import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.UserCoupons;
import com.axp.domain.Users;
import com.axp.service.UserCouponsService;
import com.axp.util.CalcUtil;
import com.axp.util.QueryModel;

@Service("userCouponsService")
public class UserCouponsServiceImpl extends  BaseServiceImpl<UserCoupons> implements UserCouponsService  {

	@Autowired
	private UserCouponsDao userCouponsDao;
	
	@Override
	public List<UserCoupons> getUserAllCouponsList(Integer usersId) {
		QueryModel queryModel1=new QueryModel();
		queryModel1.clearQuery();
		queryModel1.combPreEquals("users.id",usersId,"userId");
		queryModel1.combEquals("isValid", 1);
		queryModel1.combEquals("isUse", 0); //未使用
		queryModel1.combCondition("ticket.validtime>sysdate() and sign=1");
		List<UserCoupons> userCouponsList = dateBaseDAO.findLists(UserCoupons.class, queryModel1);
		return userCouponsList;
	}
	
	
	@Override
	public List<UserCoupons> getUserCouponsListByGoodsId(Users users, String goodsId) {
		QueryModel queryModel1=new QueryModel();
		queryModel1.clearQuery();
		queryModel1.combPreEquals("users.id",users.getId(),"userId");
		queryModel1.combEquals("isValid", 1);
		queryModel1.combEquals("isUse", 0); //未使用
		queryModel1.combEquals("goodsMall",goodsId); //未使用
		queryModel1.combCondition("ticket.validtime>sysdate() and sign=1");
		List<UserCoupons> userCouponsList = dateBaseDAO.findLists(UserCoupons.class, queryModel1);
		return userCouponsList;
	}

	

	/**
	 * 得到用户使用的优惠券
	 */
	@Override
	public List<UserCoupons> findCouponsByUserIsUse(Users users,String orderIds) {
			
			List<UserCoupons> isUseCoupons=new ArrayList<UserCoupons>();
		
			QueryModel model = new QueryModel();
			model.combCondition("order.id in ("+orderIds+")");
			model.combPreEquals("isValid", true);
			List<ReGoodsorderItem> item = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
		
			List<UserCoupons> userAllCouponsList = getUserAllCouponsList(users.getId());
			if(userAllCouponsList!=null&&userAllCouponsList.size()>0){
			for(int i=0;i<userAllCouponsList.size();i++){
				Integer mallTypeId = ReBaseGoods.getMallTypeId(userAllCouponsList.get(i).getGoodsMall().substring(0,3));
				Integer goodId =Integer.parseInt( userAllCouponsList.get(i).getGoodsMall().substring(3));
				for(int k=0;k<item.size();k++){
				//找到对应优惠券的商品
					if(mallTypeId==ReBaseGoods.getMallTypeId(item.get(k).getMallClass())&&goodId.equals(item.get(k).getMallId())){
					isUseCoupons.add(userAllCouponsList.get(i)); //优惠券id
					userAllCouponsList.remove(i);
					break;
						}
					}
				}
			}
		return isUseCoupons;
	}

	/**
	 * 修改用户优惠券使用状态
	 */
	@Override
	public boolean updateCouponsStatus(List<UserCoupons> list, Users users) {
		return	userCouponsDao.updateCouponsStatus(list, users);
	}

	
	/** 
	 * 查找订单下的优惠券
	 * @param ids
	 * @param users
	 */
	public List<UserCoupons> getUserCoupons(String ids,Users users){
		List<UserCoupons> userCouponsList=new ArrayList<UserCoupons>();
		QueryModel model = new QueryModel();
		model.combCondition("order.id ="+ids);
		model.combPreEquals("isValid", true);
		List<ReGoodsorderItem> items = dateBaseDAO.findLists(ReGoodsorderItem.class, model);
		
		for (ReGoodsorderItem item : items) {
			String name=item.getMallClass()+item.getGoodsId().toString();
			List<UserCoupons> list = getUserCouponsListByGoodsId(users, name);
			if(list.size()>0){
				userCouponsList.add(list.get(0));
			}
		}
		return userCouponsList;
	}
	
	
}
