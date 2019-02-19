package com.axp.dao.impl;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.dao.SeliveDao;
import com.axp.domain.AdminUser;
import com.axp.domain.SeLive;


@Repository("seliveDao")
public class SeliveDaoImpl extends BaseDaoImpl<SeLive> implements SeliveDao{
	@Override
	public SeLive findById(Integer id){
		SeLive selive=null;
			Session session=sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist=session.createQuery("from SeLive where id=:id and isvalid=true")
					.setParameter("id", id).list();
			if (relivelist.size()>0) {
				selive=relivelist.get(0);
			}
		
		return selive;
	}
	@Override
	public SeLive findByAdminuserId(Integer adminuserId) {
		SeLive selive = null;
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist = session.createQuery("from SeLive where isvalid=true and adminuser.id = ?0")
					.setParameter("0", adminuserId).list();
			if (relivelist.size() > 0) {
				selive = relivelist.get(0);
			}
		return selive;
	}

	@Override
	public SeLive findBySellerId(Integer sellerId) {
		SeLive seLive=null;
			Session session=sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist =session.createQuery("from SeLive where isvalid=true and seller.id=?0").setParameter("0", sellerId).list();
			if (relivelist.size()>0) {
				seLive=relivelist.get(0);
			}
		return seLive;
	}
	
	@Override
	public SeLive findByLiveName(String livename) {
		SeLive selive = null;
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist = session.createQuery("from SeLive where livename = :livename and isvalid = true ")
					.setParameter("livename", livename).list();
			if (relivelist.size() > 0) {
				selive = relivelist.get(0);
			}
		return selive;
	}
	@Override
	public void del(String ids) {
		updatePropertyByIDs("isvalid", false, ids, SeLive.class);
	}
	@Override
	public SeLive findByAdminuser(AdminUser adminUser) {
		SeLive seLive=null;
			Session session=sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist=session.createQuery("from SeLive where adminUser= :adminUser and isvalid = true")
			.setParameter("adminUser", adminUser).list();
			if (relivelist.size() > 0) {
				seLive = relivelist.get(0);
			}
	
		return seLive;
	}
	@Override
	public void findByistop(boolean istop) {
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<SeLive> relivelist = session.createQuery("from SeLive where istop = 1 and isvalid = true ")
					.setParameter("istop", istop).list();
			if (relivelist.size() > 0) {
				SeLive seLive = relivelist.get(0);
			}
	}
	@Override
	public void ajaxTop(Integer sid) {
		Session session = sessionFactory.getCurrentSession();
		List<SeLive> relivelist = session.createQuery("from SeLive where istop = 1 ").list();
		if(relivelist !=null&&relivelist.size()>0){//把之前置顶的取消
			for(SeLive temp:relivelist){
				temp.setIstop(false);
				this.saveOrUpdate(temp);
			}
		}
		SeLive selive =this.findById(sid);
		if(selive==null){//如果没有找到产生异常。控制器捕获
			selive.getAdminUser();
		}
		selive.setIstop(true);
		this.saveOrUpdate(selive);
		
	}
	
	

}
