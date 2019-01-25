package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.UsersOperationRecord;

public interface UsersOperationRecordService extends IBaseService<UsersOperationRecord>{
	boolean commitRecord(HttpServletRequest request);
	boolean isUsers(HttpServletRequest request);
}
