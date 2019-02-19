package com.axp.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ReGoodsorderDao;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.Users;
@SuppressWarnings("unchecked")
@Repository 
public class ReGoodsorderDaoImpl extends BaseDaoImpl<ReGoodsorder> implements ReGoodsorderDao{

	@Override
	public void deleteCache(Integer userId) {
		StringBuffer sb = new StringBuffer("update ReGoodsorderItem set isValid=0 where user.id = "+userId+" and status = -1");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
		
	}
	public void deleteOrder(Integer userId){
		StringBuffer sb = new StringBuffer("update ReGoodsorder set isValid=0 where user.id = "+userId+" and status = -1 ");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());	
		createQuery.executeUpdate();
	}
	
	@Override
	public List<ReGoodsorder> getSessionOrders(Integer userId){
		StringBuffer sb = new StringBuffer("from ReGoodsorder where user.id =:userId and status = -1 and isValid=1 ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("userId",userId);
		return query.list();
	}
	
	@Override
	public List<ReGoodsorder> getOrders(String ids,Integer userId){
		StringBuffer sb = new StringBuffer("from ReGoodsorder where user.id =:userId and id in ("+ids+") ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("userId",userId);
		return query.list();
	}
	
	@Override
	public void updateSessionOrders(String ids,String status,String name,String phone,String address){
		StringBuffer sb = new StringBuffer("update ReGoodsorder set status = :status,realname = :name,phone = :phone,address = :address where id in ("+ids+")");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("status", Integer.parseInt(status));
		query.setParameter("name", name);
		query.setParameter("phone", phone);
		query.setParameter("address", address);
		query.executeUpdate();
	}
	
	@Override
	public void updateSessionOrderItems(String ids,String status){
		StringBuffer sb = new StringBuffer("update ReGoodsorderItem set status = "+status+" where order.id in ("+ids+")");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.executeUpdate();
	}
	
	@Override
	public void deletOrderCar(String ids){
		StringBuffer sb = new StringBuffer("select DISTINCT car.id from ReGoodsorderItem where order.id in ("+ids+")");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());
		List<Object> list = query.list();
		if(list.size()==0){
			return;
		}
		String cars = list.get(0).toString();
		StringBuffer sbCar = new StringBuffer("update ReShoppingCar set isValid = false where id in ("+cars+")");
		Query queryCar = session.createQuery(sbCar.toString());
		queryCar.executeUpdate();
	}
	
	@Override
	public List<ReGoodsorder> getToBePaidOrders(Integer userId){
		StringBuffer sb = new StringBuffer("from ReGoodsorder where user.id =:userId and status = 0 ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("userId",userId);
		return query.list();
	}
	
	@Override
	public List<ReGoodsorder> getToBePaidOrders(Integer userId,String ids){
		StringBuffer sb = new StringBuffer("from ReGoodsorder where user.id =:userId and id in ("+ids+") and status = 0 and isValid = true");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("userId",userId);
		return query.list();
	}
	
	@Override
	public void updateStatus(Integer status, Integer orderId) {
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery("update ReGoodsorder set status = "+status +" where id = "+orderId);
		createQuery.executeUpdate();		
	}
	
	@Override
	public Integer getCountByStatus(Integer sellerId,Integer status){
		StringBuffer sb = new StringBuffer("select count(id) from ReGoodsorder where seller.id =:sellerId and status = :status and isValid = true");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("sellerId",sellerId);
		query.setParameter("status",status);
		return Integer.parseInt(query.uniqueResult().toString());
	}
	@Override
	public void deleteById(Integer orderId) {
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery("update  ReGoodsorder set isValid=0 where id = "+orderId);
		createQuery.executeUpdate();		
	}
	@Override
	public List<Integer> getOrderIdsByItems(String string) {
		StringBuffer sb = new StringBuffer("select DISTINCT ReGoodsorderItem.order.id from ReGoodsorderItem where id in ("+string+")");
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery(sb.toString());
		return createQuery.list();
	}
	@Override
	public void updateIsHasItems(StringBuffer sb) {
		Session session = sessionFactory.getCurrentSession();
		Query createQuery = session.createQuery("update ReGoodsorder set isHasItems = false where id in ("+sb.toString()+")");
		createQuery.executeUpdate();	
	}
	@Override
	public Integer getCountBySellerId(Integer sellerId) {
		try{
		StringBuffer sb = new StringBuffer("select count(id) from ReGoodsorder where seller.id =:sellerId and status >9 and status<60 and isValid = true  and date(createTime) = curdate()");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("sellerId",sellerId);
		String restu=query.uniqueResult().toString()==null?"0":query.uniqueResult().toString();
		
		return Integer.parseInt(restu);
	}catch(Exception e){
		e.printStackTrace();
	}
	return 0;
	}

	@Override
	public Double getSumBySellerId(Integer sellerId) {
		try{
		StringBuffer sb = new StringBuffer("select sum(payPrice) from ReGoodsorder where seller.id =:sellerId  and status >9 and status<60 and isValid = true ");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("sellerId",sellerId);
		String restu=query.uniqueResult()==null?"0":query.uniqueResult().toString();
		return Double.parseDouble(restu);
	}catch(Exception e){
		e.printStackTrace();
	}
	return 0d;
	}
	
	@Override
	public Double getSumBySellerIdForToday(Integer sellerId) {
		try{
		StringBuffer sb = new StringBuffer("select sum(payPrice) from ReGoodsorder where seller.id =:sellerId and status >0 and isValid = true and date(createTime) = curdate()");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sb.toString());	
		query.setParameter("sellerId",sellerId);
		String restu=query.uniqueResult()==null?"0":query.uniqueResult().toString();
		return Double.parseDouble(restu);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0d;
	}
	
	/**
	 * 根据状态获取查询订单列表
	 */
	public Object[] findOrderListByStatus(Integer userId,Integer status,Integer index,Integer size,Integer appVersion,Integer wx_sellerId){
		Object[] result=new Object[2];
		Session session = sessionFactory.getCurrentSession();
		String sqlCount="select count(1) ";
		String sqlList="select o.* ";
		String order=" order by o.id desc, o.status asc  LIMIT ?,?";
		String group=" group by o.id";
		String sql=" from re_goodsorder o "
				+ "inner join re_goodsorder_item i on o.id=i.order_id"
				+ " where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541 and i.status = o.status and i.mall_class != 'nnm' and i.mall_id !=349";
			
		if(wx_sellerId!=null){
			sql+=" and o.seller_id="+wx_sellerId;
		}
		
		sql+=" and o.user_id=? and (select count(*) from re_goodsorder_item "
				+ "item where item.order_id=o.id and item.isback in(10,40)) >0";
		
		/* public static Integer huan_cun  = -1;//待支付
			public static Integer dai_zhi_fu  = 0;//待支付
			public static Integer dai_pin_tuan=5;  //待拼团
			public static Integer dai_que_ren  =10;//待确认
			public static Integer dai_fa_huo  =20;//待发货
			public static Integer dai_dui_huan  =25;//待兑换
		    public static Integer dai_shou_huo  =30;//待收货
		    public static Integer dai_ping_jia  =40;//已完成(待评价)
		    public static Integer yi_wan_cheng  =50;//已完成(已评价)
*/		
			
			if(status!=null&&status.intValue()==20){
				sql+="  and o.status in(10,20)";
			}else{
				sql+="  and o.status="+status;
			}
		
			sql+= group;
			SQLQuery sqlQuery=session.createSQLQuery(sqlCount+sql);
			sqlQuery.setParameter(0, userId);
			String count=String.valueOf(sqlQuery.list().size());
			if(count.equals("0")){
				return null;
			}
			sqlQuery=session.createSQLQuery(sqlList+sql+order);
			
			sqlQuery.setParameter(0, userId);
			sqlQuery.setParameter(1, (index-1)*size);
			sqlQuery.setParameter(2, size);
			sqlQuery.addEntity(ReGoodsorder.class);
			List<ReGoodsorder> list = sqlQuery.list();
			
			result[0]=count;
			result[1]=list;
			return result;
	}
	
	
	
	/**
	 * 查询订单四种状态 
	 * 1 待付款 2 待支付 3 待评价 4 退单售后
	 */
	public Object[] getOrderStatusNumByUserId(Users users ){
		Session session = sessionFactory.getCurrentSession();
		String sql="select ("
				+ " select   count(DISTINCT o.id )  from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id  "
				+ "where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?  "
				+ "and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349  "
				+ "and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0 "
				+ "and o.status =0 "
				+ ") as `待付款` , "
				+ "( select   count(DISTINCT o.id )  from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id  "
				+ "where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?  "
				+ "and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349  "
				+ "and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0 "
				+ "and o.status =10 ) as `待确认` , ( select   count(DISTINCT o.id ) "
				+ " from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id  "
				+ "where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?  "
				+ "and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349  "
				+ "and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0 "
				+ "and o.status =40 ) as `待评价` , ( select count(b.id) from re_back_order b, re_goodsorder_item i,re_goodsorder g  "
				+ "where b.orderItemId =i.id and g.id =i.order_id and b.user_id =?  "
				+ "and  i.mall_class!='nnm' and i.mall_id!=349  and   b.isValid =1 and i.isValid =1 "
				+ "and g.isValid =1 and ( b.backstate=10 or b.backstate=20) ) as '退单' ";
			SQLQuery sqlQuery=session.createSQLQuery(sql);
			sqlQuery.setParameter(0, users.getId());
			sqlQuery.setParameter(1, users.getId());
			sqlQuery.setParameter(2, users.getId());
			sqlQuery.setParameter(3, users.getId());
			return (Object[]) sqlQuery.uniqueResult();
		
	}
	
	/**
	 * 查询订单五种状态 
	 * 待付款 待分享 待发货 待收货 退单售后
	 */
	public Object[] getOrderStatusNumByUserIdNew(Users users ){
		Session session = sessionFactory.getCurrentSession();
		
		//待付款 待分享 待发货 待收货 退单售后
		
		String sql="select ("
				 +"select   count(DISTINCT o.id )  from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id"  
				+" where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?  "
				+" and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349  "
				+" and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0" 
				+" and o.status =0 "
				+" ) as '待付款' , "
                +" ( select   count(DISTINCT o.id )  from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id"  
				+" where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?"
				+" and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349 "  
				+" and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0" 
				+" and o.status =5 ) as '待分享' ,   "
				+" ( select   count(DISTINCT o.id )  from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id"  
				+" where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?"
				+" and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349 "  
				+" and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0" 
				+" and o.status in(10,20) ) as '待发货' ,"
				+" ( select   count(DISTINCT o.id ) "
				+" from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id"  
				+" where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?"  
				+" and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349 "  
				+" and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0" 
				+" and o.status in(30) ) as '待收货' ,"
                +" ( select count(b.id) from re_back_order b, re_goodsorder_item i,re_goodsorder g"  
				+" where b.orderItemId =i.id and g.id =i.order_id and b.user_id =?"  
				+" and  i.mall_class!='nnm' and i.mall_id!=349  AND i.mall_class != 'ldm' and   b.isValid =1 and i.isValid =1" 
				+" and g.isValid =1 and ( b.backstate=10 or b.backstate=20) ) as '退单',"
				+" ( select   count(DISTINCT o.id ) "
				+" from re_goodsorder o inner join re_goodsorder_item i on o.id=i.order_id"  
				+" where o.isValid=1 and o.isHasitems=1 and o.seller_id!=5541  and o.user_id=?"  
				+" and i.isback in(10,40) and i.status=o.status and  i.mall_class!='nnm' and i.mall_id!=349 "  
				+" and (select count(*) from re_goodsorder_item item where item.order_id=o.id and item.isback in(10,40)) >0" 
				+" and o.status in(40) ) as '待评价'";
				
			SQLQuery sqlQuery=session.createSQLQuery(sql);
			sqlQuery.setParameter(0, users.getId());
			sqlQuery.setParameter(1, users.getId());
			sqlQuery.setParameter(2, users.getId());
			sqlQuery.setParameter(3, users.getId());
			sqlQuery.setParameter(4, users.getId());
			sqlQuery.setParameter(5, users.getId());
			return (Object[]) sqlQuery.uniqueResult();
		
	}
	
	
	
	public List<ReGoodsorder> getReGoodsOrderTeamInfo(Integer goodsId,Integer appVersion){
		int size=appVersion>5100?5:10;
		
		String sql="select o.* from re_goodsorder o inner join"
				+ " re_goodsorder_item i on o.id=i.order_id "
				+ " where o.isvalid=1 and o.status=5 and "
				+ "o.teamOrderId is null and i.mall_Id= "+goodsId+""
						+ " LIMIT 0,"+size+""  ;
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		sqlQuery.addEntity(ReGoodsorder.class);
		
		return sqlQuery.list();
		
	}
	
	/**
	 * 拼团成功展示信息
	 * @param reGoodsorder
	 * @return
	 */
	public List<ReGoodsorder> getOrderTeamStatusInfo(ReGoodsorder reGoodsorder){
		List<ReGoodsorder> list=new ArrayList<>();
		/*boolean pinZhu=true;
		if(reGoodsorder.getReGoodsorder()!=null){
			pinZhu=false;
		}
		if(pinZhu){
			list.add(reGoodsorder);
		}else{
			list.add(reGoodsorder.getReGoodsorder());
			list.add(reGoodsorder);
		}
		//拼主在第一个
		return list;*/
		
		ReGoodsorder pinZhu=null;
		
		if(reGoodsorder.getReGoodsorder()==null){ //拼员
			pinZhu=reGoodsorder;
		}else{
			pinZhu=reGoodsorder.getReGoodsorder();
		}
		
		list.add(pinZhu);				      
		 List<ReGoodsorder> listOrder = this.findByPropertyIsValid("reGoodsorder.id", pinZhu.getId());
		 
		 if(listOrder.size()>0){
			 list.add(listOrder.get(listOrder.size()-1));
		 }
		return list;
	}
	@Override
	public List<ReGoodsorder> findByScoreRegression() {
		String sql = "SELECT * FROM re_goodsorder where isValid =1 AND `STATUS` <>70 AND  id IN ( "+
					 "SELECT i.order_id FROM re_goodsorder_item AS i WHERE 1=1 "+
					 "AND  `STATUS` >0 AND isValid=1 AND isLock is NULL AND good_id IN("+
					 "SELECT id FROM re_goodsoflockmall WHERE 1=1 AND endTime < NOW() "+
					 "and isValid =1 and isChecked =1 and (openYards !=1 or openYards is NULL) and gameType=265))";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery sqlQuery=session.createSQLQuery(sql);
		sqlQuery.addEntity(ReGoodsorder.class);
		
		return sqlQuery.list();
	}
	
	
	
	
}
