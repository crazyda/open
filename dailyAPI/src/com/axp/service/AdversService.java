package com.axp.service;

import java.util.Map;

import com.axp.domain.Adverpool;

public interface AdversService extends IBaseService<Adverpool>{
/*
	Map<String, Object> getPutOutAdvers(Integer userId, Integer zoneId,
			Integer pool1, Integer pool2, Integer pool3, Integer pool4,
			double lng, double lat, String basePath);*/

	Map<String, Object> getAdvers(Integer userId, Integer zoneId,
			Integer pool1, Integer pool2, Integer pool3, Integer pool4,
			double lng, double lat, String basePath);

}