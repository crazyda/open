package com.axp.domain;

import java.sql.Timestamp;

/**
 * Buy entity. @author MyEclipse Persistence Tools
 */
public class Bonus extends AbstractBonus implements java.io.Serializable {

	public Bonus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bonus(Integer id, AdminUser adminUser, Users user,
			AdminUser adminUserBuy, Timestamp createTime, double maid,
			double maiddl, double maidhhr, double maidsj, Integer score,
			Integer recoveryScore, ReGoodsorderItem item) {
		super(id, adminUser, user, adminUserBuy, createTime, maid, maiddl, maidhhr,
				maidsj, score, recoveryScore, item);
		// TODO Auto-generated constructor stub
	}

	

	
	
}
