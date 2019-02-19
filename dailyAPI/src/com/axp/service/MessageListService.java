package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MessageListService {
	Map<String, Object> getMessageList(HttpServletRequest request,HttpServletResponse response);
	
	Map<String, Object> delMessage(HttpServletRequest request,HttpServletResponse response);
}
