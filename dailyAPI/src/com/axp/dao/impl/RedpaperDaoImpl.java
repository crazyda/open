package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.IRedpaperDao;
import com.axp.domain.NewRedPaperAddendum;
import com.axp.domain.Redpaper;

@SuppressWarnings("unchecked")
@Repository
public class RedpaperDaoImpl  extends BaseDaoImpl<Redpaper> implements IRedpaperDao{
	public static final Integer CHECKING =  0;//未审核
	public static final Integer PASS =  1;//审核通过
	public static final Integer FAIL =  2;//审核不通过
	public static final Integer ISSUEING =  3;//发放中
	public static final Integer TIMEOUT =  5;//过期
	public static final Integer INVALID =  10;//失效
	@Override
	public List<Redpaper> getList() {
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from Redpaper where isValid = :isValid and status = :status order by id";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isValid", true);
		queryObject.setParameter("status", true);
		return queryObject.list();
	}

	public List<NewRedPaperAddendum> findByUserIdAndEndTime(String userId,
			String settingId, Timestamp endTime) {
		Session session = sessionFactory.getCurrentSession();
		StringBuffer sb = new StringBuffer();
		sb.append("From NewRedPaperAddendum where users.id=:usersId AND setting.id =:settingId AND "
				+ "endTime >='" + endTime + "' AND endTime <= '" + endTime+"'");
		Query query = session.createQuery(sb.toString());
		query.setParameter("usersId",Integer.valueOf(userId));
		query.setParameter("settingId", Integer.valueOf(settingId));
		return query.list();

	}
	
}
