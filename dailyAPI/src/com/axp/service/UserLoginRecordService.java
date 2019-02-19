package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.axp.domain.UserLoginRecord;

public interface UserLoginRecordService extends IBaseService<UserLoginRecord>{
	Map<String, Object> commitRecord(HttpServletRequest request);
}
