package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserMessageDetailService {
	Map<String, Object> msgDetail(HttpServletRequest request,HttpServletResponse response);
}
