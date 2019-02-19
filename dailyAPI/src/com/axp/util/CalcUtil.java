package com.axp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Maijial 
 */
public class CalcUtil {
	
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 2;

	// 这个类不能实例化
	private CalcUtil() {
		
	}

	/**
	 * 提供精确的加法运算	
	 * @param v1           被加数
	 * @param v2            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	/**
	 * 提供精确的加法运算	
	 * @param v1           被加数
	 * @param v2            加数
	 * @param v2            加数
	 * @param v2            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2,double v3, double v4) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.add(b2).add(b3).add(b4).doubleValue();
	}
	/**
	 * 提供精确的加法运算	(小数点后scale位)
	 * @param v1           被加数
	 * @param v2            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2,int scale){
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的减法运算
	
	 * @param v1            被减数
	 * @param v2            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	
	/**
	 * 提供精确的减法运算
	
	 * @param v1            被减数
	 * @param v2            减数
	 *  @param v3           减数
	 *   @param v4            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2,double v3, double v4){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.subtract(b2).subtract(b3).subtract(b4).doubleValue();
	}
	
	public static void main(String[] args) {
        //调用
        
        
        double v1 =20;
        double v2 =1;
        double v3 =1;
        double v4 =0;
        double m =sub(v1, v2, v3, v4);
    }
	
	
	
	
    public static int genDoubleRandom(){
        Random random = new Random();
        //产生一个[0,499]的double数值
        int aaa = random.nextInt(89999)+10000;
       
        return aaa;
    }
	

	/**
	 * 提供精确的乘法运算	
	 * @param v1            被乘数
	 * @param v2            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	public static double mul(double v1, double v2,int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的乘法运算	
	 * @param v1            被乘数
	 * @param v2            乘数
	 * @param v3            乘数
	 * @param v4            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2,double v3,double v4) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.multiply(b2).multiply(b3).multiply(b4).doubleValue();
	}
	
	public static double mul(double v1, double v2,double v3,double v4 ,int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.multiply(b2).multiply(b3).multiply(b4).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入	
	 * @param v1            被除数
	 * @param v2            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。	
	 * @param v1            被除数
	 * @param v2            除数
	 * @param scale         表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。	
	 * @param v           需要四舍五入的数字
	 * @param scale       小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	
	
	/**
	 * 抽奖
	 *
	 * @param orignalRates 原始的概率列表，保证顺序和实际物品对应
	 * @return 物品的索引
	 */
	public static int lottery(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
	
	public static int getJD(List<Double> orignalRates) {
		if (orignalRates == null || orignalRates.isEmpty()) {
			return -1;
		}

		int size = orignalRates.size();

		// 计算总概率，这样可以保证不一定总概率是1
		double sumRate = 0d;
		for (double rate : orignalRates) {
			sumRate += rate;
		}

		// 计算每个物品在总概率的基础下的概率情况
		List<Double> sortOrignalRates = new ArrayList<Double>(size);
		Double tempSumRate = 0d;
		for (double rate : orignalRates) {
			tempSumRate += rate;
			sortOrignalRates.add(tempSumRate / sumRate);
		}

		// 根据区块值来获取抽取到的物品索引
		double nextDouble = Math.random();
		sortOrignalRates.add(nextDouble);
		Collections.sort(sortOrignalRates);

		return sortOrignalRates.indexOf(nextDouble);
	}
	
	
	



}