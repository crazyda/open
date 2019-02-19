package com.axp.dao;

import java.util.List;

public interface IBaseDao<T> {
	void save(T t);

	void update(T t);
	
	void delete(T t);

	List<T> queryAll();

	T get(Integer id);

	void saveOrUpdate(T t);

	T findById(Integer id);

	 T merge(T t);
	 
	List<T> queryAllByIsvalid();
	List<T> queryAllByIsValid2();
	

	List<T> queryListByParameter(String parameter, Object value);

	List<T> queryListByParameterTwo(String parameter, Object value);

	List<T> queryListByParameterByIsvalid(String parameter, Object value);
	
	
	void updatePropertyByID(String propertyName, Object value, int id,
			Class<T> model);

	Object getPropertyById(String propertyName, Integer id);

	List<T> findByIds(String ids);

	Object getPropertyById(String propertyName, Integer id, Class<T> model);

	Integer queryCountByParameter(String parameter, Object value);
    
	List<T> findByProperty(String propertyName, Object value);
	
	List<T>	findByPropertyIsValid(String propertyName, Object value);

	public void saveList(List<T> list);
	public void saveScoreList(List<T> list);
}
