package com.viettel.bss.viettelpos.v4.commons;

import java.lang.reflect.Field;

public class ReflectionUtils {
	public static String getValue(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			
			Object tmp = field.get(obj);
			if(tmp == null){
				return "";
			} else {
				return String.valueOf(tmp);
			}
		} catch (Exception ex) {
			return "";
		}
	}
	
	public static void setValue(Object obj, String fieldName, Object fieldValue) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception ignored) {
		}
	}
}
