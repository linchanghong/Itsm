package com.sccl.framework.common.utils;

import java.math.BigDecimal;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算</br>
 * 包括加减乘除、四舍五入。</p>
 * 
 * 还可以扩展，比如，传一个算式：(10+2-8)*7/5，EMath进行此算式的精确计算，并返回结果。
 */
public class EMath {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	// 这个类不能实例化
	private EMath() {
	}

	/**
	 * 提供精确的加法运算。至少传入两个参数。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @param v3
	 *            可变加数
	 * @return 所有参数的和
	 */
	public static double add(double v1, double v2, double... v3) {
		BigDecimal b = new BigDecimal(Double.toString(v1));
		b = b.add(new BigDecimal(Double.toString(v2)));

		for (double v : v3) {
			b = b.add(new BigDecimal(Double.toString(v)));
		}
		return b.doubleValue();
	}

	/**
	 * 提供精确的减法运算。至少传入两个参数，注意：第一个参数是被减数！
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @param v3
	 *            可变减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2, double... v3) {
		BigDecimal b = new BigDecimal(Double.toString(v1));
		b = b.subtract(new BigDecimal(Double.toString(v2)));

		for (double v : v3) {
			b = b.subtract(new BigDecimal(Double.toString(v)));
		}
		return b.doubleValue();
	}

	/**
	 * 提供精确的乘法运算，同时四舍五入。
	 * 
	 * @param scale
	 *            保留小数位数
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @param v3
	 *            可变乘数
	 * @return 两个参数的积，已四舍五入。
	 */
	public static double mul(int scale, double v1, double v2, double... v3) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale 必须大于0");
		}

		BigDecimal b = new BigDecimal(Double.toString(v1));
		b = b.multiply(new BigDecimal(Double.toString(v2)));

		for (double v : v3) {
			b = b.multiply(new BigDecimal(Double.toString(v)));
		}

		double tmp_double = b.doubleValue();

		return round(tmp_double, scale);
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @param v3
	 *            可变乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2, double... v3) {
		BigDecimal b = new BigDecimal(Double.toString(v1));
		b = b.multiply(new BigDecimal(Double.toString(v2)));

		for (double v : v3) {
			b = b.multiply(new BigDecimal(Double.toString(v)));
		}

		return b.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale 必须大于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale 必须大于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字的字符串
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale 必须大于0");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		double tmp_double = b.divide(one, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tmp_double;
	}
}
