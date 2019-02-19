package com.axp.service;
import com.axp.domain.FreeVoucherRecord;
import com.axp.domain.Users;

public interface IFreeVoucherService extends IBaseService<FreeVoucherRecord>{

	void addMembersVoucher(Users user, Integer type, Integer count);

}