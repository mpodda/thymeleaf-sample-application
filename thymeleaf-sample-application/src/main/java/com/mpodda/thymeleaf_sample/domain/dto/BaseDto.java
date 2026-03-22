package com.mpodda.thymeleaf_sample.domain.dto;

import java.io.Serializable;

import com.mpodda.thymeleaf_sample.utils.Serializer;

public class BaseDto implements Serializable {
	private static final long serialVersionUID = -3599303251624403426L;
	
	@Override
	public String toString() {
		return Serializer.objectToJsonString(this);
	}
}
