package com.axp.dao;

import com.axp.domain.Members;
import com.axp.domain.MembersConfig;
import com.axp.domain.Users;

public interface IMembersDao extends IBaseDao<Members>  {
	
    public Members findByUserId(Integer userId);
    
    public Members findByUserId(Integer userId, Integer vipConfigId);

    public void saveTemporaryMember(Users user, MembersConfig config, String inviteCode);

    public void confirmMember(Users user, MembersConfig config, Double realMoney,
			String accountCode, Integer accountType);

	
}