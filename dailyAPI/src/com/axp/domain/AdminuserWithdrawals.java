package com.axp.domain;

import java.sql.Timestamp;

/**
 * AdminUser entity. @author MyEclipse Persistence Tools
 */
public class AdminuserWithdrawals extends AbstractAdminuserWithdrawals implements java.io.Serializable {

	// Constructors
	public AdminuserWithdrawals(){
		
	}
	

	/** default constructor */
	public AdminuserWithdrawals(
			Integer id,
			Integer adminuserId,
			Integer dataId,
			Integer bankId,
			AdminUser adminUser,
			Double money,
			Timestamp createtime,Boolean isValid,Integer state,
			Timestamp checktime, Integer checker, String remark,
			Double counterFee,
			AdminuserWithdrawalsBank adminuserWithdrawalsBank,
			AdminuserWithdrawalsData adminuserWithdrawalsData) {
		super(adminuserId, adminuserId, dataId, bankId, adminUser, money, createtime, isValid, state, checktime, checker, remark, counterFee, adminuserWithdrawalsBank, adminuserWithdrawalsData);
	}

}
