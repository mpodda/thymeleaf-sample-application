package com.mpodda.thymeleaf_sample.domain.dto;

public class FilterDto extends BaseDto {
	private static final long serialVersionUID = -7324933580363705578L;
	
	private String sessionAttribute;
	
	private String name;
	
	private String value;
	
	private String randomSuffix;

	public String getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRandomSuffix() {
		return randomSuffix;
	}

	public void setRandomSuffix(String randomSuffix) {
		this.randomSuffix = randomSuffix;
	}
}
