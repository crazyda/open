package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongcloud.models.CodeSuccessResult;
import com.rongcloud.models.GroupInfo;
import com.rongcloud.models.TokenResult;

public interface RongCloudService {

	TokenResult getToken(String userId, String name, String portraitUri);
	
	
	CodeSuccessResult getGroupToken(String[] userId,String groupId ,String groupName);
	
	CodeSuccessResult getSyncGroup(String userId,GroupInfo[] groupInfo);
	
	public Map<String,Object> getJoinGroup(HttpServletRequest request);
	
	
}
