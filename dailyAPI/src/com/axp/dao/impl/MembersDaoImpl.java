package com.axp.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.IMembersDao;
import com.axp.domain.Members;
import com.axp.domain.MembersConfig;
import com.axp.domain.Users;

@SuppressWarnings("unchecked")
@Repository
public class MembersDaoImpl extends BaseDaoImpl<Members> implements IMembersDao {
	
	@Override
    public Members findByUserId(Integer userId) {
		Members au =null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Members where users.id = :userId and isValid = true and isActivate = true");
		List<Members> aulist = query.setParameter("userId", userId).list();
		if (aulist.size() > 0) {
			au = aulist.get(0);
		}
		return au;
	}
	
	@Override
    public Members findByUserId(Integer userId,Integer vipConfigId) {
		Members au =null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Members where users.id = :userId and membersConfig.id =:vipConfigId and isValid=true");
		query.setParameter("userId", userId);
		query.setParameter("vipConfigId", vipConfigId);
		List<Members> aulist = query.list();
		if (aulist.size() > 0) {
			au = aulist.get(0);
		}
		return au;
	}
	
	@Override
    public void saveTemporaryMember(Users user, MembersConfig config, String inviteCode) {
		Members au = new Members();
		au.setCreateTime(new Timestamp(System.currentTimeMillis()));
		au.setInviteCode(inviteCode);
		au.setIsActivate(false);
		au.setIsValid(true);
		au.setCash(config.getPayMoney());
		au.setMembersConfig(config);
		au.setRealPayMoney(99d);
		au.setUsers(user);
		this.save(au);
	}
	
	@Override
    public void confirmMember(Users user, MembersConfig config, Double realMoney, String accountCode, Integer accountType) {
		Members au = this.findByUserId(user.getId(), config.getId());
		if(au==null){
			return;
		}
		au.setAccountCode(accountCode);
		au.setAccountType(accountType);
		if(au.getCash()>=realMoney){
			System.out.println("资金充足，允许激活会员");
			au.setIsActivate(true);
		}else{
			System.out.println("资金不足，无法激活会员");
			au.setIsActivate(false);
		}
		this.update(au);
	}
	
}