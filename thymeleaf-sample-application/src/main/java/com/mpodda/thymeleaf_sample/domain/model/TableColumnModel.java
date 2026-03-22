package com.mpodda.thymeleaf_sample.domain.model;

import java.io.Serializable;

public class TableColumnModel implements Serializable {
	private static final long serialVersionUID = -813338202748202005L;
	
	
	private String headerText;
	
	private String fieldName;

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
