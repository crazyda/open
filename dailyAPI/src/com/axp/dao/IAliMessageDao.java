package com.axp.dao;

import com.axp.domain.AliMessage;

public interface IAliMessageDao extends IBaseDao<AliMessage>{
	 AliMessage getAppKey(String template_code);
}
