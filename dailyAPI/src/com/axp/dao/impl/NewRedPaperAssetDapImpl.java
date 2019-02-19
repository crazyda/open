package com.axp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.INewRedPaperAssetDao;
import com.axp.domain.NewRedPaperAsset;

@Repository("newRedPaperAssetDao")
public class NewRedPaperAssetDapImpl extends BaseDaoImpl<NewRedPaperAsset> implements INewRedPaperAssetDao{

	//更新过期额度状态
	@Override
	public Integer updateOutTime(){
		StringBuffer assetHql = new StringBuffer();
		assetHql.append("update NewRedPaperAsset ");
		assetHql.append("set status = "+NewRedPaperAsset.TIMEOUTSTATUS+" where endTime <= now() and status = "+NewRedPaperAsset.VALIDSTATUS);
		Session session = sessionFactory.getCurrentSession();
		Query queryObject = session.createQuery(assetHql.toString());
		return queryObject.executeUpdate();
	}
	
}
