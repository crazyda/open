/**
 * 2018-10-15
 * Administrator
 */
package com.axp.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.axp.domain.GameActivity;


/**
 * @author da
 * @data 2018-10-15下午5:21:49
 */
public interface GameActivityService {
	/**
	 * 通过地区获取信息
	 * @param zoneId
	 * @return
	 */
	GameActivity getZoneId(Integer zoneId);
		
}
