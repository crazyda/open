package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.test.dao.IUserDao;
import com.test.dao.impl.UserDao;
import com.test.entity.User;

@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RedisTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private IUserDao userDao;
	@Test
	public void testAddUser() {
		User user = new User();
		user.setId("user1");
		user.setName("java2000_wl1");
		boolean result = userDao.add(user);
		Assert.assertTrue(result);
	}

	@Test
	public void testAddUsers1() {
		List<User> list = new ArrayList<User>();
		for (int i = 10; i < 50000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("java2000_wl" + i);
			list.add(user);
		}
		long begin = System.currentTimeMillis();
		for (User user : list) {
			userDao.add(user);
		}
		System.out.println(System.currentTimeMillis() - begin);
	}

	@Test
	public void testAddUsers2() {
		List<User> list = new ArrayList<User>();
		for (int i = 50000; i < 100000; i++) {
			User user = new User();
			user.setId("user" + i);
			user.setName("java2000_wl" + i);
			list.add(user);
		}
		long begin = System.currentTimeMillis();
		boolean result = userDao.add(list);
		System.out.println(System.currentTimeMillis() - begin);
		Assert.assertTrue(result);
	}

	@Test
	public void testUpdate() {
		User user = new User();
		user.setId("user1");
		user.setName("new_password");
		boolean result = userDao.update(user);
		Assert.assertTrue(result);
	}

	@Test
	public void testGetUser() {
		String id = "user1";
		User user = userDao.get(id);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getName(), "java2000_wl1");
	}

	@Test
	public void testDelete() {
		String key = "user1";
		userDao.delete(key);
	}

	@Test
	public void testDeletes() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 100000; i++) {
			list.add("user" + i);
		}
		userDao.delete(list);
	}

}
