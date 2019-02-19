package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.OrderComment;


public interface OrderCommentService extends IBaseService<OrderComment>{
	
	Map<String, Object> getComment(HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getCommentList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> putCommentList(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> putComment(HttpServletRequest request,HttpServletResponse response);

	Map<String, Object> getCommentMap(OrderComment comment, String basePath);

	Map<String, Object> putCommentXCX(HttpServletRequest request, HttpServletResponse response);
}
