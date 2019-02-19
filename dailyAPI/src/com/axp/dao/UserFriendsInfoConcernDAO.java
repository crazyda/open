package com.axp.dao;

import java.util.List;

import com.axp.domain.UserFriendsInfo;
import com.axp.domain.UserFriendsInfoConcern;
import com.axp.domain.Users;

public interface UserFriendsInfoConcernDAO extends IBaseDao<UserFriendsInfoConcern> {
	/**
	 * 获取用户关注的 信息
	 * @param user
	 * @param userFriendsInfo
	 * @return
	 */
	List<UserFriendsInfoConcern> getUserConcern(Users user, UserFriendsInfo userFriendsInfo);

}
