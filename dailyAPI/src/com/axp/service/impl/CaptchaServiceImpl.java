package com.axp.service.impl;import java.util.Date;import java.util.HashMap;import java.util.List;import java.util.Map;import org.springframework.stereotype.Service;import com.alicom.dysms.api.AxpSendSms;import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;import com.aliyuncs.exceptions.ClientException;import com.axp.domain.Captcha;import com.axp.service.ICaptchaService;import com.axp.util.QueryModel;@Servicepublic class CaptchaServiceImpl extends BaseServiceImpl<Captcha> implements ICaptchaService {		@Override	public Map<String, Object> sendCaptcha(String phone) {		Map<String, Object> map = new HashMap<String, Object>();				QueryModel model=new QueryModel();		model.combEquals("phone", phone);		model.combEquals("isValid", 1);		model.setOrder("createTime desc");		List<Captcha> captChaList = dateBaseDAO.findLists(Captcha.class, model);		int random =0;// (int) ((Math.random() * 9 + 1) * 100000);				if(captChaList.size()>0){			long nowTime=new Date().getTime();			long creatTime  = captChaList.get(0).getCreatetime().getTime();			int result=(int) ((nowTime-creatTime)/1000);															//判断是否在一分钟内 重复请求			if(result<60){				map.put("status", -0x01);				map.put("message", "访问次数过于频繁,稍后再试");				return map;			}															//判断是否在30分钟内 有过记录 如果有 那就发送上次的验证码			if(result<1800){				random=Integer.parseInt(captChaList.get(0).getCode());			}else{				random= (int) ((Math.random() * 9 + 1) * 100000);			}		}else{			random= (int) ((Math.random() * 9 + 1) * 100000);		}								String content="{\"captcha\":\""+random+"\"}";		/*SingleSendSms app = new SingleSendSms();		String s = app.sendMsg(phone, content);*/		AxpSendSms axp = new AxpSendSms();		try {			SendSmsResponse response = axp.sendSms(phone, content);		    System.out.println("短信接口返回的数据----------------");//	        System.out.println("Code=" + response.getCode());//	        System.out.println("Message=" + response.getMessage());//	        System.out.println("RequestId=" + response.getRequestId());//	        System.out.println("BizId=" + response.getBizId());	        System.out.println("Code="+random);			if (response.getCode() != null && response.getCode().equals("ok")) {				map.put("status", -0x01);				map.put("message", "发送失败");			} else {				// 验证码的短信				Captcha captcha = new Captcha();				captcha.setPhone(phone);				captcha.setIsvalid(true);				captcha.setCreatetime(new java.sql.Timestamp(System						.currentTimeMillis()));				captcha.setCode(Integer.toString(random));				captchaDao.save(captcha);				map.put("status", 0x01);				map.put("message", "发送成功，请在30分钟内输入验证码");			}		} catch (ClientException e) {			e.printStackTrace();		}		return map;	}			public static void main(String[] args) {		/*SingleSendSms app = new SingleSendSms();		 app.sendMsg("17688843100","{\"captcha\":\"123123\"}");*/		/*UrlUtil urlUtil = new UrlUtil();		urlUtil.send("13710532349", "123123");*/		 		AxpSendSms axp = new AxpSendSms();		 try {			SendSmsResponse response = axp.sendSms("17688843100","{\"captcha\":\"123123\"}");					} catch (ClientException e) {			e.printStackTrace();		}	}	/* (non-Javadoc)	 * @see com.axp.service.ICaptchaService#checkCaptcha(java.lang.String, java.lang.String)	 */	@Override	public Map<String,Object> checkCaptcha(String phoneNumber, String captcha) {		//		Captcha captchaMoel = captchaDao.getCaptchaByPhone(phoneNumber);				Map<String, Object> map=new HashMap<String, Object>();		//如果等于空 说明验证码发送记录表中 没有这个手机号码		if(captchaMoel==null){			 map.put("status", -1);			 map.put("message", "无记录手机号");			 return map;		}else if(!captchaMoel.getCode().equals(captcha)){			 map.put("status", -2);			 map.put("message","验证码错误");			 return map;		}else{			 map.put("status", 1);			 map.put("message","验证成功");			 return map;		}			}}