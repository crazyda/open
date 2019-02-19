package com.axp.dao;

import java.util.List;

import com.axp.domain.ReGoodsorder;
import com.axp.domain.Users;

public interface ReGoodsorderDao extends IBaseDao<ReGoodsorder> {

	void deleteCache(Integer userId);
	
	public void deleteOrder(Integer userId);

	List<ReGoodsorder> getSessionOrders(Integer userId);

	List<ReGoodsorder> getToBePaidOrders(Integer userId);

	List<ReGoodsorder> getToBePaidOrders(Integer userId, String ids);

	void updateSessionOrderItems(String ids, String status);

	void deletOrderCar(String ids);

	List<ReGoodsorder> getOrders(String ids, Integer userId);

	void updateSessionOrders(String ids, String status, String name,
			String phone, String address);

	void updateStatus(Integer status, Integer orderId);
	Integer getCountByStatus(Integer sellerId, Integer status);
	
	Integer getCountBySellerId (Integer sellerId);
	
	Double getSumBySellerId (Integer sellerId);
	
	Double getSumBySellerIdForToday (Integer sellerId);

	void deleteById(Integer orderId);

	List<Integer> getOrderIdsByItems(String string);

	void updateIsHasItems(StringBuffer sb);
	
	 Object[] findOrderListByStatus(Integer userId,Integer status,Integer index,Integer size,Integer appVersion,Integer wx_sellerId);

	/**
	 * 查询订单四种状态 
	 * 1 待付款 2 待支付 3 待评价 4 退单售后
	 */
	 Object[] getOrderStatusNumByUserId(Users users );

	 Object[] getOrderStatusNumByUserIdNew(Users users );
	 /**
	  * 获取拼团订单信息
	  * @param sellerMallId
	  * @return
	  */
	 List<ReGoodsorder> getReGoodsOrderTeamInfo(Integer sellerMallId,Integer appVersion);
	 
	 /**
		 * 拼团订单展示信息
		 * @param reGoodsorder
		 * @return
		 */
	 List<ReGoodsorder> getOrderTeamStatusInfo(ReGoodsorder reGoodsorder);
	 /**
	  * 查询需要商品下架没有开奖的所有订单
	  * @return
	  */
	List<ReGoodsorder> findByScoreRegression();
}
