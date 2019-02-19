package com.axp.dao;

import com.axp.domain.SystemConfig;

public interface ISystemConfigDao extends IBaseDao<SystemConfig>{

	SystemConfig findByParameter(String parameter);

}
