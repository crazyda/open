package com.axp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.axp.domain.ReBackOrder;

public interface IReBackOrderService extends IBaseService<ReBackOrder> {

	Map<String, Object> applyBackOrder(HttpServletRequest request);

	Map<String, Object> getBackOrder(HttpServletRequest request);

	Map<String, Object> getBackOrderList(HttpServletRequest request);

}
