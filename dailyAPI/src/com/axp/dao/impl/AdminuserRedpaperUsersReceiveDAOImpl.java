package com.axp.dao.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.AdminuserRedpaperUsersReceiveDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.AdminuserRedpaperUsersReceive;
import com.axp.util.StringUtil;

@Repository
public class AdminuserRedpaperUsersReceiveDAOImpl extends BaseDaoImpl<AdminuserRedpaperUsersReceive> implements AdminuserRedpaperUsersReceiveDAO {

	@Override
	public List<AdminuserRedpaperUsersReceive> findUsersReceive(
			Integer usersId, Integer redpaperId) {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminuserRedpaperUsersReceive as model where model.userid = :usersId and model.isvalid = :isvalid and model.redpaper = :redpaperId order by id desc";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		queryObject.setParameter("usersId", usersId);
		queryObject.setParameter("redpaperId", redpaperId);
		return queryObject.list();
		
	}
	
	

}
