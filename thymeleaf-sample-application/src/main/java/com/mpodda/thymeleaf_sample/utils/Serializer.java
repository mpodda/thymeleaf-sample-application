package com.mpodda.thymeleaf_sample.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public final class Serializer {

	public static String objectToJsonString(final Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static <O extends Object> O jsonStringToObject (String s, Class<O> clazz) {
		ObjectMapper objectMapper = JsonMapper.builder().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
		
		try {
			return objectMapper.readValue(s, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;		
	}
	
	
}
