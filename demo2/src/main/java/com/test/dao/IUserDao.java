package com.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.entity.User;
@Repository("userDao")
public interface IUserDao {
	boolean add(User user);
	boolean add(List<User> list);  
	void delete(String key);  
	void delete(List<String> keys);  
	boolean update(User user);  
	User get(String keyId);  
	
	
	
}
