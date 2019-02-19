package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ProvinceEnumDAO;
import com.axp.domain.ProvinceEnum;

@Repository
public class ProvinceEnumDAOImpl extends BaseDaoImpl<ProvinceEnum> implements ProvinceEnumDAO {

	@SuppressWarnings("unchecked")
	@Override
	public ProvinceEnum findById(Integer id) {
		ProvinceEnum au =null;
		Session session = sessionFactory.getCurrentSession();
		List<ProvinceEnum> aulist = session.createQuery("from ProvinceEnum where isvalid=true and id = :zoneId")
				.setParameter("zoneId", id).list();
		if(aulist.size()>0){
			au = aulist.get(0);
		}
		return au;
	}
	
	@Override
	public ProvinceEnum getCurrentCity(Integer id) {
		ProvinceEnum au = this.findById(id);
		if(au.getLevel()==3){
			au = au.getProvinceEnum();
		}
		return au;
	}

	@Override
	public List<ProvinceEnum> getListByLevel(int level) {
		ProvinceEnum au =null;
		Session session = sessionFactory.getCurrentSession();
		List<ProvinceEnum> aulist = session.createQuery("from ProvinceEnum where isvalid=true and level = :level")
				.setParameter("level", level).list();
		
		return aulist;
	}

	@Override
	public List<ProvinceEnum> getListByParentCity(int zoneid) {
		ProvinceEnum au =null;
		Session session = sessionFactory.getCurrentSession();
		List<ProvinceEnum> aulist = session.createQuery("from ProvinceEnum where isValid = true and provinceEnum.id = :zoneid")
				.setParameter("zoneid", zoneid).list();
		
		return aulist;
	}

	@Override
	public List<ProvinceEnum> findLevel() {
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery("from ProvinceEnum where isvalid=true and level != 1");
		return createQuery.list();
	}
	
	@Override
	public List<ProvinceEnum> findByIsHot(Integer isHot) {
		ProvinceEnum au =null;
		Session session = sessionFactory.getCurrentSession();
		List<ProvinceEnum> aulist = session.createQuery("from ProvinceEnum where isValid = true and isHot = :isHot")
				.setParameter("isHot", isHot).list();
		
		return aulist;
	}

	@Override
	public ProvinceEnum getCurrentCity2(Integer id) {
		ProvinceEnum au = this.findById(id);
		if(au.getLevel()==3 && au.getLevel2()!=2){
			au = au.getProvinceEnum();
		}
		return au;
	}
	
}