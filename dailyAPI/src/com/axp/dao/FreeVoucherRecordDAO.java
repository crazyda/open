package com.axp.dao;

import com.axp.domain.FreeVoucherRecord;

public interface FreeVoucherRecordDAO extends IBaseDao<FreeVoucherRecord> {

	Integer getCountByUserId(Integer userId);

	Integer updateStatus(Integer userId, Integer count, Integer status);

}
