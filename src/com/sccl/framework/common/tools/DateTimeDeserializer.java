package com.sccl.framework.common.tools;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import org.apache.commons.httpclient.util.DateUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeDeserializer implements JsonSerializer<Timestamp>{

	@Override
	public JsonElement serialize(Timestamp date, Type type, JsonSerializationContext arg2) {
		
		 return new JsonPrimitive(DateUtil.formatDate(date));  
	}
	
}
