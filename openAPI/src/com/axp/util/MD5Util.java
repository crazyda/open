package com.axp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
	 * 访问验证码
	 * 
	 * @throws ParseException
	 */
	

	
	public static boolean getComp(String clientId,String clientSecret ,String type,String sign,String appId) throws ParseException{
		
		
		long l = System.currentTimeMillis();
		String time = l+"";
		
	
		String newSign = clientSecret+"appId"+appId+"clienId"+clientId+"time"+time.substring(0, 10)+"type"+type+clientSecret;
		
		
		String Md5Sign = MD5Util.GetMD5Code(newSign);//da 大写对比
		
		if(Md5Sign.equals(sign)){
			return true;
		}
		return false;
	}
	
	
	
	public static String getSign(Map<String, String> map,String client_screct){
		
		 Map<String, String> resultMap = sortMapByKey(map); 
		String value = client_screct;
		 for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			 value+=entry.getKey() + entry.getValue();
	        }
		 value+=client_screct;
		value=GetMD5Code(value);
		
		return value.toUpperCase();
		
	}
	
	public static void main(String[] args) {

        Map<String, String> map = new TreeMap<String, String>();

        map.put("aac", "kfc");
        map.put("aab", "wnba");
        map.put("x", "nba");
        map.put("s", "cba");

        Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序

        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
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
	
    


	
	
	/*public static void main(String[] args) throws ParseException {
		//String u = "4515";
		String md5 = MD5Util.GetMD5Code("axp" +811);
		// System.out.println(md5.equals("d0467882cc84426c0fa43e5546ef431a"));
		String md52 = MD5Util.GetMD5Code(md5 + "2017-07-17-11-21");
		System.out.println(md52);
		System.out.println(md52.equals("12eb06d418fd77e4078a7bab002f5598"));
		System.out.println(md52);
		Date currentTime = new Date();

		MD5Util m = new MD5Util();
		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		String times="17-07-24 17:28:30";
		String times2="2017-07-24 17:28:30";
		
		Date a=new Date();
		Date b=new Date();
		
		System.out.println(a.getTime()==b.getTime());
		SimpleDateFormat formatterTwo = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Date createDate=new Date();
		String createDateStr = formatterTwo.format(createDate); // 2017-07-17-11-21
		System.out.println(createDateStr);
	}*/

}


class MapKeyComparator implements Comparator<String>{

    @Override
    public int compare(String str1, String str2) {
        
        return str1.compareTo(str2);
    }
}