package com.axp.dao;

import java.util.List;

import com.axp.domain.ReGoodsofextendmall;


public interface ReGoodsofextendmallDao extends IBaseDao<ReGoodsofextendmall> {

	/**
	 * @param mall
	 * @param orderData
	 * @return
	 */
	List<ReGoodsofextendmall> findByGdtc(int mall, String orderData,int typeId);

	/**
	 * @param mall
	 * @param orderDatas
	 * @param zoneId
	 * @return
	 */
	List<ReGoodsofextendmall> findByZbsc(int mall, String orderDatas,
			String zoneId,int typeId);

}
