package com.mpodda.thymeleaf_sample.domain.dto;

import java.io.Serializable;

public class PagingDto implements Serializable {
	private static final long serialVersionUID = -2619965566661487333L;
	
	private Integer pageNumber;
	
	private Integer numberOfPages;
	
	private SortingDto sortingDto;
	
	private Object data;

	private String viewName;
	
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

	public SortingDto getSortingDto() {
		return sortingDto;
	}

	public void setSortingDto(SortingDto sortingDto) {
		this.sortingDto = sortingDto;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
