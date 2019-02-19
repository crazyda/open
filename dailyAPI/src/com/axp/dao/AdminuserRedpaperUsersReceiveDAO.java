package com.axp.dao;

import java.util.List;

import com.axp.domain.AdminuserRedpaperUsersReceive;

public interface AdminuserRedpaperUsersReceiveDAO extends IBaseDao<AdminuserRedpaperUsersReceive> {

	
	public List<AdminuserRedpaperUsersReceive> findUsersReceive(Integer usersId, Integer redpaperId) ;
	
}
