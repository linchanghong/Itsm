package com.sccl.framework.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Uid {

	private static Uid uidUtil;
	private String format = "yyMMddHHmmssSSS";
	private SimpleDateFormat sdf = new SimpleDateFormat(format);
	private static String lastUID = "";
	private static int lastCount = 0;
	
	public static Uid getUidUtil() {
		if(uidUtil == null) {
			uidUtil = new Uid();
		}
		return uidUtil;
	}
	
	public String createUID() {
		Date date = new Date();
		String currentUID = sdf.format(date);
		if(currentUID == lastUID){
			lastCount = lastCount+1;
		}else{
			lastCount = 0;
		}
		String result = "";
		Random random = new Random();
		int length = 4;
		for(int i=0; i<length; i++) {
			result += String.valueOf(random.nextInt(10));
		}
		lastUID = currentUID;
		if(lastCount >= 100) {
			lastCount = 0;
		}
		return currentUID + String.valueOf(lastCount) + result;
	}
	
	/**
	 * 转换日期为String类型
	 * @param date
	 * @author avanry
	 * @date 2011/6/9
	 */
	public static String getDateString(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
}
