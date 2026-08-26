package com.mpodda.thymeleaf_sample.domain.enums;

public enum SessionalConstants {
	DATA("data"),
	PAGING_AND_SORTING_MODEL_ATTRIBUTE("pagingAndSortingDto")
	;
	
	private String value;
	
	private SessionalConstants(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	public String modelAttributeValue(String sessionAttribute) {
		return PAGING_AND_SORTING_MODEL_ATTRIBUTE.value() + "_" + sessionAttribute;
	}
}
