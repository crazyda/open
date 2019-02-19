package com.axp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.axp.dao.ReGoodsofextendmallDao;
import com.axp.domain.ReGoodsofextendmall;
@Repository("reGoodsofextendmallDao")
public class ReGoodsofextendmallDaoImpl  extends BaseDaoImpl<ReGoodsofextendmall> implements ReGoodsofextendmallDao{

	/**
	 * 查询各地特产
	 */
	@Override
	public List<ReGoodsofextendmall> findByGdtc(int mall, String orderData,int typeId) {
		Session session = sessionFactory.getCurrentSession();
		String sql1 = "SELECT a.*,b.salesVolume,b.sales,b.transportationType"
					+"FROM re_goodsofextendmall as a  LEFT JOIN re_goodsoflocalspecialtymall as b" 
					+"ON a.goodsMall = b.goodsOrder WHERE";
		String sql2 ="";
		if(mall>0){
			
			sql2 = "a.mall = "+mall+"and";
		}
		String sql3 = "";
		if(typeId>0){
			sql3 = "Cid = "+typeId+"and";
		}
		String sql4 = "a.isvalid=1 and a.sign=1"
					+"AND a.isChecked =1 and b.isValid =1 ORDER BY"+orderData;
		
		String sql = sql1+sql2+sql3+sql4;
		Query query = session.createQuery(sql);
		return query.list();
	}

	/**
	 * 周边商城
	 */
	@Override
	public List<ReGoodsofextendmall> findByZbsc(int mall, String orderDatas,
			String zoneId,int typeId) {
		Session session = sessionFactory.getCurrentSession();
		String sql1 = "SELECT a.*,b.salesVolume,b.transportationType"
				+"FROM re_goodsofextendmall as a  LEFT JOIN re_goodsofsellermall as b"
				+"ON a.goodsMall = b.goodsOrder WHERE";
		
		String sql2 = "";
		if(mall>0){
			sql2 = "a.mall = "+mall+"and";
		}
		String sql3 = "";
		if(typeId>0){
			sql3 = "Cid = "+typeId+"and";
		}
		String sql4 = "a.zone_id="+zoneId+" AND a.isvalid=1"
				+"and  a.isChecked=1 and b.isValid=1  and a.sign=1"
				+"AND a.display=1 ORDER BY"+orderDatas;
		
		String sql = sql1+sql2+sql3+sql4;
		
		Query query = session.createQuery(sql);
		return query.list();
	}

}
