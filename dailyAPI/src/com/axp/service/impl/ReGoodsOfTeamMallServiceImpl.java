package com.axp.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.axp.domain.ReGoodsOfBase;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsOfTeamMall;
import com.axp.domain.ReGoodsSnapshot;
import com.axp.domain.Seller;
import com.axp.service.ReGoodsOfTeamMallService;

@Service
public class ReGoodsOfTeamMallServiceImpl extends BaseServiceImpl<ReGoodsOfTeamMall> implements ReGoodsOfTeamMallService {

	
	

	@Override
	public ReGoodsOfTeamMall buildReGoodsOfTeamMall(ReGoodsOfTeamMall teamMall2,String name,
			ReGoodsOfBase baseGoods, ReGoodsOfSellerMall reGoodsOfSellerMall,
			Integer teamNum, Integer transportationType,
			Double transportationPrice, Integer repertory, String startTime,
			String endTime, Seller seller, Boolean isChecked,
			ReGoodsSnapshot reGoodsSnapshot, Boolean isRestrict,
			Integer restrictNum, Double discountPrice) {
		ReGoodsOfTeamMall teamMall=teamMall2;
		teamMall.setIsValid(true);
		teamMall.setCreateTime(new Timestamp(System.currentTimeMillis()));
		teamMall.setName(name);
		teamMall.setBaseGoodsId(baseGoods.getId());
		teamMall.setCoverPic(baseGoods.getCoverPic());
		teamMall.setDescriptionPics(baseGoods.getDescriptionPics());
		teamMall.setReGoodsOfSellerMall(reGoodsOfSellerMall);
		teamMall.setTeamNum(teamNum);
		teamMall.setNoStandardPrice(reGoodsOfSellerMall.getNoStandardPrice());
		teamMall.setTransportationType(reGoodsOfSellerMall.getTransportationType());
		teamMall.setTransportationPrice(reGoodsOfSellerMall.getTransportationPrice());
		teamMall.setReleaseNum(repertory);
		teamMall.setDiscountPrice(discountPrice);
		teamMall.setDisplayPrice(reGoodsOfSellerMall.getDisplayPrice());
		teamMall.setStandardDetails(reGoodsOfSellerMall.getStandardDetails());
		teamMall.setRightsProtect(reGoodsOfSellerMall.getRightsProtect());
		teamMall.setIsNoStandard(reGoodsOfSellerMall.getIsNoStandard());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		Date d2;
		try {
			d = sf.parse(startTime);
			d2= sf.parse(endTime);
			teamMall.setAddedTime(new Timestamp(d.getTime()));
			teamMall.setShelvesTime(new Timestamp(d2.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		teamMall.setSeller(seller);
		teamMall.setIsChecked(isChecked);
		teamMall.setSnapshotGoods(reGoodsSnapshot);
		teamMall.setIsRestrict(isRestrict);
		teamMall.setRestrictNum(restrictNum);
	return teamMall;
	}
	
}
