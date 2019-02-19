package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.MessageType;

public interface SystemMessageService extends IBaseService<MessageType>{
	Map<String, Object> getMessageHome(HttpServletRequest request,HttpServletResponse response);
	
}
