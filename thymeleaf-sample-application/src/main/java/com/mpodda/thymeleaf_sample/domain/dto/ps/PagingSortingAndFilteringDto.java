package com.mpodda.thymeleaf_sample.domain.dto.ps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.model.FilterModel;

public class PagingSortingAndFilteringDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -8711706368363317655L;
	
	private String fragmentUrl;
	
	private Integer numberOfRecords;
	
	/* Paging */
	private Integer pageOffset;
	
	private Integer pageNumber;
	
	private Integer numberOfPages;
	
	private List<PageNumberDto> pageNumbers;
	
	private List<Integer> firstPageNumbers;
	
	private List<Integer> lastPageNumbers;
	
	private Integer previousPageNumber;
	
	private Integer nextPageNumber;
	
	private transient List<? extends BaseDto> pageData;

	/* Sorting */
	private String currentSortField;
	
	private String currentSortDirection;
	
	private String viewName;
	
	private String dataType;
	
	private String randomSuffix;
	
	private String sessionAttribute;
	
	/* filtering */
//	private String filterField;
//	
//	private String filterValue;
	
	private List<FilterModel> filters = new ArrayList<FilterModel>();
	
//	private FilterDto filterDto;
	
//	public FilterDto getFilterDto() {
//		return filterDto;
//	}
//
//	public void setFilterDto(FilterDto filterDto) {
//		this.filterDto = filterDto;
//	}

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
	
	public List<PageNumberDto> getPageNumbers() {
		return pageNumbers;
	}

	public void setPageNumbers(List<PageNumberDto> pageNumbers) {
		this.pageNumbers = pageNumbers;
	}
	
	public Integer getPreviousPageNumber() {
		return previousPageNumber;
	}

	public void setPreviousPageNumber(Integer previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}

	public Integer getNextPageNumber() {
		return nextPageNumber;
	}

	public void setNextPageNumber(Integer nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public List<? extends BaseDto> getPageData() {
		return pageData;
	}

	public void setPageData(List<? extends BaseDto> pageData) {
		this.pageData = pageData;
	}

	public List<Integer> getFirstPageNumbers() {
		return firstPageNumbers;
	}

	public void setFirstPageNumbers(List<Integer> firstPageNumbers) {
		this.firstPageNumbers = firstPageNumbers;
	}

	public List<Integer> getLastPageNumbers() {
		return lastPageNumbers;
	}

	public void setLastPageNumbers(List<Integer> lastPageNumbers) {
		this.lastPageNumbers = lastPageNumbers;
	}

	public String getFragmentUrl() {
		return fragmentUrl;
	}

	public void setFragmentUrl(String fragmentUrl) {
		this.fragmentUrl = fragmentUrl;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}


	/*
	public void addFilter(final Filter filter) {
		this.filters.add(filter);
	}
	*/
	
	public void addFilter(final FilterModel filter) {
		
//		if (this.filterDto == null) {
//			this.filterDto = new FilterDto();
//		}
//		
//		this.filterDto.addFilter(filter);
		
		this.filters.add(filter);
	}
	

	/* filtering */
	
//	public String getFilterField() {
//		return filterField;
//	}
//
//	public void setFilterField(String filterField) {
//		this.filterField = filterField;
//	}
//
//	public String getFilterValue() {
//		return filterValue;
//	}
//
//	public void setFilterValue(String filterValue) {
//		this.filterValue = filterValue;
//	}
//	
//	public boolean hasFilter() {
//		return this.filterField != null && this.filterValue != null && !this.filterField.isBlank() && !this.filterValue.isBlank(); 
//	}
	
	public List<FilterModel> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterModel> filter) {
		this.filters = filter;
	}

	public boolean hasFilter() {
		return !this.filters.isEmpty();
	}
	
//	public static enum FilterOperator {
//		STARTS,CONTAINS,EXACT;
//	}
	
//	public static class Filter {
//		private String filterField;
//		
//		private String filterValue;
//		
//		private FilterOperator operator;
//
//		public Filter(String filterField, String filterValue, FilterOperator operator) {
//			
//			this.filterField = filterField;
//			this.filterValue = filterValue;
//			this.operator = operator;
//		}
//
//		public String getFilterField() {
//			return filterField;
//		}
//
//		public void setFilterField(String filterField) {
//			this.filterField = filterField;
//		}
//
//		public String getFilterValue() {
//			return filterValue;
//		}
//
//		public void setFilterValue(String filterValue) {
//			this.filterValue = filterValue;
//		}
//
//		public FilterOperator getOperator() {
//			return operator;
//		}
//
//		public void setOperator(FilterOperator operator) {
//			this.operator = operator;
//		}
//	}
	
}
