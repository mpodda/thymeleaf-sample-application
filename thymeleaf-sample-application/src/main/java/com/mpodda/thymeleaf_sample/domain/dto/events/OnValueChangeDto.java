package com.mpodda.thymeleaf_sample.domain.dto.events;

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;

public class OnValueChangeDto extends BaseDto {
	private static final long serialVersionUID = 2178282993007613951L;
	
	private String sessionAttribute;
	
	private String name;
	
	private String value;
	
	private String event;
	
	private String fragmentUrl;
	
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getFragmentUrl() {
		return fragmentUrl;
	}

	public void setFragmentUrl(String fragmentUrl) {
		this.fragmentUrl = fragmentUrl;
	}

	public String getRandomSuffix() {
		return randomSuffix;
	}

	public void setRandomSuffix(String randomSuffix) {
		this.randomSuffix = randomSuffix;
	}
	
//	public String getEventBean() {
//		if (this.event == null) {
//			return null;
//		}
//		
//		return this.event.split(".")[0];
//	}
	
//	public String getEventMethod() {
//		if (this.event == null) {
//			return null;
//		}
//		
//		return this.event.split(".")[1];
//	}
	
}
