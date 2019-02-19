package com.axp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.axp.domain.ReBaseGoods;
import com.axp.domain.ReGoodsofextendmall;
import com.axp.service.ReGoodsofextendmallService;
import com.axp.util.CalcUtil;
import com.axp.util.QueryModel;
@Service("reGoodsofextendmallService")
public class ReGoodsofextendmallServiceImpl extends BaseServiceImpl<ReGoodsofextendmall> implements ReGoodsofextendmallService {

	
	/**
	 * 计算劵后价
	 * @param specifications
	 * @return
	 */
	public String  computeDeductPrice(List<Map<String, Object>> specifications,Double DeductPrice){
		
		List<Double> list=new ArrayList<>();
		String prices="";
		for (Map<String, Object> map : specifications) {
			double price = Double.parseDouble(map.get("price").toString());
			list.add(price);
		}
		Collections.sort(list);
		Double temp=0d;
		List<Double> list2=new ArrayList<>();
		for (Double double1 : list) {
			temp=CalcUtil.sub(double1,DeductPrice);
			list2.add(temp);
		}
		
		
		Set<Double> set=new HashSet<>();
		List<Double> newList = new  ArrayList(); 
		for (Double p : list2) {
			if(set.add(p)){
				newList.add(p);
			}
		}
		
		if(newList.size()>=1){
			prices= newList.get(0)+"-"+newList.get(newList.size()-1);
		}else{
			prices= newList.get(0).toString();
		}
		
		return prices;
		
	}
	
	/**
	 * 计算劵后价
	 */
	public Double  computeDeductPrice(ReBaseGoods reBaseGoods,Integer couponType){
	
		QueryModel model=new QueryModel();
		double discount=0d;
		model.combEquals("isValid", 1);
		model.combEquals("isChecked", 1);
		model.combEquals("goodsMall", reBaseGoods.getGoodsOrder());
		if(couponType!=null&&couponType==1){
			model.combCondition("isActivity=1");
			
		}else if(couponType!=null&&couponType==2){
			model.combCondition("isActivity=0 "); 
		}
		if(couponType!=null){
			 
			List<ReGoodsofextendmall> extendMallList =dateBaseDAO.findLists(ReGoodsofextendmall.class, model);
			if(extendMallList.size()>0){
			  discount=reBaseGoods.getPrice()-extendMallList.get(0).getTicketprice();
			}
		}
		
		if(discount<0){
			discount=0d;
		}
		
	  return discount;
	}
	
	
	
}
