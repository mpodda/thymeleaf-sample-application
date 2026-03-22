package com.mpodda.thymeleaf_sample.domain.enums;

public enum SessionalConstants {
	DATA("data");
	
	private String value;
	
	private SessionalConstants(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
