package com.axp.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.axp.dao.AdverPoolDAO;
import com.axp.dao.ProvinceEnumDAO;
import com.axp.domain.Adverpool;
import com.axp.domain.ProvinceEnum;

@Repository
public class AdverPoolDAOImpl extends BaseDaoImpl<Adverpool> implements AdverPoolDAO {
	
	@Autowired
	private ProvinceEnumDAO provinceEnumDAO;
	
	/**
	 * 总部广告池
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Adverpool> getHQAdverPool(){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Adverpool as model where model.isvalid=true " +
				"and model.higelevel = false and starttime <= current_timestamp() " +
				"and  endtime >= current_timestamp() and model.isplay =2 and model.adminUser.level in (99,95)";
		Query queryObject = session.createQuery(queryString);
		return queryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Adverpool> getAdminUserPool(Integer adminUserId,String ids){
		Session session = sessionFactory.getCurrentSession();
		Query queryObject = null;
		if (StringUtils.isNotBlank(ids)) {
			String queryString = "from Adverpool as model where model.isvalid=true " +
					"and model.higelevel = false and starttime <= current_timestamp() " +
					"and  endtime >= current_timestamp() and model.isplay =2 and " +
//					"(model.adminUser.parentAdminUser.id = :adminUserId or model.adminUser.id=:adminUserId2)";
					"model.adminUser.id=:adminUserId2  and id not in("+ids+") ";
			queryObject = session.createQuery(queryString);
//			queryObject.setParameter("adminUserId", adminUserId);
			queryObject.setParameter("adminUserId2", adminUserId);
		}
		return queryObject.list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Adverpool> getHighLevelPool(Integer adminUserId){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Adverpool as model where model.isvalid=true " +
				"and model.higelevel = true and starttime <= current_timestamp() " +
				"and  endtime >= current_timestamp() and model.isplay =2 and " +
				"model.adminUser.parentAdminUser.id=:adminUserId2";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("adminUserId2", adminUserId);
		return queryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Adverpool> getSellerPool(Integer adminUserId,String ids){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Adverpool as model where model.isvalid=true " +
				"and model.higelevel = true and starttime <= current_timestamp() " +
				"and  endtime >= current_timestamp() and model.isplay =2 and " +
				"model.adminUser.parentAdminUser.id=:adminUserId2  and id not in("+ids+") ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("adminUserId2", adminUserId);
		return queryObject.list();
	}
	

	@SuppressWarnings("unchecked")		
	@Override   
	public List<Adverpool> getCityAdverPool(Integer zonrId,String ids){ 
		Session session = sessionFactory.getCurrentSession(); 
		ProvinceEnum provinceEnum = provinceEnumDAO.findById(zonrId);
		
		String queryString = "from Adverpool as model where model.isvalid=true " +
				"and model.higelevel = true and model.starttime <= current_timestamp() " +
				"and model.endtime >= current_timestamp() and model.isplay =2 " +
				"and model.adminUser.provinceEnum2.provinceEnum2.id = :provinceEnumId and id not in("+ids+")";
				if(provinceEnum.getLevel2()==3){
					queryString		+= " and  model.adminUser.provinceEnum2.provinceEnum2.level2=3" ;
				}
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("provinceEnumId", zonrId);
		return queryObject.list();
	}
}
