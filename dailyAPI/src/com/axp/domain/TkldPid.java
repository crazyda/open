package com.axp.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class TkldPid  extends AbstractTkldPid implements Serializable{


	public TkldPid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TkldPid(Integer id, String pId, Integer level, String ldLoginName,
			String ldLoginPwd, String ldLoginReamrk, String remark,
			TkldPid tkldPid, Timestamp bindingTime, AdminUser adminUser,
			Users users, ProvinceEnum provinceEnum, String usersRemark,
			String adminUserReamrk, boolean isValid, Timestamp createTime,
			Integer isCareerPartner, String pddPid, double settlementAmount,Integer parentPidId) {
		super(id, pId, level, ldLoginName, ldLoginPwd, ldLoginReamrk, remark, tkldPid,
				bindingTime, adminUser, users, provinceEnum, usersRemark,
				adminUserReamrk, isValid, createTime, isCareerPartner, pddPid,
				settlementAmount,parentPidId);
		// TODO Auto-generated constructor stub
	}


	
	
	
	
}
