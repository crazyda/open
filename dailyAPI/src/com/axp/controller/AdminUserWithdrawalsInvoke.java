package com.axp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("invoke/sellers")
public class AdminUserWithdrawalsInvoke extends BaseController {
	/*
	 * 个人提现资料申请
	 * */
    @RequestMapping("/Apply")
    @ResponseBody
    public Map<String, Object> withdrawalsApply (HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsDataService.withdrawApply(request, response);
    }
    
    
    /*
	 * 提现申请界面
	 * */
    @RequestMapping("/WithdrawApplyInfo")
    @ResponseBody
    public Map<String, Object> withdrawApplyInfo (HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsDataService.withdrawApplyInfo(request, response);
    }
    /*
     * 提现资料获取
     * */
    @RequestMapping("/getWithdrawals")
    @ResponseBody
    public Map<String, Object> getWithdrawalsInfo (HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsDataService.getWithdrawals(request, response);
    }
    /*
     * 银行卡信息提交
     * */
    @RequestMapping("/commitBank")
    @ResponseBody
    public Map<String, Object> commitBankInfo (HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsBankService.commitBankInfo(request, response);
    }
    /*
     * 银行卡信息获取
     * */
    @RequestMapping("/getBank")
    @ResponseBody
    public Map<String, Object> getBankInfo(HttpServletRequest request,HttpServletResponse response ){
    	return adminuserWithdrawalsBankService.getBankInfo(request, response);
    }
    /*
     * 删除银行卡
     * */
    @RequestMapping("/delBankById")
    @ResponseBody
    public Map<String, Object> delBankInfoById(HttpServletRequest request,HttpServletResponse response){
		return adminuserWithdrawalsBankService.delBankInfoById(request, response);
    }
    /*
     * 修改银行卡信息
     * */
    @RequestMapping("/updateBankById")
    @ResponseBody
    public Map<String, Object> updateBankInfoById(HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsBankService.updataBankInfoById(request, response);
    }
    /*
     * 提现申请
     * */
    @RequestMapping("/withdrawalApply")
    @ResponseBody
    public Map<String, Object> withdrawApply(HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsService.withdrawApply(request, response);
    }
    /*
     * 提现申请页面数据
     * */
    @RequestMapping("/getwithdrawalApply")
    @ResponseBody
    public Map<String, Object> getwithdrawalApply(HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsService.getwithdrawalApply(request, response);
    }
    
    /*
     * 提现申请页面数据
     * 
     * */
    @RequestMapping("/getwithdrawalApplyForPay")
    @ResponseBody
    public Map<String, Object> getwithdrawalApplyForPay(HttpServletRequest request,HttpServletResponse response){
    	return adminuserWithdrawalsService.getwithdrawalApplyForPay(request, response);
    }
}
