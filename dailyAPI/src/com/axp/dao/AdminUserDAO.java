package com.axp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.axp.domain.AdminUser;
import com.axp.domain.Users;

public interface AdminUserDAO extends IBaseDao<AdminUser> {

	AdminUser getLoginAdminUser(String loginname);

	void updateInviteCode(AdminUser adminUser);

	AdminUser getCurrentUser(HttpServletRequest request);

	List<AdminUser> findAll();

	AdminUser getClosestBA(double lng, double lat);

	AdminUser getZoneAdminUser(Integer zoneId,Integer level);
	List<AdminUser> getAdminUserListByZones(Integer zoneId,double lng,double lat);

	AdminUser getBySellerId(Integer sellerId);

	AdminUser getSellerAreaByZoneId(Integer zoneId, double lng, double lat);

	AdminUser getHeadquarters();
	
	List<AdminUser> getLoginByLoginname(String loginname);
	List<AdminUser> getAdminUserByInvitecode(String invitecode);
	AdminUser getSellerAreaByZoneId2(Integer zoneId,double lng,double lat);
	/**
	 * 是否拥有推广特产权限
	 */
	public boolean isNationwide(Integer roleId);
	
	
	AdminUser findByNameOrPhone(String presenterName);
}
