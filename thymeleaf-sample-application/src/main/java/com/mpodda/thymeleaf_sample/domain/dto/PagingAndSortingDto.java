package com.mpodda.thymeleaf_sample.domain.dto;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class PagingAndSortingDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -8711706368363317655L;
	
	/* Paging */
	private Integer pageOffset;
	
	private Integer pageNumber;
	
	private Integer numberOfPages;

	/* Sorting */
	private String currentSortField;
	
	private String currentSortDirection;
	
	private String viewName;
	
	private String dataType;
	
	private String randomSuffix;
	
	private String sessionAttribute;

	public Integer getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(Integer pageOffset) {
		this.pageOffset = pageOffset;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRandomSuffix() {
		return randomSuffix;
	}

	public void setRandomSuffix(String randomSuffix) {
		this.randomSuffix = randomSuffix;
	}

	public String getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(String sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}
}
