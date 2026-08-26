package com.mpodda.thymeleaf_sample.domain.model;

import java.io.Serializable;

import com.mpodda.thymeleaf_sample.domain.enums.FilterOperators;

public class FilterModel implements Serializable {
	private static final long serialVersionUID = 3574568609786008620L;

	private String filterField;
	
	private String filterValue;
	
	private FilterOperators operator;

	public FilterModel() {
		
	}
	
	public FilterModel(String filterField, FilterOperators operator) {
		this.filterField = filterField;
		this.operator = operator;
		
		this.filterValue = "";
	}

	public FilterModel(String filterField, String filterValue, FilterOperators operator) {
		this.filterField = filterField;
		this.filterValue = filterValue;
		this.operator = operator;
	}
	

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public FilterOperators getOperator() {
		return operator;
	}

	public void setOperator(FilterOperators operator) {
		this.operator = operator;
	}
}
