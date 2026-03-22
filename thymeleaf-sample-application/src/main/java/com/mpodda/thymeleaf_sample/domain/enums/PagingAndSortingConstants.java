package com.mpodda.thymeleaf_sample.domain.enums;

public enum PagingAndSortingConstants {
	PAGE_SIZE(5),
	DEFAULT_PAGE(0),
	DEFALUT_SORTING_DIRECTION("asc"),
	ASC("asc"),
	DESC("desc")
	;
	
	private int intValue;
	private String stringValue;

	private PagingAndSortingConstants(int intValue) {
		this.intValue = intValue;
	}

	private PagingAndSortingConstants(String stringValue) {
		this.stringValue = stringValue;
	}
	
	public int intValue() {
		return this.intValue;
	}
	
	public String stringValue() {
		return this.stringValue;
	}
}
