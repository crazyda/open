package com.axp.service;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {
	void save(T t);

	void update(T t);

	void delete(T t);

	List<T> queryAll();

	T get(Integer id);
	
}
