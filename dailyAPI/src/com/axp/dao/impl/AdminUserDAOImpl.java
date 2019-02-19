package com.axp.dao.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.AdminUserDAO;
import com.axp.domain.AdminUser;
import com.axp.domain.Users;
import com.axp.util.StringUtil;

@Repository
public class AdminUserDAOImpl extends BaseDaoImpl<AdminUser> implements AdminUserDAO {
	
	/**
	 * 获得登陆用户
	 * @param loginname
	 * @param password
	 * @return
	 */
	@Override
	public AdminUser getLoginAdminUser(String loginname){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true and model.loginname=:loginname";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("loginname", loginname);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	/**
	 * 保存用户邀请码
	 * @param adminUser
	 */
	@Override
	public void updateInviteCode(AdminUser adminUser){
		String invitecode = "1"+adminUser.getId();
		adminUser.setInvitecode(invitecode);
		saveOrUpdate(adminUser);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUser> findAll(){
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid=:isvalid";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("isvalid", true);
		return queryObject.list();
	}
	
	@Override
	public AdminUser getBySellerId(Integer sellerId){
		AdminUser admin = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.sellerId = :sellerId and model.isvalid=:isvalid";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("sellerId", sellerId);
		queryObject.setParameter("isvalid", true);
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			admin = list.get(0);
		}
		return admin;
	}
	
	/**
	 * 获得当前已登录用户
	 */
	@Override
	public AdminUser getCurrentUser(HttpServletRequest request){
		Object id = request.getSession().getAttribute("currentUserId");
		if(id!=null){
		int currentUserId = Integer.parseInt(id.toString());
		AdminUser currentUser = this.findById(currentUserId);
			if(currentUser!=null){
				return currentUser;
			}
		}
		return null;
	}
	
	/**
	 * 获得离粉丝定位最近的管理分部
	 * @param loginname
	 * @param password
	 * @return
	 */
	@Override
	public AdminUser getClosestBA(double lng, double lat){
		return this.getInAreaAdminUser(StringUtil.ADVERTWO,lng,lat);
	}
	
	/**
	 * 获取里定位最近的分部
	 * @param level
	 * @param lng
	 * @param lat
	 * @return
	 */
	private AdminUser getClosestAdminUser(Integer level, double lng, double lat){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true ";
		String distance = "(round(6378138*2*asin(sqrt(pow(sin( ("+lat+"*pi()/180-model.latitude*pi()/180)/2),2)" +
				"+cos("+lat+"*pi()/180)*cos(model.latitude*pi()/180)*pow(sin( ("+lng+
				"*pi()/180-model.longitude*pi()/180)/2),2)))*1000))";
		if(level!=null){
			queryString = queryString + " and model.level="+level;
		}
		queryString = queryString + " order by " + distance + "asc ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	/**
	 * 获取定位所在半径范围内的分部
	 * @param level
	 * @param lng
	 * @param lat
	 * @return
	 */
	private AdminUser getInAreaAdminUser(Integer level, double lng, double lat){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String distance = "(round(6378138*2*asin(sqrt(pow(sin( ("+lat+"*pi()/180-model.latitude*pi()/180)/2),2)" +
				"+cos("+lat+"*pi()/180)*cos(model.latitude*pi()/180)*pow(sin( ("+lng+
				"*pi()/180-model.longitude*pi()/180)/2),2)))*1000))";
		
		String queryString = "from AdminUser as model where model.isvalid = true and "+distance+" <= 10000";  //<=10公里
		if(level!=null){
			queryString = queryString + " and model.level="+level;
		}
		queryString = queryString + " order by " + distance + "asc ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	@Override
	public AdminUser getZoneAdminUser(Integer zoneId,Integer level){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true and model.level=:level and model.provinceEnum2.id = :zoneId"; 
		queryString = queryString + " order by id desc ";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("level", level);
		queryObject.setParameter("zoneId", zoneId);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	/**
	 * 获取定位所在半径范围内的分部
	 * @param level
	 * @param lng
	 * @param lat
	 * @return
	 */
	@Override
	public List<AdminUser> getAdminUserListByZones(Integer zoneId,double lng,double lat){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true and level=65 and model.provinceEnum.provinceEnum.id = :zoneId"; 
		if(lng>0&&lat>0){
			//order = "(2 * 6378.137* ASIN(SQRT(POW(SIN(PI()*("+latitude+"-seller.latitude)/360),2)+COS(PI()*33.07078170776367/180)* COS(seller.latitude * PI()/180)*POW(SIN(PI()*("+longitude+"-seller.longitude)/360),2))))";
			queryString = queryString + " order by (round(6378.138*2*asin(sqrt(pow(sin( ("+lat+"*pi()/180-model.latitude*pi()/180)/2),2)+cos("+lat+"*pi()/180)*cos(model.latitude*pi()/180)*pow(sin( ("+lng+"*pi()/180-model.longitude*pi()/180)/2),2)))*1000)) asc";
			
		}else{
			queryString = queryString + " order by id desc ";
		}
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("zoneId", zoneId);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		
		return list;
	}
	
	/**
	 * 获取定位所在半径范围内的分部
	 * @param level
	 * @param lng
	 * @param lat
	 * @return
	 */
	@Override
	public AdminUser getSellerAreaByZoneId(Integer zoneId,double lng,double lat){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true " +
				"and level = :level and model.provinceEnum2.id = :zoneId";
		if(lng>0&&lat>0){
			queryString = queryString
					+ " order by (round(6378138*2*asin(sqrt(pow(sin( (" + lat
					+ "*pi()/180-model.latitude*pi()/180)/2),2)+cos(" + lat
					+ "*pi()/180)*cos(model.latitude*pi()/180)*pow(sin( ("
					+ lng
					+ "*pi()/180-model.longitude*pi()/180)/2),2)))*1000)) asc";
		}else{
			queryString = queryString + " order by id desc ";
		}
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("level", StringUtil.ADVERTWO);
		queryObject.setParameter("zoneId", zoneId);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	/**
	 * 获取定位所在半径范围内的分部
	 * @param level
	 * @param lng
	 * @param lat
	 * @return
	 */
	@Override
	public AdminUser getSellerAreaByZoneId2(Integer zoneId,double lng,double lat){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true " +
				"and level = :level and model.provinceEnum2.id = :zoneId";
		if(lng>0&&lat>0){
			queryString = queryString
					+ " order by (round(6378138*2*asin(sqrt(pow(sin( (" + lat
					+ "*pi()/180-model.latitude*pi()/180)/2),2)+cos(" + lat
					+ "*pi()/180)*cos(model.latitude*pi()/180)*pow(sin( ("
					+ lng
					+ "*pi()/180-model.longitude*pi()/180)/2),2)))*1000)) asc";
		}else{
			queryString = queryString + " order by id desc ";
		}
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("level", StringUtil.ADVERTWO);
		queryObject.setParameter("zoneId", zoneId);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}
	
	
	/**
	 * 获取总部
	 * @param zoneId
	 * @param lng
	 * @param lat
	 * @return
	 */
	@Override
	public AdminUser getHeadquarters(){
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "from AdminUser as model where model.isvalid = true " +
				"and level = :level order by id desc";
		Query queryObject = session.createQuery(queryString);
		queryObject.setParameter("level", StringUtil.ADMIN);
		queryObject.setFirstResult(0);
		queryObject.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<AdminUser> list = queryObject.list();
		if(list.size()>0){
			adminUser = list.get(0);
		}
		return adminUser;
	}

	@Override
	public List<AdminUser> getLoginByLoginname(String loginname) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from AdminUser where isvalid = true and loginname=:loginname");
		query.setParameter("loginname", loginname);
		return query.list();
	}
	
	@Override
	public List<AdminUser> getAdminUserByInvitecode(String invitecode) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from AdminUser where isvalid = true and invitecode=:invitecode");
		query.setParameter("invitecode", invitecode);
		return query.list();
	}

	/**
	 * 是否拥有推广特产权限
	 */
	public boolean isNationwide(Integer roleId){
		Session session = sessionFactory.getCurrentSession();
		Object object = session.createSQLQuery("select permission_id from rerole_repermission where role_id="+roleId+" and permission_id=125").uniqueResult();
		if(object!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public AdminUser findByNameOrPhone(String presenterName) {
		AdminUser adminUser = null;
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select * from admin_user where isvalid=true and loginname ='"+presenterName+"'");
		query.addEntity(AdminUser.class);
		List<AdminUser> usersList = query.list();
		if (usersList.size()>0) {
			adminUser = usersList.get(0);
		}
		return adminUser;
	}
}
