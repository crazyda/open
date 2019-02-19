package com.axp.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.axp.domain.CommissionRate;
import com.axp.service.CommissionRateService;
import com.axp.util.QueryModel;
@Service("commissionRateService")
public class CommissionRateServiceImpl extends BaseServiceImpl<CommissionRate> implements CommissionRateService  {

	/**
	 * 倒序得到最近的佣金分配率
	 */
	@Override
	public CommissionRate findCommissionRate() {
		QueryModel model=new QueryModel();
		model.combEquals("isValid", 1);
		model.setOrder("createtime desc");
		List<CommissionRate> list = dateBaseDAO.findLists(CommissionRate.class, model);
		return list.size()>0?list.get(0):null;
	}

}
