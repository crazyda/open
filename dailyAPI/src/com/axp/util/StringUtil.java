package com.axp.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.axp.dao.DateBaseDAO;
import com.axp.domain.ReGoodsOfSellerMall;
import com.axp.domain.ReGoodsorderItem;
import com.axp.domain.ReShoppingCar;
import com.axp.domain.Seller;
import com.axp.domain.Users;
import com.vdurmont.emoji.EmojiParser;

public class StringUtil {
	public final static String url = "http://jifen.aixiaoping.cn:8080/dailyRes";
	
	
	//分享出去卡片的小图片
	public final static String shareImg = url+"/cashshop_type/1/nomal/351390922043149200.jpg";
	//微信小程序的appId 
	public final static String appId = "wxdc32f5850693caa9";
	
	public final static String secret = "7a148923bb9a4b57b3c4bdbe17562804";
	
	//百度地图appid
	public final static String ak = "EalGGkvau6k05uQL1MeRk11v2QHdioz9"; //百度AK 
	
	public final static String mcode = "55:95:95:79:CC:D0:38:89:7B:DB:70:93:6E:7E:5C:D8:FC:3B:E5:9F;com.axp.axpseller";
	// 统计字符串
	public static String gzhImg = "http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/4163060429030025200.jpg";
	
	public static String RealPath;
	
	private static Hashtable<String,String> faceHash;
	
	
	
	public final static String CCP_SERVER_IP = "app.cloopen.com";

	public final static String CCP_SERVER_PORT = "8883";
	
	public final static String CCP_ACCOUNT_SID = "aaf98f894ac24cc7014ac38db07500fd";
	
	public final static String CCP_ACCOUNT_TOKEN = "209638136986426ebc5b8599e55bf71b";
	
	public final static String CCP_APP_ID = "8a48b5514ac24e1b014ac390be570136";
	
	public final static String CCP_API_CALLBACK_MAKE = "Calls/Callback";
	
	public final static String CCP_API_CALL_CALCEL = "Calls/CallCancel";
	
	/**补充真实姓名*/
	public final static String KEY_REPLENISH_REAL_NAME = "keyReplenishRealName";
	/**补充电话号码*/
	public final static String KEY_REPLENISH_PHONE_NUMBER = "keyReplenishPhoneNumber";
	/**补充地址*/
	public final static String KEY_REPLENISH_ADDRESS = "keyReplenishAddress";
	/**补充出生日期*/
	public final static String KEY_REPLENISH_BIRTHDAY = "keyReplenishBirthday";
	/**补充性别*/
	public final static String KEY_REPLENISH_SEX = "keyReplenishSex";
	/**网络通话*/
	public final static String KEY_PHONE_SPEND_SCORE = "keyPhoneSpendScore";
	
	//默认用户头像
	public final static  String userHead="/cashshop_type/1/nomal/1333781106081350300.png";
	//谢谢参与
	public final static String darwXXCY = "cashshop_type/1/nomal/3419600107122751400.png";
	//积分奖品
	public final static String darwScore = "cashshop_type/1/nomal/3419590117090011000.png";
	/**商家默认头像*/
	public final static  String sellerHead="http://jifen.aixiaoping.cn:8080/dailyRes/cashshop_type/1/nomal/4163060429030025200.jpg";
	/**粉丝群组头像*/
	public final static  String fansGroupHead="http://jph.aixiaoping.cn:8080/jupinhuiRes/upload-res/message_icon/1/nomal/6200881211100906819.png";
	
	public final static String advertDefaultImg="http://jph.aixiaoping.cn:8080/jupinhuiRes/upload-res/message_icon/1/nomal/6566810202042629819.png";
	
	/**默认父级规格*/
	public final static Integer parentStandardId=1;
	/**默认父级规格名*/
	public final static String parentStandardName="规格";
	
	/**
	 * 保存用户最新的分数增加的日期
	 */
	private static Hashtable<Integer,String> userScoreDayHash;
	
	/**
	 * 保存用户的实时的分数
	 */
	private static Hashtable<Integer,Integer> userStoreScoresHash;
	
	
	public final static String signDatil = "1.点击上方“立即签到”按钮完成签到哦!\n2.每日签到可获得1积分的奖励，连续完成8天签到可额外获得奖励，连续签到满8天后将会重置签到记录。";
	
	/**
	 * 保存字母的顺序，用于按字母检索楼盘
	 */
	private static Hashtable<String,Integer> spellcharHash = null;
	private static Hashtable<String,String> levelHash = null;
	private static Hashtable<String,String> superadmin =null;
	private static Hashtable<String,String> admin =null;
	private static Hashtable<String,String> giftcenter =null;
	private static Hashtable<String,String> advercenter =null;
	private static Hashtable<String,String> giftporxyone =null;
	private static Hashtable<String,String> adverporxyone =null;
	private static Hashtable<String,String> addscore =null;
	private static Hashtable<String,String> offscore =null;
	private static Hashtable<String,String> position = null;
	private static Hashtable<String,Integer> quantity = null;
	private static Hashtable<Integer,String> type =null;
	private static Hashtable<Integer,String> status;
	private static Hashtable<Integer,String> adver_type;
	private static Hashtable<Integer,String> max_str;
	private static Hashtable<Integer,String> machine_show_type;
	private static Hashtable<Integer,String> all_adver_type;
	private static Hashtable<Integer,String> plays_type;
	private static Hashtable<Integer,String> gift_show;
	private static Hashtable<Integer,String> quantityHash = null;
	private static Hashtable<Integer,String> paymentHash = null;
	private static Hashtable<Integer,String> mallTypeHash = null;
	private static Hashtable<Integer,String> freeVoucherHash = null;
	
	private static int sellerAdminLevel = 62;
	
	public final static String TAOKEURI = "http://www.518wtk.com";
	public final static String TAOKEPID = "mm_111685502_17728608_64252042";
	
	/**
	 * 拼多多信息
	 */
	public final static String pdd_client_id = "68bf2d0655473ec3ff6670394fbf5f26";
	public final static String pdd_client_screct = "bcffc94d10b6d4f9913af7c52a88a130";
	public final static String pdd_pid = "1001389_14247709";
	
	/**
	 * da  各层级代理的分佣比例
	 */
	public final static double dlfy = 0.1;
	public final static double sjfy = 0.2;
	public final static double fy = 0.4;
	
	/**
	 * 默认事业合伙人PID
	 */
	public final static String 	CAREERPARTNER="mm_111685502_31344923_117108452";
	
	/**
	 * 订单红包触发几率
	 */
	public final static int redPacketOdds=50;
	public final static Integer MESSAGE_FENYONG = 9;
	public final static Integer MESSAGE_TUIKUAN = 7;
	public final static Integer MESSAGE_CHONGZHI = 8;
	public final static Integer MESSAGE_TIXIAN = 6;
	public final static Integer MESSAGE_DINGDAN = 5;
	public final static Integer MESSAGE_PINGTAIZIXUN = 4;
	
	/**久久人后台id*/
	public final static int JJRID = 325;
	
	/**
	 * 代金券记录类型
	 */
	private static Hashtable<String,String> cashpointRecordHash = null;

	public static String fenyong = "/cashshop_type/1/nomal/3861570319093742300.jpg";

	//下载链接
	public static String targetUrl = "http://mtjf.518wk.cn:8080/dailyAPI/invoke/users/download";

	
	
	public static Hashtable<Integer, String> getGift_show() {
		if(gift_show == null){
			gift_show = new Hashtable<Integer,String>();
			gift_show.put(5,"全民抢拍");
			gift_show.put(4,"今日特惠");
			gift_show.put(3,"猜你喜欢");
			gift_show.put(2,"值得兑换");
			gift_show.put(1,"推荐兑换");
		}
		return gift_show;
	}


	/**
	 * 获取用户默认头像
	 * @param users
	 * @param basePath
	 * @return
	 */
	public static String getUserDefaultHead(Users users,String basePath){
		String imgUrl =StringUtils.isBlank(users.getImgUrl())?users.getHeadimage():(basePath+users.getImgUrl());
		if(StringUtils.isBlank(imgUrl)||(StringUtils.isNotBlank(imgUrl)&&imgUrl.length()<=49)){ 
			imgUrl=basePath + StringUtil.userHead;
		}
		return imgUrl;
		
	}
	public static String getUserDefaultHead(Seller seller,String basePath){
		String imgUrl =StringUtils.isBlank(seller.getImgUrl())?seller.getHeadImg():(basePath+seller.getImgUrl());
		if(StringUtils.isBlank(imgUrl)||(StringUtils.isNotBlank(imgUrl)&&imgUrl.length()<=49)){ 
			imgUrl=StringUtil.userHead;
		}
		return imgUrl;
		
	}
	public static String getUserDefaultName(Users users){
		String name="";
		if(StringUtils.isNotBlank(users.getRealname())){
			name=users.getRealname();
		}else{
			name=users.getName();
		}
			return name;
	}
	
	
	public static void setGift_show(Hashtable<Integer, String> gift_show) {
		StringUtil.gift_show = gift_show;
	}
	
	/**
	 * 判断字符串是否有长度；
	 * 1，不是为空；
	 * 2，有长度；
	 */
	public static Boolean hasLength(String s) {
		return s != null && s.length() > 0;
	}
	
	public static String hasValue(String s){
		if(s!=null && !"undefined".equals(s) && s !=""){
			
			return s;
		}else{
			return "";
		}
	}
	
	public static Hashtable<Integer,String> getAll_adver_type() {
		if(all_adver_type == null){
			all_adver_type = new Hashtable<Integer,String>();
			all_adver_type.put(1,"广告机二维码广告");
			all_adver_type.put(2,"广告机二维码分销");
			all_adver_type.put(3,"广告机图片广告");
			all_adver_type.put(4,"广告机全屏图片广告");
			all_adver_type.put(5,"滑屏图片广告");
			all_adver_type.put(6,"积分商城图片广告");
			all_adver_type.put(7,"个人中心图片广告");
			all_adver_type.put(8,"商家联盟图片广告");
			all_adver_type.put(9,"十秒视频广告");
			all_adver_type.put(10,"二十秒视频广告");
			all_adver_type.put(11,"三十秒视频广告");
			all_adver_type.put(12,"四十五秒视频广告");
			all_adver_type.put(13,"六十秒视频广告");
			all_adver_type.put(14,"九十秒视频广告");
			all_adver_type.put(15,"礼品");
			all_adver_type.put(16,"分享");
			all_adver_type.put(17,"视频展播");
			all_adver_type.put(18,"游戏");
			
		}
		return all_adver_type;
	}
	
	public static Hashtable<Integer, String> getPlays_type() {
		if(plays_type == null){
			plays_type = new Hashtable<Integer,String>();
			plays_type.put(1, "分享");
			plays_type.put(2, "视频");
			plays_type.put(3,"游戏");
		}
		return plays_type;
	}

	public static void setPlays_type(Hashtable<Integer, String> plays_type) {
		StringUtil.plays_type = plays_type;
	}

	public static void setAll_adver_type(Hashtable<Integer,String> all_adver_type) {
		StringUtil.all_adver_type = all_adver_type;
	}
	
	public static Hashtable<Integer,String> getMachine_show_type() {
		if(machine_show_type == null){
			machine_show_type = new Hashtable<Integer,String>();
			machine_show_type.put(0, "广告横屏");
			machine_show_type.put(1, "广告竖屏");
			machine_show_type.put(2,"广告机横屏");
			machine_show_type.put(3,"广告单屏");
			//machine_show_type.put(4,"广告全屏");
		}
		return machine_show_type;
	}
	
	public static void setMachine_show_typee(Hashtable<Integer,String> machine_show_type) {
		StringUtil.machine_show_type = machine_show_type;
	}
	
	
	public static Hashtable<Integer, String> getMax_str() {
		
		if(max_str == null){
			max_str = new Hashtable<Integer,String>();
			max_str.put(1, "000000");
			max_str.put(10, "00000");
			max_str.put(100,"0000");
			max_str.put(1000,"000");
			max_str.put(10000,"00");
			max_str.put(100000,"0");
			
		}
		return max_str;
	}

	public static void setMax_str(Hashtable<Integer, String> max_str) {
		StringUtil.max_str = max_str;
	}

	public static Hashtable<Integer,String> getAdver_type() {
		if(adver_type == null){
			adver_type = new Hashtable<Integer,String>();
			adver_type.put(1, "底图");
			adver_type.put(2, "二维码");
			adver_type.put(3,"视频");
			adver_type.put(4,"广告");
			adver_type.put(5,"全屏广告");
			
		}
		return adver_type;
	}
	
	public static void setAdver_type(Hashtable<Integer,String> adver_type) {
		StringUtil.adver_type = adver_type;
	}
	
	
	public static Hashtable<Integer,String> getStatus() {
		if(status == null){
			status = new Hashtable<Integer,String>();
			status.put(0, "待审核");
			status.put(-1, "不通过");
			status.put(1, "审核通过");
			status.put(2,"待播");
			status.put(3,"播放中");
			status.put(4,"已播");
			status.put(9,"禁播");
		}
		return status;
	}
	
	public static void setStatus(Hashtable<Integer,String> status) {
		StringUtil.status = status;
	}
	
	
	public static Hashtable<Integer,String> getType() {
		if(type == null){
			type = new Hashtable<Integer,String>();
			type.put(1, "首页顶部");
			type.put(2,"特产速递");
			type.put(3,"久久特惠顶部");
			type.put(4,"久久特惠中部");
			type.put(5,"商家详情");
			type.put(6,"会员横幅广告");
		}
		return type;
	}
	
	
	public static Hashtable<String, String> getAddscore() {
		if(addscore == null){
			addscore = new Hashtable<String,String>();
			addscore.put("referee", "邀请推荐");
			addscore.put("register", "注册");
			addscore.put("huaping", "滑屏");
			addscore.put("guanwang","访问官网");
			addscore.put("add","后台加分");
			addscore.put(KEY_REPLENISH_REAL_NAME, "补充真实姓名");
			addscore.put(KEY_REPLENISH_PHONE_NUMBER, "补充电话号码");
			addscore.put(KEY_REPLENISH_ADDRESS, "补充地址");
			addscore.put(KEY_REPLENISH_BIRTHDAY, "补充出生日期");
			addscore.put(KEY_REPLENISH_SEX, "补充性别");
			addscore.put(KEY_PHONE_SPEND_SCORE, "网络通话");
		}
		return addscore;
	}


	public static void setAddscore(Hashtable<String, String> addscore) {
		StringUtil.addscore = addscore;
	}


	public static Hashtable<String, String> getOffscore() {
		if(offscore == null){
			offscore = new Hashtable<String,String>();
			offscore.put("changer", "兑换礼品");
		}
		return offscore;
	}


	public static void setOffscore(Hashtable<String, String> offscore) {
		StringUtil.offscore = offscore;
	}


	public static Hashtable<String, String> getSuperadmin() {
		if(superadmin == null){
			superadmin = new Hashtable<String,String>();
			superadmin.put("99", "超级管理员");
			superadmin.put("95", "管理员");
		
		}
		return superadmin;
	}


	public static void setSuperadmin(Hashtable<String, String> superadmin) {
		
		StringUtil.superadmin = superadmin;
	}


	public static Hashtable<String, String> getAdmin() {
		if(admin == null){
			admin = new Hashtable<String,String>();
			//admin.put("90", "礼品运营中心");
			admin.put("94", "审核专员");
			admin.put("93", "管理员");
			admin.put("92", "财务");
			admin.put("85","运营中心");
			admin.put("60","供应商");
		}
		return admin;
	}


	public static void setAdmin(Hashtable<String, String> admin) {
		StringUtil.admin = admin;
	}


	public static Hashtable<String, String> getGiftcenter() {
		if(giftcenter == null){
			giftcenter = new Hashtable<String,String>();
		
			//giftcenter.put("80","礼品市级代理");
			giftcenter.put("75","城市代理");
		}
		return giftcenter;
	}


	public static void setGiftcenter(Hashtable<String, String> giftcenter) {
		StringUtil.giftcenter = giftcenter;
	}


	public static Hashtable<String, String> getAdvercenter() {
		if(advercenter == null){
			advercenter = new Hashtable<String,String>();
			
			advercenter.put("75","城市代理");
			advercenter.put("60","供应商");
		}
		return advercenter;
	}


	public static void setAdvercenter(Hashtable<String, String> advercenter) {
		StringUtil.advercenter = advercenter;
	}


	public static Hashtable<String, String> getGiftporxyone() {
		if(giftporxyone == null){
			giftporxyone = new Hashtable<String,String>();
			//giftporxyone.put("70","礼品联盟商家");
			giftporxyone.put("65","联盟组");
		}
		return giftporxyone;
	}


	public static void setGiftporxyone(Hashtable<String, String> giftporxyone) {
		StringUtil.giftporxyone = giftporxyone;
	}


	public static Hashtable<String, String> getAdverporxyone() {
		if(adverporxyone == null){
			adverporxyone = new Hashtable<String,String>();
			
			adverporxyone.put("65","联盟组");
		}
		return adverporxyone;
	}


	public static void setAdverporxyone(Hashtable<String, String> adverporxyone) {
		StringUtil.adverporxyone = adverporxyone;
	}

	public static final int SUPERADMIN =99;
	public static final int ADMIN =95; //总部
	public static final int ASSESSOR =94;		//审核专员
	public static final int MANAGER =93;		//管理员
	public static final int TREASURER =92;	//财务
	public static final int GIFTCENTER=90;
	public static final int ADVERCENTER=85;//运营中心
	public static final int GIFTONE =80;
	public static final int ADVERONE =75;//城市代理
	public static final int GIFTTWO =70;
	public static final int ADVERTWO =65;//联盟组（商圈）
	public static final int SUPPLIER = 60;//供应商
	
	public static final int CATEGORY_QR =2;
	public static final int CATEGORY_AD =4;
	public static final int CATEGORY_MV =3;
	public static final int CATEGORY_PH =5;
	
	public static final int OPEROTION_DELETE =0;
	public static final int OPEROTION_ADD =1;
	public static final int OPEROTION_UPDATE =2;
	
	public static final int referee4 = 4;
	public static final int referee3 = 3;
	public static final int referee2 = 2;
	public static final int referee1 = 1;
	
	
	public static Hashtable<String,String > getLevelsHash(){
		if(levelHash == null){
			levelHash = new Hashtable<String,String>();
			levelHash.put("99", "超级管理员");
			levelHash.put("95", "平台管理员");
			levelHash.put("94", "审核专员");
			levelHash.put("93", "管理员");
			levelHash.put("92", "财务");
		//	levelHash.put("90", "礼品运营中心");
		//	levelHash.put("85","广告运营中心");
			levelHash.put("85","运营中心");
		//	levelHash.put("80","礼品市级代理");
		//	levelHash.put("75","广告市级代理");
			levelHash.put("75","市级代理");
		//	levelHash.put("70","礼品联盟商");
		//	levelHash.put("65","广告联盟商");
			levelHash.put("65","联盟组");
			levelHash.put("60","供应商");
		}
		return levelHash;
	}
	
	public static Hashtable<String,String > getCashpointRecordHash(){
		if(cashpointRecordHash == null){
			levelHash = new Hashtable<String,String>();
			levelHash.put("1", "充值代金券");
			levelHash.put("2", "在新支付");
			levelHash.put("3", "推广收益");
			levelHash.put("4", "红包");
			levelHash.put("5", "商家代金券提现");
			levelHash.put("6", "注册收益");
			levelHash.put("7", "直接邀请收益");
			levelHash.put("8", "间接邀请收益");
			levelHash.put("9", "退订返还");
			levelHash.put("10", "发放红包");
			levelHash.put("11", "获得赞助红包");
			levelHash.put("12", "兑换返现");
		}
		return levelHash;
	}
	
	public static Hashtable<Integer,String > getFreeVoucherHash(){
		if(freeVoucherHash == null){
			freeVoucherHash = new Hashtable<Integer,String>();
			freeVoucherHash.put(1, "免单专区免单券");
		}
		return freeVoucherHash;
	}
	
	public static Hashtable<Integer,String > getQuantityHash(){
		if(quantityHash == null){
			quantityHash = new Hashtable<Integer,String>();
			quantityHash.put(0, "广告天数");
			quantityHash.put(95, "平台广告天数");
			quantityHash.put(85,"运营中心广告天数");
			quantityHash.put(75,"城市代理广告天数");
			quantityHash.put(65,"联盟组广告天数");
		}
		return quantityHash;
	}
	
	
	public static void setLevelHash(Hashtable<String, String> levelHash) {
		StringUtil.levelHash = levelHash;
	}
	
	public static Hashtable<String, Integer> getSpellcharHash() {
		if(spellcharHash==null){
			spellcharHash = new Hashtable<String,Integer>();
			spellcharHash.put("a", 1);
			spellcharHash.put("b", 1);
			spellcharHash.put("c", 1);
			spellcharHash.put("d", 1);
			
			spellcharHash.put("e", 2);
			spellcharHash.put("f", 2);
			spellcharHash.put("g", 2);
			spellcharHash.put("h", 2);
			
			spellcharHash.put("i", 3);
			spellcharHash.put("j", 3);
			spellcharHash.put("k", 3);
			spellcharHash.put("l", 3);
			spellcharHash.put("m", 3);
			spellcharHash.put("n", 3);
			
			
			spellcharHash.put("o", 4);
			spellcharHash.put("p", 4);
			spellcharHash.put("q", 4);
			spellcharHash.put("r", 4);
			spellcharHash.put("s", 4);
			spellcharHash.put("t", 4);
			spellcharHash.put("u", 4);
			spellcharHash.put("v", 4);
			
			spellcharHash.put("w", 5);
			spellcharHash.put("x", 5);
			spellcharHash.put("y", 5);
			spellcharHash.put("z", 5);
		}
		return spellcharHash;
	}
	
	public static Hashtable<Integer, String> getMallTypeHash(){
		if(mallTypeHash==null){
			mallTypeHash = new Hashtable<Integer, String>();
			mallTypeHash.put(0, "城市精品");
			mallTypeHash.put(1, "促销特惠");
			mallTypeHash.put(2, "会员商城");
			mallTypeHash.put(3, "秒杀专区");
			mallTypeHash.put(4, "名店优品");
			mallTypeHash.put(5, "会员免单区");
			mallTypeHash.put(6, "会员商城置顶区");
		}
		return mallTypeHash;
	}
	
	public static Hashtable<Integer, String> getPaymentHash(){
		if(paymentHash==null){
			paymentHash = new Hashtable<Integer, String>();
			paymentHash.put(0, "全红包");
			paymentHash.put(1, "红包加现金");
			paymentHash.put(2, "现金");
		}
		return paymentHash;
	}

	public static void setSpellcharHash(Hashtable<String, Integer> spellcharHash) {
		StringUtil.spellcharHash = spellcharHash;
	}

	public static int getCount(String sresoue, String ssub) {
		int count = 0;
		Matcher matcher = Pattern.compile(ssub).matcher(sresoue);
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	// 取字符串
	public static String[] getStringsub(String org, String sub) {
		String[] a = new String[2];
		int of = org.indexOf(sub);
		String subs = org.substring(0, of);
		a[0] = subs;
		String resubs = org.substring(of + sub.length(), org.length());
		a[1] = resubs;

		return a;
	}
	// 左填充12位数字字符串
			public static String getStringByFilling12(Integer id) {
				String str_m = String.valueOf(id); 
				String str ="000000000000";
				str_m=str.substring(0, 12-str_m.length())+str_m;

				return str_m;
			}
	//取得文件的类型
	public static String[] getStringsubWithFileType(String org, String sub) {
		String[] a = new String[2];
		int of = org.lastIndexOf(sub);
		String subs = org.substring(0, of);
		a[0] = subs;
		String resubs = org.substring(of + sub.length(), org.length());
		a[1] = resubs;

		return a;
	}
	
	
	

	//取总页数
	public static int getPagenumberCount(int count , int pagesize){
		
		int pagenumcount = count/pagesize ;
    	if(count % (pagesize)!=0){
    		pagenumcount = pagenumcount +1;
    	}
    	if(pagenumcount==0){
    		pagenumcount=1;
    	}
    	return pagenumcount;
	}
	
	
	
	public static String encodePwd(String pwd){
		
		return pwd;
	}
	
	
	/**
	 * 解码url
	 * @param s
	 * @return
	 */
	public static String DecodeUrl(String s){
		try{
			s = java.net.URLDecoder.decode(s,"UTF-8");
			//System.out.println("s new =============="+s);
			//s=new String(s.getBytes("ISO-8859-1"));
			return s;
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
	public static String getStringGBKEncode(String str) {  
		  try {   
			   byte[] bytes1 = str.getBytes("UTF-8");
			   byte[] bytes2 = str.getBytes("ISO-8859-1");
			   byte[] bytes3 = str.getBytes("Unicode");   
			   byte[] bytes4 = str.getBytes("GB2312");
			   if (str.equals(new String(bytes1))) {
			    return new String(str.getBytes("GBK"),"UTF-8");
			   }
			   if (str.equals(new String(bytes2))) {
			    return new String(str.getBytes("GBK"),"ISO-8859-1");
			   }
			   if (str.equals(new String(bytes3))) {
			    return new String(str.getBytes("GBK"),"Unicode");
			   }
			   if (str.equals(new String(bytes4))) {
			    return new String(str.getBytes("GBK"),"GB2312");
			   }
		  } catch (UnsupportedEncodingException e) {   
		      e.printStackTrace();
		  } 
		  return str;
		 }



	/**
	 * 编码url
	 * @param s
	 * @return
	 */
	public static String EncodeUrl(String s){
		
		try{
			//必须要编码两次才能成功 反编码，适用于Url包含中文字符
			s = java.net.URLEncoder.encode(s,"UTF-8");
			s = java.net.URLEncoder.encode(s,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
	//生成18位长的随机数
	public static String randomString(){
		String s = "";
		  Random random = new Random();
		  s+=random.nextInt(9)+1; 
		  for (int i = 0; i < 18-1; i++) {
		            s+=random.nextInt(10); 
		  }
		  return s;
		 
	}
	//生成6位长的随机数
		public static String randomString6(){
			String s = "";
			  Random random = new Random();
			  s+=random.nextInt(9)+1; 
			  for (int i = 0; i < 6-1; i++) {
			            s+=random.nextInt(10); 
			  }
			  return s;
			 
		}
	public static String getPushMessagelist(Map<String,String> map){

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"machinecode\":\""+getNullValue(map.get("code"))+"\"");
		sb.append(",\"category\":\""+getNullValue(map.get("category"))+"\"");
		sb.append(",\"operation\":\""+getNullValue(map.get("operation"))+"\"");
		sb.append(",\"imgurl\":\""+getNullValue(map.get("imgurl"))+"\"");
		sb.append(",\"new_imgurl\":\""+getNullValue(map.get("newimgurl"))+"\"");
		sb.append(",\"imgurl_small\":\""+getNullValue(map.get("imgurl_small"))+"\"");
		sb.append(",\"new_imgurl_small\":\""+getNullValue(map.get("new_imgurl_small"))+"\"");
		sb.append(",\"url\":\""+getNullValue(map.get("url"))+"\"");
		sb.append(",\"new_url\":\""+getNullValue(map.get("new_url"))+"\"");
		sb.append(",\"description\":\""+getNullValue(map.get("description"))+"\"");
		sb.append("}");
		return sb.toString();
	}
	
	public static String checkZoneName(String zoneName){
		return zoneName.replace("中山市市辖区", "市辖区");
	}
	
	/**
	 * 关键字过滤条件不能包含全角或半角的单引号、双引号和百分号等，一般SQL语句用
	 * @param str SQL语句
	 * @return 转义后的SQL语句
	 * @author zhangpeng
	 */
	public static String replaceStr(String str)
	{
		if (str == null )
		{
			return "";
		}
		//for sql server
		//		String replaceStr = str.replace("[", "[[]");// 此句一定要在最前面
		//		replaceStr = replaceStr.replace("_", "[_]");
		//		replaceStr = replaceStr.replace("%", "[%]");
		//		replaceStr = replaceStr.replace("'", "''");
		//		replaceStr = replaceStr.replace("‘", "\\‘");
		//		replaceStr = replaceStr.replace("’", "\\’");
		//		replaceStr = replaceStr.replace("{", "[{]");

		//for mysql
		String replaceStr = str.replace("\\", "\\\\\\\\");
		replaceStr = replaceStr.replace("_", "\\_");
		replaceStr = replaceStr.replace("%", "\\%");
		replaceStr = replaceStr.replace("'", "''");
		return replaceStr;
	}

	public static String getAPPurl(String basePath){
		return basePath+"LoveSmallScreen-3.2.3.apk";
	}
	public static String getIosAPPurl(){
		return "http://www.wwcode.net/s/hkTe2";
	}
	public static String getSpread(){
		return "亲！下载每天积分,滑屏送积分，礼品免费送！";
	}
	public static String getQr(String basePath){
		return basePath+"qr.png";
	}
	
	public static String getWebQr(String basePath){
		return basePath+"webqr.png";
	}
	
	public static String getLogo(String basePath){
		return basePath+"logo.png";
	}
	public static String getNullValue(String value){
		if(value ==null){
			return "";
		}
		return value;
	}
	
	public static Integer getNullValue(Integer value){
		if(value ==null){
			return 0;
		}
		return value;
	}
	
	public static Double getNullValue(Double value){
		if(value ==null){
			return 0d;
		}
		return value;
	}
	
	public static Hashtable<Integer,String> GetUserScoreDayHash(){
		
		if(userScoreDayHash==null){
			userScoreDayHash = new Hashtable<Integer,String>();
			
		}
		return userScoreDayHash;
	}
	
    public static Hashtable<Integer,Integer> GetUserStoreScoreHash(){
		
		if(userStoreScoresHash==null){
			userStoreScoresHash = new Hashtable<Integer,Integer>();
			
		}
		return userStoreScoresHash;
	}
	/**过滤特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 *@author zhangpeng
	 *@time 2015-6-6
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	} 
    public static void main(String[] args) {
	//	System.out.println(StringFilter("$23&44@"));
	//	 System.out.println(isContainChinese("是否包含中文China"));
    	for(int i=0;i<100;i++){
		 redRandom();
    	}
	}
    
    public static String escapeUtil(String txt){
    	return txt.replace("&amp;", "&")
    			.replace("&ensp;", "")
    			.replace("&emsp;", "")
    			.replace("&nbsp;", "")
    			.replace("&quot;", "\""); 
    }

	/**
	 * 检查字符串是否为空
	 * 检查规则：
	 * 1，为null；
	 * 2，是空字符串；
	 * 3，是全空格字符串；
	 * @param param 被检查的字符串
	 * @return
	 */
	public static Boolean isEmpty(String param) {
		return !hasLength(param);
	}

	/**
	 * 将给定的字符串首字母大写；
	 * @param param 传入的参数；
	 * @return 返回首字母大写的字符串；
	 */
	public static String upperFirstChar(String param) {
		if (isEmpty(param)) {//先判断是否为空；
			return null;
		}
		if (param.length() == 1) {//判断是否只有一个字符；
			return param.toUpperCase();
		}
		return String.valueOf(param.charAt(0)).toUpperCase() + param.substring(1);
	}

	/**
	 * 将给定的字符串的首字母小写；
	 * @param param 给定的字符串；
	 * @return 首字母小写的字符串；
	 */
	public static String lowerFirstChar(String param) {
		if (isEmpty(param)) {//先判断是否为空；
			return null;
		}
		if (param.length() == 1) {//判断是否只有一个字符；
			return param.toLowerCase();
		}
		return String.valueOf(param.charAt(0)).toLowerCase() + param.substring(1);
	}
	
	/**
	 * 是否包含中文
	 * @param str
	 * @return
	 */
	 public static boolean isContainChinese(String str) {
	        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	        Matcher m = p.matcher(str);
	        if (m.find()) {
	            return true;
	        }
	        return false;
	    }
	
	 
	 /**
	  * 订单红包随机数
	  * @return
	  */
	public static boolean redRandom(){
			int num=(int)(Math.random()*100);
			if(num<redPacketOdds){
				return true;
			}
			return false;
	}
	 
	public static Integer getPayTypeNum(String payType){
  		if(payType.equals("ALIPAY")){
  			return 10;
  		}else if(payType.equals("WEIXIN")){
  			return 20;
  		}else if(payType.equals("WALLET")){
  			return 30;
  		}else if(payType.equals("YILIAN")){
  			return 40;
  		}
		return -1;
  	}
	
	public static String replaceJsonArray(String str){
		if(StringUtils.isNotBlank(str)&&str.length()>10){
		JSONArray jsonArray=JSONArray.parseArray(str);
		String data=jsonArray.toString().replaceAll("imageUrl", "img");
		data=data.replaceAll("linkUrl", "url");
		return data;
		}else{
			return null;
		}
	}
	
	public static String replaceJsonObject(String str){
		if(StringUtils.isNotBlank(str)&&str.length()>8){
			JSONArray jsonArray=new JSONArray();
			JSONObject obj=JSONObject.parseObject(str);
			jsonArray.add(obj);
			String data=jsonArray.toString().replaceAll("imageUrl", "img");
			data=data.replaceAll("linkUrl", "url");
			return data;
		}else{
			return null;
		}
		
	}

	public static Integer getAppVersion(String appVersion){
		String version = "";
        char[] charArray = appVersion.toCharArray();
    	for (int i = 0; i < charArray.length; i++) {
			if(".".equals(String.valueOf(charArray[i]))){
				continue;
			}
			   version+=charArray[i];
		}
    	return Integer.parseInt(version);
	}
	
	
	
	public static Integer getGoodsStock(ReShoppingCar eachSellerShoppingCar,ReGoodsOfSellerMall sellerMall ){
		Integer stock = 0;
		String standradCar = eachSellerShoppingCar.getGoodsStandardString();
		JSONObject object = JSONObject.parseObject(standradCar);
		String id = object.getJSONObject("secondStandard").getString("id1");
		
		
		String standradAll = sellerMall.getStandardDetails();
		JSONObject object2 = JSONObject.parseObject(standradAll);
		JSONArray jsonArray =object2.getJSONObject("data").getJSONArray("standardDetails");
		for (int i = 0; i < jsonArray.size(); i++) {
			String id1 = jsonArray.getJSONObject(i).getString("id1");
			
			if (id1.equals(id)) {
				stock = Integer.parseInt(jsonArray.getJSONObject(i).getString("repertory"));
			}
		}
		return stock;
	}
	
	 public static String filterEmoji(String source) {
	        if (source == null) {
	            return source;
	        }
	        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
	                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	        Matcher emojiMatcher = emoji.matcher(source);
	        if (emojiMatcher.find()) {
	            source = emojiMatcher.replaceAll("");
	            return source;
	        }
	        return source;
	    }
	
	/**
	 * 处理emoji 表情保存数据库
	 * @param emoji
	 * @return
	 */
	 public static String saveEmoji(String emoji){
		return EmojiParser.parseToAliases(emoji);
	 }
	 /**
	 * 处理emoji 从数据库获取
	 * @param emoji
	 * @return
	 */
	 public static String getEmoji(String emoji){
		return EmojiParser.parseToUnicode(emoji);
	 }
}
