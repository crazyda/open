package com.axp.domain;

import java.sql.Timestamp;


public class AdminuserWithdrawalsDataLog extends AbstractAdminuserWithdrawalsDataLog implements java.io.Serializable{
	
	public AdminuserWithdrawalsDataLog(){
		
	}
	
	public AdminuserWithdrawalsDataLog(Integer id,
			Integer adminuserId,
			AdminUser adminUser,
			String name,
			String phone,
			String code,
			Timestamp cretatetime,
			Boolean isValid,
			String image,String image2,Integer status){
		super(id, adminuserId, name, phone, code, cretatetime, isValid, image, image2, status, adminUser);
		
	}
}
