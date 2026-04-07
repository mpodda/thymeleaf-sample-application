package com.mpodda.thymeleaf_sample.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.BaseDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PageNumberDto;
import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;
import com.mpodda.thymeleaf_sample.domain.enums.PagingAndSortingConstants;

@Service
public final class PagingAndSortingService<Dto extends BaseDto> {
	@Value("${tsa.page-size}")
	private int pageSize;
	
	@Value("${tsa.max-number-of-pages}")
	private int maxNumberOfPages;
	
	@Value("${tsa.number-of-limits-pages}")
	private int numberOfLimitsPages;

	@SuppressWarnings("null")
	public PagingAndSortingDto pagingSetup(PagingAndSortingDto pagingAndSortingDto, final List<Dto> data) {
		PagedListHolder<Dto> pagedListHolder = new PagedListHolder<Dto>(data);
		pagedListHolder.setPageSize(this.pageSize);
		pagedListHolder.setPage(PagingAndSortingConstants.DEFAULT_PAGE.intValue());
		
		pagingAndSortingDto.setPageData(pagedListHolder.getPageList());
		
		pagingAndSortingDto.setPageOffset(pagedListHolder.getPageSize());
		pagingAndSortingDto.setPageNumber(pagedListHolder.getPage() + 1);
		pagingAndSortingDto.setNumberOfPages(pagedListHolder.getPageCount());
		pagingAndSortingDto.setPreviousPageNumber(previousPageNumber(pagingAndSortingDto.getPageNumber()));
		pagingAndSortingDto.setNextPageNumber(nextPageNumber(pagingAndSortingDto.getPageNumber(), pagingAndSortingDto.getNumberOfPages()));
		
		pagingAndSortingDto.setPageNumbers(definePageNumbers(pagingAndSortingDto, this.maxNumberOfPages));
		
		pagingAndSortingDto.setFirstPageNumbers(firstPageNumbers(this.numberOfLimitsPages, pagingAndSortingDto.getNumberOfPages(), pagingAndSortingDto.getPageNumbers()));
		pagingAndSortingDto.setLastPageNumbers(lastPageNumbers(this.numberOfLimitsPages, pagingAndSortingDto.getNumberOfPages(), pagingAndSortingDto.getPageNumbers()));
		
		return pagingAndSortingDto;
	}
	
	public PagingAndSortingDto updatePagingData(PagingAndSortingDto pagingAndSortingDto, final PagedListHolder<Dto> pagedListHolder) {
		pagingAndSortingDto.setPageOffset(pagedListHolder.getPageSize());
		pagingAndSortingDto.setPageNumber(pagedListHolder.getPage() + 1);
		pagingAndSortingDto.setNumberOfPages(pagedListHolder.getPageCount());
		
		pagingAndSortingDto.setPreviousPageNumber(previousPageNumber(pagingAndSortingDto.getPageNumber()));
		pagingAndSortingDto.setNextPageNumber(nextPageNumber(pagingAndSortingDto.getPageNumber(), pagingAndSortingDto.getNumberOfPages()));
		
		pagingAndSortingDto.setPageNumbers(definePageNumbers(pagingAndSortingDto, this.maxNumberOfPages));
		
		pagingAndSortingDto.setFirstPageNumbers(firstPageNumbers(this.numberOfLimitsPages, pagingAndSortingDto.getNumberOfPages(), pagingAndSortingDto.getPageNumbers()));
		pagingAndSortingDto.setLastPageNumbers(lastPageNumbers(this.numberOfLimitsPages, pagingAndSortingDto.getNumberOfPages(), pagingAndSortingDto.getPageNumbers()));
		
		return pagingAndSortingDto;
	}
	
	private static List<Integer> firstPageNumbers(final int numberOfLimitsPages, final int totalPages, List<PageNumberDto> pageNumbers) {
		List<Integer> firstPagesNumbers = new ArrayList<Integer>(numberOfLimitsPages);
		
		if (totalPages > numberOfLimitsPages) {
			for (int pageNumber = 1; pageNumber <= numberOfLimitsPages; pageNumber++) {
				firstPagesNumbers.add(pageNumber);
			}
			
			boolean containsAtLeastOne = false;
			
			for (Integer pageNumber : firstPagesNumbers) {
				if (pageNumbers.stream().filter(pnDto -> pnDto.getValue() == pageNumber).count() > 0) {
					containsAtLeastOne = true;
					break;
				}
			} 
			
			if (containsAtLeastOne) {
				firstPagesNumbers.clear();
			}
		}
		
		return firstPagesNumbers;
	} 
	
	private static List<Integer> lastPageNumbers(final int numberOfLimitsPages, final int totalPages, List<PageNumberDto> pageNumbers) {
		List<Integer> lastPagesNumbers = new ArrayList<Integer>(numberOfLimitsPages);
		
		if (totalPages > numberOfLimitsPages) {
			for (int pageNumber = (totalPages - numberOfLimitsPages + 1); pageNumber <= totalPages;  pageNumber++) {
				lastPagesNumbers.add(pageNumber);
			}
			
			boolean containsAtLeastOne = false;
			
			for (Integer pageNumber : lastPagesNumbers) {
				if (pageNumbers.stream().filter(pnDto -> pnDto.getValue() == pageNumber).count() > 0) {
					containsAtLeastOne = true;
					break;
				}
			} 
			
			if (containsAtLeastOne) {
				lastPagesNumbers.clear();
			}
		}
		
		return lastPagesNumbers;
	}
	
	private static Integer previousPageNumber(final Integer currentPageNumber) {
		if (currentPageNumber.equals(Integer.valueOf(1))) {
			return Integer.valueOf(1);
		}
		
		return currentPageNumber - 1;
	}
	
	private static Integer nextPageNumber(final Integer currentPageNumber, final Integer pageCount) {
		if (currentPageNumber.equals(pageCount)) {
			return currentPageNumber;
		}
		
		return currentPageNumber + 1;
	}
	
	
	private static List<PageNumberDto> definePageNumbers(final PagingAndSortingDto pagingAndSortingDto, final int maxNumberOfPages) {
		List<PageNumberDto> pageNumbersList = new ArrayList<PageNumberDto>(pagingAndSortingDto.getNumberOfPages() < maxNumberOfPages ? pagingAndSortingDto.getNumberOfPages() : maxNumberOfPages);
		
		List<Integer> allPageNumbers = new ArrayList<Integer>(pagingAndSortingDto.getNumberOfPages());
		
		List<Integer> pageNumbers = null;
		
		for (int pageNumber = 1; pageNumber <= pagingAndSortingDto.getNumberOfPages(); pageNumber++) {
			allPageNumbers.add(Integer.valueOf(pageNumber));
		}
		
		if (pagingAndSortingDto.getNumberOfPages() < maxNumberOfPages) {
			pageNumbers = allPageNumbers;
		} else {
			final int halfNumberOfPages = (maxNumberOfPages / 2);
			
			int firstPageNumber = (pagingAndSortingDto.getPageNumber() - halfNumberOfPages);
			
			if (firstPageNumber <= 0) {
				firstPageNumber = 1;
			}
			
			int lastPageNumber = (firstPageNumber + maxNumberOfPages - 1);
			
			if (lastPageNumber > pagingAndSortingDto.getNumberOfPages()) {
				firstPageNumber = (pagingAndSortingDto.getNumberOfPages() - maxNumberOfPages) + 1;
				lastPageNumber = pagingAndSortingDto.getNumberOfPages();
			}
			
			final int firstPageNumberPosition = allPageNumbers.indexOf(firstPageNumber);
			final int lastPageNumberPosition = allPageNumbers.indexOf(lastPageNumber);
			
			pageNumbers = allPageNumbers.subList(firstPageNumberPosition, lastPageNumberPosition + 1);
		}
		
		System.out.println(String.format("Current Page Number: %s", pagingAndSortingDto.getPageNumber()));
		
		pageNumbers.forEach(pageNumber -> {
			pageNumbersList.add (
				new PageNumberDto(pageNumber)
					.currentPage(pageNumber.equals(pagingAndSortingDto.getPageNumber()))
					.firstPage(pageNumber.equals(1))
					.lastPage(pageNumber.equals(pagingAndSortingDto.getNumberOfPages())
				)
			);
		});
		
		System.out.println(String.format("pageNumbers: %s", pageNumbers));
		
		System.out.println(String.format("Current Page: %s", pageNumbersList.stream().filter(pgNrDto -> pgNrDto.isCurrentPage()).findFirst())); 
		
		
		return pageNumbersList;
	}
	
	/*
	private static List<PageNumberDto> definePageNumbers(PagingAndSortingDto pagingAndSortingDto, final int maxNumberOfPages) {
		List<PageNumberDto> pageNumbersList = new ArrayList<PageNumberDto>(maxNumberOfPages);
		
		final int[] pageNumberRange = definePageNumberRange(pagingAndSortingDto, maxNumberOfPages);
		for (int pageNumber = pageNumberRange[0]; pageNumber <= pageNumberRange[1]; pageNumber++) {
			pageNumbersList.add(new PageNumberDto(pageNumber).currentPage(pageNumber == pagingAndSortingDto.getPageNumber()));
		}
		
		return pageNumbersList;
	}
	*/
	
	/*
	private static int[] definePageNumberRange(final PagingAndSortingDto pagingAndSortingDto, final int maxNumberOfPages) {
		int[] returnValues = {1, pagingAndSortingDto.getNumberOfPages()};
		
		if (pagingAndSortingDto.getNumberOfPages() <= maxNumberOfPages) {
			return returnValues;
		}
		
		final int halfNumberOfPages = (maxNumberOfPages / 2);
		
		int startPageNumber = (pagingAndSortingDto.getPageNumber() - halfNumberOfPages);
		
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}
		
		int endPageNumber = (startPageNumber + maxNumberOfPages - 1);
		
		if (endPageNumber > pagingAndSortingDto.getNumberOfPages()) {
			startPageNumber = (pagingAndSortingDto.getNumberOfPages() - maxNumberOfPages) + 1;
			endPageNumber = pagingAndSortingDto.getNumberOfPages();
		}
		
		returnValues[0] = startPageNumber;
		returnValues[1] = endPageNumber;
		
		return returnValues;
	}
	*/
}
