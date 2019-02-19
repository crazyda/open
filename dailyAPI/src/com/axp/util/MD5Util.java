package com.axp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.alipay.api.domain.Data;
import com.axp.domain.Users;

/*
 * MD5 算法
 */
@SuppressWarnings("all")
public class MD5Util {

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5Util() {
	}

	
	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}



	// 返回形式只为数字

	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	 /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(origin.getBytes()));
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return resultString;
    }
    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }
    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return strDigits[d1] + strDigits[d2];
    }


	/**
	 * 访问验证码
	 * 
	 * @throws ParseException
	 */
	public static boolean getAxpMd5code(String userId, String axp,String times)
			throws ParseException {
		
		
		
		boolean flag=getComp(userId,axp,times);
		
		if(!flag && "-1".equals(userId)){
			flag = getComp("",axp,times);
			if(!flag){
				flag = getComp("(null)",axp,times);
			}
		}
		
		return flag;
		
	}

	
	public static boolean getComp(String userId,String axp ,String times) throws ParseException{
		SimpleDateFormat formatterTwo = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date createDate=new Date();
		Calendar calendar = Calendar.getInstance();
			Date date=null;
			
			String axpUser = MD5Util.GetMD5Code("axp" + userId);
			String createDateStr = formatterTwo.format(createDate); // 2017-07-17-11-21
			String axpKey = MD5Util.GetMD5Code(axpUser + createDateStr);
			//System.out.println("明文："+axpUser + createDateStr);
			
			
			if(StringUtils.isBlank(axp)&&StringUtils.isNotBlank(times)){
				    Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(times);
				    String dateStr=formatterTwo.format(d);
				    axp = MD5Util.GetMD5Code(axpUser + dateStr);
				   
			}
			
			if (axp.equals(axpKey)) {
				return true;
			} else if (!axp.equals(axpKey)) {
				calendar.setTime(createDate);
				calendar.add(Calendar.MINUTE, -1);
				date = calendar.getTime();
				axpKey=MD5Util.GetMD5Code(axpUser+ formatterTwo.format(date));
			} 
			if (!axp.equals(axpKey)) {
				calendar.setTime(createDate);
				calendar.add(Calendar.MINUTE, 1);
				date = calendar.getTime();
				axpKey=MD5Util.GetMD5Code(axpUser+ formatterTwo.format(date));
			}
			
			if (!axp.equals(axpKey) ){
				return false;
			} 
			
		return true;
	}
	
	public static void main(String[] args) throws ParseException {
		//String u = "4515";
		String md5 = MD5Util.GetMD5Code("axp" +811);
		// System.out.println(md5.equals("d0467882cc84426c0fa43e5546ef431a"));
		String md52 = MD5Util.GetMD5Code(md5 + "2017-07-17-11-21");
		/*Date currentTime = new Date();

		MD5Util m = new MD5Util();
		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		String times="17-07-24 17:28:30";
		String times2="2017-07-24 17:28:30";
		
		Date a=new Date();
		Date b=new Date();
		
		System.out.println(a.getTime()==b.getTime());*/
		SimpleDateFormat formatterTwo = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date createDate=new Date();
		String createDateStr = formatterTwo.format(createDate); // 2017-07-17-11-21
	}
	
	public static String getSign(Map<String, String> map,String client_screct){
		
		 Map<String, String> resultMap = sortMapByKey(map); 
		String value = client_screct;
		 for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			 value+=entry.getKey() + entry.getValue();
	        }
		 value+=client_screct;
		value=GetMD5Code(value);
		//System.out.println(value.toUpperCase());
		return value.toUpperCase();
		
	}
	   /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
	
    /**
     * 把手机号码隐藏
     * @param phone
     * @return
     */
    public static String hidePhone(Users user){
    	if(user.getPhone() == null){
			return "132****456";
		}else{
			
			return user.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
		}
    	
    }

    public static String getRandomStringByLength(int length) {
    	String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    	Random random = new Random();
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < length; i++) {
    		int number = random.nextInt(base.length());
    		sb.append(base.charAt(number));
    	}
    	return sb.toString();
    	
    }
}


	
	class MapKeyComparator implements Comparator<String>{
	
	    @Override
	    public int compare(String str1, String str2) {
	        
	        return str1.compareTo(str2);
	    }
}