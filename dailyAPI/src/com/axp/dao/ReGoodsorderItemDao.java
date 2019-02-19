package com.axp.dao;

import java.util.List;

import com.axp.domain.ReGoodsOfLockMall;
import com.axp.domain.ReGoodsorderItem;

public interface ReGoodsorderItemDao extends IBaseDao<ReGoodsorderItem>{
	/**
	 * 获取子订单列表
	 * @param str
	 * @return
	 */
	public List<ReGoodsorderItem> getItemList(String str);

	List<ReGoodsorderItem> findByParent(Integer parentId);
	
	/**
	 * 根据主订单的id修改其所有子订单的Id状态
	 * @param status
	 * @param id
	 */
	void updateStatusByParent(Integer status,Integer id);

	public void deleteByOrderId(Integer orderId);

	Integer getQuantitySumByGoodsObject(String goodsObject, Integer userId,Boolean isTeam);

	/**
	 * 查询Lock下的订单并做处理的
	 * @param lm
	 * @return
	 */
	public List<ReGoodsorderItem> findByLockGoods(ReGoodsOfLockMall lm);

	/**
	 * 
	 * 分组查询的
	 * @param id
	 * @param string
	 * @return
	 */
	public List<ReGoodsorderItem> findByParticipant(Integer id, String string);
}
