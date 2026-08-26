package com.mpodda.thymeleaf_sample.utils;

public final class NumericUtils {
	public static boolean isNumeric(String value, Class<?> targetType) {
	    try {
	        if (targetType == Byte.class || targetType == byte.class) {
	            Byte.parseByte(value);
	        } else if (targetType == Short.class || targetType == short.class) {
	            Short.parseShort(value);
	        } else if (targetType == Integer.class || targetType == int.class) {
	            Integer.parseInt(value);
	        } else if (targetType == Long.class || targetType == long.class) {
	            Long.parseLong(value);
	        } else if (targetType == Float.class || targetType == float.class) {
	            Float.parseFloat(value);
	        } else if (targetType == Double.class || targetType == double.class) {
	            Double.parseDouble(value);
	        } else {
	            return false;
	        }

	        return true;

	    } catch (NumberFormatException e) {
	        return false;
	    }
	}	
}
