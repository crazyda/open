package com.axp.service;

import java.util.List;
import java.util.Map;

import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsofextendmall;

public interface ReGoodsofextendmallService extends IBaseService<ReGoodsofextendmall> {

	
	/**
	 * 计算劵后价
	 * @param specifications
	 * @return
	 */
	public String  computeDeductPrice(List<Map<String, Object>> specifications,Double DeductPrice);
	
	
	/**
	 * 计算劵后价
	 */
	public Double  computeDeductPrice(ReBaseGoods reBaseGoods,Integer couponType);
}
