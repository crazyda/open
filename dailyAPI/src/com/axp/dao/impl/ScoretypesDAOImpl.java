package com.axp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.axp.dao.ScoretypesDAO;
import com.axp.domain.Scoretypes;

@Repository
public class ScoretypesDAOImpl extends BaseDaoImpl<Scoretypes> implements ScoretypesDAO {
	
	@Override
	public Map<Integer, String> getScoretypesRemarkMap(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		Query query = sessionFactory.getCurrentSession().createQuery("from Scoretypes where isvalid = true order by id asc");
		@SuppressWarnings("unchecked")
		List<Scoretypes> list = query.list();
		for(Scoretypes item : list){
			map.put(item.getId(), item.getName());
		}
		return map;
	} 
}