package com.axp.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.axp.domain.FreeVoucher;
import com.axp.domain.FreeVoucherRecord;
import com.axp.domain.Users;
import com.axp.service.IFreeVoucherService;
import com.axp.util.DateUtil;
import com.axp.util.QueryModel;

@Service
public class FreeVoucherServiceImpl extends BaseServiceImpl<FreeVoucherRecord> implements IFreeVoucherService{
	
	@Override
	public void addMembersVoucher(Users user,Integer type,Integer count) {
		if(user==null)
			return;
		QueryModel model = new QueryModel().setOrder("id desc");
		model.combPreEquals("isValid", true);
		model.combPreEquals("type", type);
		List<FreeVoucher> voucherList = dateBaseDAO.findLists(FreeVoucher.class, model);
		if(voucherList.size()==0)
			return;
		Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		FreeVoucher voucher = voucherList.get(0);
		Integer recordCount = freeVoucherRecordDao.getCountByUserId(user.getId());
		if(recordCount > 0){
			return;
		}
		try{
			for(int i=0;i<count;i++){
				String startTime = DateUtil.formatDate("yyyy-MM-dd",DateUtil.getNow());
				String endTime = DateUtil.formatDate("yyyy-MM-dd",DateUtil.addDay2Date(voucher.getDays(),DateUtil.getNow()));
				FreeVoucherRecord record = new FreeVoucherRecord();
				record.setCreateTime(time);
				record.setEndTime(Timestamp.valueOf(endTime+" 00:00:00"));
				record.setFreeVoucher(voucher);
				record.setIsValid(true);
				record.setStartTime(Timestamp.valueOf(startTime+" 00:00:00"));
				record.setUsers(user);
				record.setStatus(0);//未使用
				super.save(record);
			}
			System.out.println("会员免单券发放成功");
		}catch(Exception e){
			System.out.println("会员免单券发放出错");
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
}