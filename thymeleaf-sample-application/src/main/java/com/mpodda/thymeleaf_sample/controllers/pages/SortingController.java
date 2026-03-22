package com.mpodda.thymeleaf_sample.controllers.pages;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mpodda.thymeleaf_sample.domain.dto.BaseIdentifiableDto;
import com.mpodda.thymeleaf_sample.domain.dto.PagingAndSortingDto;
import com.mpodda.thymeleaf_sample.domain.enums.PagingAndSortingConstants;
import com.mpodda.thymeleaf_sample.domain.enums.SessionalConstants;
import com.mpodda.thymeleaf_sample.utils.Serializer;

import jakarta.servlet.http.HttpSession;

@Controller
//@SessionAttributes({"pagingAndSortingDto", "data"})
public class SortingController {

	@Autowired
    protected FindByIndexNameSessionRepository sessionRepository;

	@PostMapping("/sort")
	public <Dto extends BaseIdentifiableDto> String sort(Model model, @ModelAttribute PagingAndSortingDto pagingAndSortingDto, HttpSession httpSession) {
//		System.out.println(String.format("sort :: pagingAndSortingDto: %s", Serializer.objectToJsonString(pagingAndSortingDto)));
		
		pagingAndSortingDto = defineSortingDirection(pagingAndSortingDto);
		
		final String fieldName = pagingAndSortingDto.getCurrentSortField();
		final String sortDirection = pagingAndSortingDto.getCurrentSortDirection();
		
		Session session = this.sessionRepository.findById(httpSession.getId());
		
		if (session != null && session.getAttribute(SessionalConstants.DATA.value()) != null) {
			List<Dto> data = ((Map<String, List<Dto>>)session.getAttribute(SessionalConstants.DATA.value())).get(pagingAndSortingDto.getSessionAttribute());
			//data = sortData(data, fieldName, sortDirection);
			
			PagedListHolder<Dto> pagedListHolder = new PagedListHolder<Dto>(data);
			
			SortDefinition sortDefinition = new SortDefinition() {
				@Override
				public boolean isIgnoreCase() {
					return true;
				}
				
				@Override
				public boolean isAscending() {
					return sortDirection.equals(PagingAndSortingConstants.ASC.stringValue());
				}
				
				@Override
				public String getProperty() {
					return fieldName;
				}
			};
			
			pagedListHolder.setSort(sortDefinition);
			pagedListHolder.resort();
			
			pagedListHolder.setPageSize(PagingAndSortingConstants.PAGE_SIZE.intValue());
			pagedListHolder.setPage(PagingAndSortingConstants.DEFAULT_PAGE.intValue());
			
			model.addAttribute(pagingAndSortingDto.getSessionAttribute(), pagedListHolder.getPageList());
			
			//model.addAttribute(pagingAndSortingDto.getSessionAttribute(), data);
			
			//model.addAttribute(sortingDto.getSessionAttribute(), sortData(((Map<String, List<? extends BaseIdentifiableDto>>)session.getAttribute("data")).get(sortingDto.getSessionAttribute()), fieldName, sortDirection));
		}
		
		return new StringBuilder("application").append(pagingAndSortingDto.getViewName()).toString();
	}
	
	
//	private static <Dto extends BaseIdentifiableDto> List<Dto> sortData(List<Dto> data, final String fieldName, final String sortDirection) {
//		data.sort(new Comparator<Dto>() {
//			@Override
//			public int compare(Dto o1, Dto o2) {
//				try {
//					Field field1 = ReflectionUtils.findField(o1.getClass(), fieldName);
//					field1.setAccessible(true);
//					
//					Field field2 = ReflectionUtils.findField(o2.getClass(), fieldName);
//					field2.setAccessible(true);
//					
//					final Object valueO1 = field1.get(o1);
//					final Object valueO2 = field2.get(o2);
//					
//					final Class<?> clazz = field1.getType();
//					
//					if (clazz == java.lang.String.class) {
//						return getDifferenceOfStrings(valueO1.toString(), valueO2.toString(), sortDirection);
//					}
//					
//					if (isNumeric(clazz)) {
//						final Number number1 = NumberFormat.getInstance().parse(valueO1.toString());
//						final Number number2 = NumberFormat.getInstance().parse(valueO2.toString());
//						
//						return getDifferenceOfNumbers(number1, number2, sortDirection, clazz);
//					}
//					
//					if (isBoolean(clazz)) {
//						if (clazz == java.lang.Boolean.class) {
//							return getDifferenceOfBooleans((Boolean)valueO1, (Boolean)valueO2, sortDirection);
//						}
//						
//						return getDifferenceOfBooleans((boolean)valueO1, (boolean)valueO2, sortDirection);
//					}
//					
//					if (clazz == java.util.Date.class) {
//						return getDifferenceOfDates((Date)valueO1, (Date)valueO2, sortDirection);
//					}
//					
//					/* Default */
//					return getDifferenceOfStrings(valueO1.toString(), valueO2.toString(), sortDirection);
//					
//				} catch (Exception e) {
//					System.err.println(String.format("Error sorting: %s", e.getMessage()));
//				}
//				
//				return 0;
//			}
//		});
//		
//		return data;
//	}
	
//	private static boolean isNumeric(final Class<?> clazz) {
//		return (
//				clazz == java.lang.Integer.class || 
//				clazz == java.lang.Long.class || 
//				clazz == java.lang.Byte.class ||
//				clazz == int.class ||
//				clazz == long.class ||
//				clazz == byte.class
//			);
//	
//	}
	
//	private static boolean isBoolean(final Class<?> clazz) {
//		return  clazz == java.lang.Boolean.class || clazz == boolean.class;
//	}
	
//	private static int getDifferenceOfNumbers(final Number number1, final Number number2, final String sortDirection, final Class<?> clazz) {
//		/* Desc */
//		if (sortDirection.equals("desc")) {
//			if (clazz == java.lang.Long.class || clazz == long.class) {
//				if (number1.longValue() > number2.longValue()) {
//					return -1;
//				}
//				
//				return 1;
//			}
//			
//			if (clazz == java.lang.Integer.class || clazz == int.class) {
//				if (number1.intValue() > number2.intValue()) {
//					return -1;
//				}
//				
//				return 1;
//			}
//			
//			if (clazz == java.lang.Byte.class || clazz == byte.class) {
//				if (number1.byteValue() > number2.byteValue()) {
//					return -1;
//				}
//				
//				return 1;
//			}
//		}
//		
//		/* Asc */
//		if (clazz == java.lang.Long.class || clazz == long.class) {
//			if (number1.longValue() > number2.longValue()) {
//				return 1;
//			}
//			
//			return -1;
//		}
//								
//		if (clazz == java.lang.Integer.class || clazz == int.class) {
//			if (number1.intValue() > number2.intValue()) {
//				return 1;
//			}
//			
//			return -1;
//		}
//		
//		if (clazz == java.lang.Byte.class || clazz == byte.class) {
//			if (number1.intValue() > number2.intValue()) {
//				return 1;
//			}
//			
//			return -1;			
//		}
//		
//		return 0;
//	}
	
//	private static int getDifferenceOfBooleans(final Boolean booleanValue1, final Boolean booleanValue2, final String sortDirection) {
//		/* Asc */
//		if (sortDirection.equals("asc")) {
//			return (booleanValue1.compareTo(booleanValue2));
//		}
//		
//		/* Desc */
//		return booleanValue2.compareTo(booleanValue1);
//	}
	
//	private static int getDifferenceOfStrings(final String stringValue1, final String stringValue2, final String sortDirection) {
//		/* Asc */
//		if (sortDirection.equals("asc")) {
//			return stringValue1.compareToIgnoreCase(stringValue2);
//		}
//		
//		/* Desc */
//		return stringValue2.compareToIgnoreCase(stringValue1);
//	}
	
//	private static int getDifferenceOfDates(final Date dateValue1, final Date dateValue2, final String sortDirection) {
//		/* Asc */
//		if (sortDirection.equals("asc")) {
//			return dateValue1.compareTo(dateValue2);
//		}
//		
//		/* Desc */
//		return dateValue2.compareTo(dateValue1);
//	}
	
	private static PagingAndSortingDto defineSortingDirection(PagingAndSortingDto sortingDto) {
		if (sortingDto.getCurrentSortDirection() == "") {
			sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DEFALUT_SORTING_DIRECTION.stringValue());
			
			return sortingDto;
		}
		
		if (sortingDto.getCurrentSortDirection().equalsIgnoreCase(PagingAndSortingConstants.ASC.stringValue())) {
			sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DESC.stringValue());
			
			return sortingDto;
		}
		
		sortingDto.setCurrentSortDirection(PagingAndSortingConstants.DEFALUT_SORTING_DIRECTION.stringValue());
		
		return sortingDto;
	}
}
