package com.mpodda.thymeleaf_sample.domain.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableModel implements Pageable {
	private int totalItems = 0;
	private int currentPage = 1;
	private int pageSize = 1;
	private int numberOfPages = 1;
	private Sort sort;

	public PageableModel(int totalItems, int currentPage, int pageSize) {
		this.totalItems = totalItems;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.numberOfPages = this.calculateNumberOfPages();
		
		if (this.numberOfPages < currentPage) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;	
		}
		
		
	}

	@Override
	public int getPageNumber() {
		return this.currentPage;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}
	
	public int getFirstPageNumber() {
		return 1;
	}
	
	public int getPreviousPageNumber() {
		return this.previousOrFirst().getPageNumber();
	}
	
	public int getNextPageNumber() {
		if (this.hasNext()) {
			return this.currentPage + 1;
		}
		
		return this.getLastPageNumber();
	}
	
	public int getLastPageNumber() {
		return this.numberOfPages;
	}

	@Override
	public long getOffset() {
		return ((this.currentPage - 1) * this.pageSize + 1)-1;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	@Override
	public Pageable next() {
		if (this.hasNext()) {
			return new PageableModel(this.totalItems, this.currentPage + 1, this.pageSize);
		}
		
		return this;
	}

	@Override
	public Pageable previousOrFirst() {
		if (this.hasPrevious()) {
			return new PageableModel(this.totalItems, this.currentPage - 1, this.pageSize);
		}
		
		return this.first();
	}

	@Override
	public Pageable first() {
		return new PageableModel(this.totalItems, 1, this.pageSize);
	}

	@Override
	public Pageable withPage(int pageNumber) {
		if (pageNumber <= this.numberOfPages) {
			return new PageableModel(this.totalItems, pageNumber, this.pageSize);
		}
		
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return this.currentPage > 1;
	}
	
	public boolean hasNext() {
		return this.currentPage < this.numberOfPages;
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}
	
	public int getTotalItems() {
		return totalItems;
	}

	private int calculateNumberOfPages() {
		if (this.totalItems == 0) {
			return 1;
		}
		
		if (this.totalItems % this.pageSize == 0) {
			return this.totalItems / this.pageSize;
		}
		
		return (this.totalItems / this.pageSize) + 1;
	}	
}
