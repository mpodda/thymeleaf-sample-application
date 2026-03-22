package com.mpodda.thymeleaf_sample.domain.dto;

import java.io.Serializable;

public class SortingDto implements Serializable {
	private static final long serialVersionUID = -6377595841269981923L;

	private String currentSortField;
	
	private String currentSortDirection;
	
	private String viewName;
	
	private String dataType;
	
	private String randomSuffix;
	
	private String sessionAttribute;
	

	public String getCurrentSortField() {
		return currentSortField;
	}

	public void setCurrentSortField(String currentSortField) {
		this.currentSortField = currentSortField;
	}

	public String getCurrentSortDirection() {
		return currentSortDirection;
	}

	public void setCurrentSortDirection(String currentSortDirection) {
		this.currentSortDirection = currentSortDirection;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getRandomSuffix() {
		return randomSuffix;
	}

	public void setRandomSuffix(String randomSuffix) {
		this.randomSuffix = randomSuffix;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}
}
