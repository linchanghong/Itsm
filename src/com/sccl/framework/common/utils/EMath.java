package com.sccl.framework.common.utils;

import java.math.BigDecimal;

/**
 * ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ������������㣬����������ṩ�� ȷ�ĸ���������</br>
 * �����Ӽ��˳����������롣</p>
 * 
 * ��������չ�����磬��һ����ʽ��(10+2-8)*7/5��EMath���д���ʽ�ľ�ȷ���㣬�����ؽ����
 */
public class EMath {
	// Ĭ�ϳ������㾫��
	private static final int DEF_DIV_SCALE = 10;

	// ����಻��ʵ����
	private EMath() {
	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣���ٴ�������������
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param v3
	 *            �ɱ����
	 * @return ���в����ĺ�
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
	 * �ṩ��ȷ�ļ������㡣���ٴ�������������ע�⣺��һ�������Ǳ�������
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param v3
	 *            �ɱ����
	 * @return ���������Ĳ�
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
	 * �ṩ��ȷ�ĳ˷����㣬ͬʱ�������롣
	 * 
	 * @param scale
	 *            ����С��λ��
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param v3
	 *            �ɱ����
	 * @return ���������Ļ������������롣
	 */
	public static double mul(int scale, double v1, double v2, double... v3) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale �������0");
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
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param v3
	 *            �ɱ����
	 * @return ���������Ļ�
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
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * @param v2
	 *            ����
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale �������0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale �������0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ������������ֵ��ַ���
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static double round(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"scale �������0");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		double tmp_double = b.divide(one, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return tmp_double;
	}
}
