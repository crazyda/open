package com.axp.service;

import com.axp.domain.AdminuserZoneTaoke;

public interface AdminuserZoneTaokeService extends IBaseService<AdminuserZoneTaoke>{
	
	AdminuserZoneTaoke getAdminuserZoneTaokeByZone(Integer zoneid);
	
}
