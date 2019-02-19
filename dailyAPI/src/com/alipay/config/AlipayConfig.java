package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088721091073684"; //da
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	// 商户的私钥(RSA) 
	public static String private_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1dgihHinInO6W0LUL8NcCk4decc2bp5uYiHZFFKNskAowHbnyNJoqhnQCBJsY13BoILxqSy5K4avnEnQqsP9GiDQhFPSi7hP3GAOt5LIi51FPCmKKsbQMPYizwyzcjOLOpSvHzLJOtAzy176LqWxA51+wMNZtuxD1W53eQiBGFsOhNsGDjGNiS9FUz+RHO3FZluO7hKSpHjHDKhz7VDIHGVIh8pz2Q/nStTMSmrkFJo/BYn8CkqK5F73U501gxb3i9tImdge3e0QZ1HWzCtvYB3maS7j4WPTdXx4bw2inTCJl1E1PDYkhTlJgQeckWArEfnAgFHI0KniY99Ac5eYMwIDAQAB";
	//da
	public static String appid = "2018090561256545";
	// 商户的私钥(RSA,pkcs8格式)  da
	public static String private_key_pkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDV2CKEeKcic7pbQtQvw1wKTh15xzZunm5iIdkUUo2yQCjAdufI0miqGdAIEmxjXcGggvGpLLkrhq+cSdCqw/0aINCEU9KLuE/cYA63ksiLnUU8KYoqxtAw9iLPDLNyM4s6lK8fMsk60DPLXvoupbEDnX7Aw1m27EPVbnd5CIEYWw6E2wYOMY2JL0VTP5Ec7cVmW47uEpKkeMcMqHPtUMgcZUiHynPZD+dK1MxKauQUmj8FifwKSorkXvdTnTWDFveL20iZ2B7d7RBnUdbMK29gHeZpLuPhY9N1fHhvDaKdMImXUTU8NiSFOUmBB5yRYCsR+cCAUcjQqeJj30Bzl5gzAgMBAAECggEAe3eKHnBHF6Y37987AcTLDIwjip9Rtza0RuaCHrC3bTaMEOvWUeXiVmwG59IzUH/eft17yBCxbQcbt6IK372/EenmWrDG/LLTMUso1CbksW+BfQYC50XZCJvkZyVPKl3GQnF8VwgMlW+GqCJTUsGJ3at+dq+TygDJ/Z4FTxVw6+0AfRBjYTZYcLEe3o+/OL4H97toLa1v6IC9FE3XoI3Xx3aHYX4p2Ty0XPfifzp7EkY35+Jd7slH/EzIbRyorxspcCit7ogJnMlzuW0pREuSfzZ8tv/aPd0Bl+yfUYuLb04tDDU/hKyFbyQE6oB76P19kBwHqkhuZrTFMfzmjOlk0QKBgQD2f1oU8EKdCxyYuiN9fhvWXwqa4eS+fVb18ymzaGtNqzgI0X/83ByOGwORa/xTH449FCV5SPPPx9GsaKbgOzuxFySU/WT30acbHk9uOg3GFIoaUP8Egglta3iEiYrmgthFng6EJLCaDDE7yAj/OneW/qQjfTeehsjqOLYO3EofOQKBgQDeFoiF4uf65ccQ3XNd3lb1hK9vKK9IYL4mcjfwHJ3Q16Gp/AhltonIOXhSP+xJoVCmMflruXLiO4ammRCxY0xpPfI0IJSSups9m/nALlUenE2v0XFqRuoYSJvDJKW2a7//pyp1ppI9MVPanMsSeyRQaN3hANr7+JOXzWdkY0GGywKBgHugcaiQu4gR+5yptoUJC6Mfda0qYFQdftDJzP9a/n4swmTB1eQW7QdkiwyTUTDLYcZGZWkH6qgqOoGy1wcdDhoKVmGXGN2lu57BO+s3mJLg2EbMmqpDIcFMJlyqswxbUWbRF8y0rhIEzWiDMef4BeSn/+CAu7V5OikmmkCnwVEZAoGBANLeLk6BfaX6GiLnQSz1eTew/inbRWLBiIpUlMxFP4sV0oflp8t7oCMefiIKU74tjrfeZ2ZjNLOdtWHln7XuBJVAngHklW0kBVZgDKju1R81ZYX0pW01W/wmpM6Ks1Ubxbdl76i5ucykU2wUx7QKfBZlG5cSCtqH0Qunme31WDg1AoGBAKmY+cUn6FGWZcjBBNEFRqfArqLISrDkoCHpg5TNOZ9VZaUz95T86ZEwsEZOFBlDOq2SIrIudWqOOrZwoUiTP+sL+LBss7Ns2TvedOxFbY1F7Ielh2ocmQfb0kSV5GHTVuk2yZvAm/Af3+ghcXcXlmH4zH1HcEs6g/Y+nacnYtsv";
	// 支付宝的公钥，无需修改该值 da
	public static String ali_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlT3Zhd404am9bjFbS4k5VGHR6WKPwZEoGFK4hJepr5t3UQDFtH1ncFyCOAqXytCP1SIobR4T2pT8sd4oTbvohYS6oIWARvdnVpxp+VZMDhnq0lpDu0Phe8H517g9ljltyYx2SDAABnN3Ddj3RBLG70CoT3OQXJoTyXFqK/PQHGKqT5yu/jrDyP0o6Lle0Kk2rn03XnSmWkEQAVkBxYymGZFryDr8xiUvTBcwojZjr4xQufUha+7Mc0mui74u+DPdIi26BRfj8azd0x1psTJqvpnyY2gMFh0vnU95JtBypviqTdRjeAYe1GpzSEsJy2I1tPjNum88YzIRKjoXh2E27QIDAQAB";


	// 商户私钥(MD5)
	public static String key = "r1tcvyb04i61eprw2fub2p2bg4u71d6l";

	// 商户绑定支付宝账号 da
	public static String seller_email = "2535044006@qq.com";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\alipayLog\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 即时到帐签名方式 不需修改 da
	public static String sign_type = "RSA2";

	// MD5签名方式
	public static String sign_type_MD5 = "MD5";

	// ////////////////////////////////////////
	// service
	public static String service = "alipay.wap.create.direct.pay.by.user";

	// 异步通知的地址
	public static String notify_url = "http://jifen.aixiaoping.cn:8080/dailyintegralAI/sellerMallNotify";
	

	// 返回跳转界面
	public static String return_url = "http://jifen.aixiaoping.cn:8080/jupinhui/successPay.jsp";
	

	// 支付类型，1：表示商品购买；
	public static String payment_type = "1";

	// 超时时间
	public static String it_b_pay = "1h";
}
