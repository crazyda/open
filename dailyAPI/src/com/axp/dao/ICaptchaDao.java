package com.axp.dao;import java.util.List;import java.util.Map;import com.axp.domain.Captcha;public interface ICaptchaDao extends IBaseDao<Captcha> {	/**	 * 根据手机号码寻找Captcha对象；	 * @param phoneNumber 查询的手机号；	 * @return 返回Captcha的对象 是按日期时间排序的最近结果；	 */	Captcha getCaptchaByPhone(String phoneNumber);	/**	 * 验证用户的电话号码和验证码是否匹配；	 * @param phoneNumber 待验证电话；	 * @param captcha 待验证验证码；	 * @return 返回是否验证成功；	 */	Map<String, Object> checkCaptcha(String phoneNumber, String captcha);	Integer getTodaySendTimes(String phone);		List<Captcha> checkCaptchByPhone(String phone,String captcha);}