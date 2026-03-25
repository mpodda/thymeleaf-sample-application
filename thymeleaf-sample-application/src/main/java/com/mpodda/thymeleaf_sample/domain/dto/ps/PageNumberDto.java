package com.mpodda.thymeleaf_sample.domain.dto.ps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mpodda.thymeleaf_sample.utils.Serializer;

public class PageNumberDto implements Serializable {
	private static final long serialVersionUID = 7035497282934375272L;
	
	private int value;
	
	private boolean isCurrentPage;
	
	private boolean isFirstPage;
	
	private boolean isLastPage;
	
	
	public PageNumberDto(int value) {
		this.value = value;
		this.isCurrentPage = false;
		this.isFirstPage = false;
		this.isLastPage = false;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isCurrentPage() {
		return isCurrentPage;
	}

	public void setCurrentPage(boolean isCurrentPage) {
		this.isCurrentPage = isCurrentPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public PageNumberDto setIsCurrentPage() {
		this.isCurrentPage = true;
		
		return this;
	}	
	
	public PageNumberDto currentPage(boolean isCurrentPage) {
		this.isCurrentPage = isCurrentPage;
		
		return this;
	}
	
	public PageNumberDto firstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
		
		return this;
	}	
	
	public PageNumberDto lastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
		
		return this;
	}
	
	@Override
	public int hashCode() {
		return this.value + (this.isCurrentPage? 1 : 0) + (this.isFirstPage? 1 : 0) + (this.isLastPage? 1 : 0);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		
		if (!(object instanceof PageNumberDto)) {
			return false;
		}
		
		final PageNumberDto pageNumberDto = (PageNumberDto)object;
		
		return this.value == pageNumberDto.getValue() && 
				(this.isCurrentPage == pageNumberDto.isCurrentPage()) && 
				(this.isFirstPage == pageNumberDto.isFirstPage()) && 
				(this.isLastPage == pageNumberDto.isLastPage());
	}
	
	@Override
	public String toString() {
		return Serializer.objectToJsonString(this);
	}
	
//	@Override
//	public String toString() {
//		StringBuilder stringValue = new StringBuilder().append("{").append("value=").append(this.value);
//		
//		if (this.isFirstPage) {
//			stringValue.append(" ,").append("isFirstPage");
//		}
//		
//		if (this.isLastPage) {
//			stringValue.append(" ,").append("isLastPage");
//		}		
//		
//		if (this.isCurrentPage) {
//			stringValue.append(" ,").append("isCurrentPage");
//		}
//		
//		stringValue.append("}");
//		
//		return stringValue.toString();
//	}
	
	 
	public static void main(String[] args) {
		System.out.println("Hello!");
		
		final int NUMBER_OF_LIMITS_PAGES = 3;
		
		List<PageNumberDto> list = new ArrayList<PageNumberDto>(10);
		list.add(new PageNumberDto(1).currentPage(true));
		list.add(new PageNumberDto(2).currentPage(false));
		list.add(new PageNumberDto(3).currentPage(false));
		list.add(new PageNumberDto(4).currentPage(false));
		list.add(new PageNumberDto(5));
		list.add(new PageNumberDto(6));
		list.add(new PageNumberDto(7));
		list.add(new PageNumberDto(8));
		list.add(new PageNumberDto(9));
		list.add(new PageNumberDto(10));
		list.add(new PageNumberDto(11));
		list.add(new PageNumberDto(12));
		list.add(new PageNumberDto(13));
		
		final int totalPages = 15;
		
		//list.
		
		List<PageNumberDto> firstPageslist = new ArrayList<PageNumberDto>(3);
		
		List<PageNumberDto> lastPageslist = new ArrayList<PageNumberDto>(3);
		
//		System.out.println(String.format("List contains page 17: %s", list.contains(new PageNumberDto(17))));
		
		if (totalPages > NUMBER_OF_LIMITS_PAGES) {
			/*
			List<Integer> firstPagesNumbers = new ArrayList<Integer>(3);
			firstPagesNumbers.add(1);
			firstPagesNumbers.add(2);
			firstPagesNumbers.add(3);
			*/
			List<Integer> firstPagesNumbers = new ArrayList<Integer>(NUMBER_OF_LIMITS_PAGES);
			for (int pageNumber = 1; pageNumber <= NUMBER_OF_LIMITS_PAGES; pageNumber++) {
				firstPagesNumbers.add(pageNumber);
			}
			
			System.out.println(String.format("firstPagesNumbers: %s", firstPagesNumbers));
			
			boolean containsAtLeastOne = false;
			
			//First Pages
			for (Integer pageNumber : firstPagesNumbers) {
				if (list.stream().filter(pnDto -> pnDto.getValue() == pageNumber).count() > 0) {
					containsAtLeastOne = true;
					break;
				}
			} 
			
			if (!containsAtLeastOne) {
				firstPagesNumbers.forEach(pn -> {
					firstPageslist.add(new PageNumberDto(pn));
				});
			}
			
			//Last Pages
			containsAtLeastOne = false;
			
			/*
			List<Integer> lastPagesNumbers = new ArrayList<Integer>(3);
			lastPagesNumbers.add(totalPages - 2);
			lastPagesNumbers.add(totalPages - 1);
			lastPagesNumbers.add(totalPages);
			*/
			List<Integer> lastPagesNumbers = new ArrayList<Integer>(NUMBER_OF_LIMITS_PAGES);
			for (int pageNumber = (totalPages - NUMBER_OF_LIMITS_PAGES + 1); pageNumber <= totalPages;  pageNumber++) {
				lastPagesNumbers.add(pageNumber);
			}
			
			System.out.println(String.format("lastPagesNumbers: %s", lastPagesNumbers));
			
			for (Integer pageNumber : lastPagesNumbers) {
				if (list.stream().filter(pnDto -> pnDto.getValue() == pageNumber).count() > 0) {
					containsAtLeastOne = true;
					break;
				}
			} 
			
			if (!containsAtLeastOne) {
				lastPagesNumbers.forEach(pn -> {
					lastPageslist.add(new PageNumberDto(pn));
				});
			}
			
			System.out.println(String.format("firstPageslist: %s", firstPageslist));
			System.out.println(String.format("lastPageslist: %s", lastPageslist));
		}
		
		
	}
//	public static void main(String[] args) {
//		int MAX_NUMBER_OF_PAGES_TO_SHOW = 10;
//		final int currentPage = 5;
//		
//		int pageCount = 11;
//		
//		int listInitialCapacity = pageCount;
//		if (pageCount > MAX_NUMBER_OF_PAGES_TO_SHOW) {
//			listInitialCapacity = MAX_NUMBER_OF_PAGES_TO_SHOW;
//			listInitialCapacity++;
//		}
//		
//		List<PageNumberDto> pageNumbersList = new ArrayList<PageNumberDto>(listInitialCapacity);
//		
//		List<Integer> allPageNumbers = new ArrayList<Integer>(pageCount);
//		
//		for (int pageNumber = 1; pageNumber<=pageCount; pageNumber++) {
//			allPageNumbers.add(Integer.valueOf(pageNumber));
//		}
//		
//		
//		System.out.println(String.format("allPageNumbers: %s", allPageNumbers));
//		System.out.println(String.format("currentPage: %s", currentPage));
//		
//		int positionInList = allPageNumbers.indexOf(currentPage);
//		System.out.println(String.format("positionInList: %s", positionInList));
//
//		if (pageCount <= MAX_NUMBER_OF_PAGES_TO_SHOW) {
//			List<Integer> pageNumbers = allPageNumbers;
//			System.out.println(String.format("pageNumbers: %s", pageNumbers));
//		} else {		
//		
//			final int halfNumberOfPages = (MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
//			
//			
//			int startPageNumber = (currentPage - halfNumberOfPages);
//			
//			if (startPageNumber <= 0) {
//				startPageNumber = 1;
//			}
//			
//			int endPageNumber = (startPageNumber + MAX_NUMBER_OF_PAGES_TO_SHOW - 1);
//			
//			
//			if (endPageNumber > pageCount) {
//				startPageNumber = (pageCount - MAX_NUMBER_OF_PAGES_TO_SHOW) + 1;
//				endPageNumber = pageCount;
//			}
//			
//			final int startPageNumberPosition = allPageNumbers.indexOf(startPageNumber);
//			final int endPageNumberPosition = allPageNumbers.indexOf(endPageNumber);
//					
//			List<Integer> pageNumbers = allPageNumbers.subList(startPageNumberPosition, endPageNumberPosition+1);
//			System.out.println(String.format("pageNumbers: %s", pageNumbers));
//		}
//		
////		if (pageCount > MAX_NUMBER_OF_PAGES_TO_SHOW) {
////			List<Integer> allPageNumbers = new ArrayList<Integer>(pageCount);
////
////			for (int i = 1; i<=pageCount; i++) {
////				allPageNumbers.add(Integer.valueOf(i));
////			}
////			
////			System.out.println(String.format("allPageNumbers: %s", allPageNumbers));
////			
////			final int HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW = (MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
////			System.out.println(String.format("HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW: %s", HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW));
////			
////			System.out.println(String.format("currentPage: %s", currentPage));
////			
////			final int POSITION_IN_LIST = allPageNumbers.indexOf(currentPage);
////			System.out.println(String.format("currentPage position in list: %s", POSITION_IN_LIST));
////			
////			boolean isAferListMiddle = POSITION_IN_LIST > (allPageNumbers.size() / 2);
////			System.out.println(String.format("isAferListMiddle: %s", isAferListMiddle));
////			
////			if (isAferListMiddle) {
////				if (HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW <= 3) {
////					List<Integer> firstPages = allPageNumbers.subList(0, HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW);
////					System.out.println(String.format("firstPages: %s", firstPages));
////					
////					int startIndex = POSITION_IN_LIST - 1;
////					
////					if (startIndex < 0) {
////						startIndex = 0;
////					}
////					
////					System.out.println(String.format("startIndex: %s", startIndex));
////					
////					int remainig = MAX_NUMBER_OF_PAGES_TO_SHOW - HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW;
////					System.out.println(String.format("remainig: %s", remainig));
////					
////					int	endIndex = startIndex + remainig;
////					
////					
////					/*					
////					int	endIndex = startIndex + 1 + 1;
////					*/
////					
////					if (endIndex >= allPageNumbers.size()) {
////						endIndex = allPageNumbers.size() - 1;
////					}
////					
////					System.out.println(String.format("endIndex: %s", endIndex));
////					
////					int finalLength = (endIndex+1) - startIndex;
////					System.out.println(String.format("final length: %s", finalLength));
////					
////					if (finalLength < remainig) {
////						//startIndex--;
////					}
////					
////					List<Integer> lastPages = allPageNumbers.subList(startIndex, endIndex+1);
////					System.out.println(String.format("last: %s", lastPages));
////				} else {
////					
////				}
////			} else {
////				if (HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW <= 3) {
////					int startIndex = POSITION_IN_LIST - 1;
////					
////					if (startIndex < 0) {
////						startIndex = 0;
////					}
////					
////					int	endIndex = startIndex + 1 + 1;
////					
////					List<Integer> firstPages = allPageNumbers.subList(startIndex, endIndex+1);
////					System.out.println(String.format("firstPages: %s", firstPages));
////					
////					Collections.reverse(allPageNumbers);
////					List<Integer> lastPages = allPageNumbers.subList(0, HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW);
////					Collections.reverse(lastPages);
////					System.out.println(String.format("lastPages: %s", lastPages));
////				} else {
////					int startIndex = (POSITION_IN_LIST - (HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW / 2));
////					
////					if (startIndex < 0) {
////						startIndex = 0;
////					}
////					
////					int remaining =  HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW - (HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
////							
////					int	endIndex = startIndex + remaining + 1;
////					
////					List<Integer> firstPages = allPageNumbers.subList(startIndex, endIndex+1);
////					
////					Collections.reverse(allPageNumbers);
////					List<Integer> lastPages = allPageNumbers.subList(0, HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW);
////					Collections.reverse(lastPages);
////				}
////				
////				
////				/*
////				int startIndex = POSITION_IN_LIST - (HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
////				System.out.println(String.format("Initial startIndex: %s", startIndex));
////				
////				if (startIndex < 0) {
////					startIndex = startIndex + HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW;
////				}
////				
////				int endIndex = startIndex + HALF_OF_MAX_NUMBER_OF_PAGES_TO_SHOW;
////				
////				List<Integer> firstPages = allPageNumbers.subList(startIndex, endIndex);
////				System.out.println(String.format("firstPages: %s", firstPages));
////				*/
////			}
////			
////			
////			//One back and the rest ahead
////			
//////			System.out.println(MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
//////			
//////			int startPage = (currentPage - (MAX_NUMBER_OF_PAGES_TO_SHOW / 2));
//////			if (startPage <= 0) {
//////				startPage = 1;
//////			}
//////			
//////			int endPage = (startPage + (MAX_NUMBER_OF_PAGES_TO_SHOW / 2));
//////			
//////			startPage--;
//////			endPage--;
//////			
//////			System.out.println(String.format("startPage: %s, endPage: %s", startPage, endPage));
//////			
//////			List<Integer> firstPages = allPageNumbers.subList(startPage, endPage);
//////			System.out.println(String.format("firstPages: %s", firstPages));
//////			
//////			Collections.reverse(allPageNumbers);
//////			List<Integer> lastPages = allPageNumbers.subList(0, MAX_NUMBER_OF_PAGES_TO_SHOW / 2);
//////			Collections.reverse(lastPages);
//////			System.out.println(String.format("lastPages: %s", lastPages));
////			
////			
////		} else {
////			for (int i = 1; i<=pageCount; i++) {
////				pageNumbersList.add(new PageNumberDto(i));
////			}
////			
////			System.out.println(String.format("pageNumbersList: %s", pageNumbersList));
////		}
//	}	
}
