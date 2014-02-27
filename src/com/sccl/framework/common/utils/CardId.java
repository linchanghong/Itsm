package com.sccl.framework.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号</p>
 * 
 * 功能：</br>
 * 验证15、18位身份证号是否正确；</br>
 * 15转18位身份证号；</br>
 * 始终返回18位的身份证号；</br>
 * 返回出生年月、年月日、性别、预计退休年龄；
 */
public class CardId {
	/**
	 * 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
	 * 三位数字顺序码和一位数字校验码。
	 * 
	 * 2、地址码(前六位数） 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
	 * 
	 * 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 * 
	 * 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
	 * 
	 * 5、校验码（第十八位数） （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16
	 * ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6
	 * 3 7 9 10 5 8 4 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7
	 * 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
	 * */

	/**
	 * 身份证号
	 */
	private String cardId;

	/**
	 * 出生年月
	 */
	private Date birthday;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 构造函数
	 */
//	public CardId() {
//		super();
//	}

	/**
	 * 带身份证号的构造函数
	 * 
	 * @param cardId
	 *            身份证号
	 * @throws Exception
	 */
	public CardId(String cardId) throws Exception {
		this.cardId = toEighteenId(cardId);
		birthday = getBirthday(cardId);
		gender = getGender(cardId);
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 * @throws Exception
	 */
	public void setCardId(String cardId) throws Exception {
		this.cardId = toEighteenId(cardId);
		birthday = getBirthday(cardId);
		gender = getGender(cardId);
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * 出生年月
	 * 
	 * @return 出生年月，格式2013-01
	 * 
	 */
	public String getBirthmonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

		return formatter.format(birthday);
	}
	
	/**
	 * 出生年月日
	 * 
	 * @return 出生年月日，格式2013-01-01
	 * 
	 */
	public String getBirthday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return formatter.format(birthday);
	}

	/**
	 * 获取年龄
	 * 
	 * @return 年龄
	 */
	public int getAge() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		int age = Integer.parseInt(formatter.format(date))
				- Integer.parseInt(formatter.format(birthday));
		return age;

	}

	/**
	 * 退休年月，按男60岁，女55岁退休计算
	 * 
	 * @return 退休年月，格式2013-01
	 * 
	 */
	public String getTireDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String str = formatter.format(birthday);
		if(getGender().equals("男")) {
			str = String.valueOf(Integer.parseInt(str.substring(0, 4)) + 60)
				+ str.substring(4);
		} else {
			str = String.valueOf(Integer.parseInt(str.substring(0, 4)) + 55)
				+ str.substring(4);
		}
		return str;
	}
	
	/**
	 * 退休年月
	 * 
	 * @param tireAge 退休年龄
	 * @return 退休年月，格式2013-01
	 * 
	 */
	public String getTireDate(int tireAge) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String str = formatter.format(birthday);
		
		str = String.valueOf(Integer.parseInt(str.substring(0, 4)) + tireAge)
			+ str.substring(4);
			
		return str;
	}

	/**
	 * 返回性别
	 * 
	 * @return 性别
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * 根据身份证获取出生年月
	 * 
	 * @param cardId
	 *            身份证号
	 * @return 出生年月
	 * @throws Exception
	 *             身份证错误信息
	 *             
	 */
	public static Date getBirthday(String cardId) throws Exception {
		Date birthday;

		// 判断身份证号码长度
		if (cardId.length() != 18 && cardId.length() != 15) {
			throw new Exception("身份证号长度应该为15位或18位");
		}

		// ================ 数字 除最后1位都为数字 ================
		String sevenId = "";
		if (cardId.length() == 18) {
			sevenId = cardId.substring(0, 17);
		} else if (cardId.length() == 15) {
			sevenId = cardId.substring(0, 6) + "19" + cardId.substring(6, 15);
		}
		if (isNumeric(sevenId) == false) {
			throw new Exception("15位号应都为数字 ; 18位号除最后一位外也都应为数字。");
		}

		// 验证校验位
		if (cardId.length() == 18) {
			String verify = getVerify(sevenId);
			if (!cardId.substring(17, 18).toLowerCase().equals(verify)) {
				throw new Exception("身份证校验位有误");
			}
		}
		// 根据身份证号获取出生年月
		String dateString;
		dateString = sevenId.substring(6, 10);
		dateString = dateString + "-" + sevenId.substring(10, 12);
		dateString = dateString + "-" + sevenId.substring(12, 14);
		birthday = StringToDate(dateString);
		return birthday;

	}

	/**
	 * 验证身份证是否有效
	 * 
	 * @param cardId
	 *            身份证号
	 * @return 如果正确，返回传入的身份证号；否则返回错误信息
	 * 
	 */
	public static String checkEffect(String cardId) {

		// 判断身份证号码长度
		if (cardId.length() != 18 && cardId.length() != 15) {
			return "身份证号长度应该为15位或18位";
		}
		// ================ 数字 除最后以为都为数字 ================
		String sevenId = "";
		if (cardId.length() == 18) {
			sevenId = cardId.substring(0, 17);
		} else if (cardId.length() == 15) {
			sevenId = cardId.substring(0, 6) + "19" + cardId.substring(6, 15);
		}
		if (isNumeric(sevenId) == false) {
			return "15位号应都为数字 ; 18位号除最后一位外也都应为数字。";
		}
		 // 验证校验位
		if (cardId.length() == 18) {
			String verify = getVerify(sevenId);
			if (!cardId.substring(17, 18).toLowerCase().equals(verify)) {
				return "身份证校验位有误";
			}
		}
		int year = Integer.parseInt(sevenId.substring(6, 10));
		int month = Integer.parseInt(sevenId.substring(10, 12));
		int day = Integer.parseInt(sevenId.substring(12, 14));
		if (month < 1
				|| month > 12
				|| day < 1
				|| day > 31
				|| ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
				|| (month == 2 && (((year) % 4 > 0 && day > 28) || day > 29))) {
			return "身份证号中出生日期有误";
		}
		
		return cardId;
	}

	/**
	 * 根据身份证号获取性别
	 * 
	 * @param cardId
	 *            身份证号
	 * @return 性别，男 或 女
	 * 
	 */
	public static String getGender(String cardId) {
		String gender = null;
		if (cardId.length() == 18) {
			gender = Integer.parseInt(cardId.substring(16, 17)) % 2 == 0 ? "女"
					: "男";
		} else if (cardId.length() == 15) {
			gender = Integer.parseInt(cardId.substring(14, 15)) % 2 == 0 ? "女"
					: "男";
		}
		return gender;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据17位身份证号获取验证码
	 * 
	 * @param cardId
	 *            17位身份证号
	 * @return 验证码
	 * 
	 */
	public static String getVerify(String cardId) {
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(cardId.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];

		return strVerifyCode;
	}

	/**
	 * 将"yyyy-MM-dd"格式的日期字符串转为java.util.Date类型
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return 时间类型
	 * 
	 */
	public static Date StringToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String[] array = strDate.split("-");
			if (array.length != 3)
				throw new Exception();
			int year = Integer.parseInt(array[0]);
			int month = Integer.parseInt(array[1]);
			int day = Integer.parseInt(array[2]);
			if (month < 1
					|| month > 12
					|| day < 1
					|| day > 31
					|| ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
					|| (month == 2 && (((year) % 4 > 0 && day > 28) || day > 29)))
				throw new Exception();
			return formatter.parse(strDate);

		} catch (Exception e) {
			throw new RuntimeException("日期错误");
		}
	}

	/**
	 * 15位身份证号转18位
	 * 
	 * @param cardId
	 * @return
	 * 
	 */
	public static String toEighteenId(String cardId) {
		if (cardId.length() != 15)
			return cardId;
		cardId = cardId.substring(0, 6) + "19" + cardId.substring(6, 15);
		cardId = cardId + getVerify(cardId);
		return cardId;
	}
	
}

