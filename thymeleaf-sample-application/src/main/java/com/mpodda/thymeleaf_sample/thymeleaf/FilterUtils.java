package com.mpodda.thymeleaf_sample.thymeleaf;

import java.util.Map;
import java.util.Optional;

import org.thymeleaf.context.IContext;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.domain.enums.FilterOperators;
import com.mpodda.thymeleaf_sample.domain.model.FilterModel;

public final class FilterUtils {
	public static int addFilterField(IContext context, final String sessionAttribute, final String fieldName, final String filterOperation) {
		//Map<String, PagingSortingAndFilteringDto> pagingSortingAndFilteringDtoMap = (Map<String, PagingSortingAndFilteringDto>)context.getVariable("pagingAndSortingDto");
		
		//System.out.println(String.format("sessionAttribute=%s, fieldName=%s", sessionAttribute, fieldName));
		//System.out.println(String.format("pagingSortingAndFilteringDtoMap: %s", Serializer.objectToJsonString(pagingSortingAndFilteringDtoMap)));
		
		/*
		if (pagingSortingAndFilteringDtoMap != null) {
			if (sessionAttribute != null) {
				PagingSortingAndFilteringDto pagingSortingAndFilteringDto = pagingSortingAndFilteringDtoMap.get(sessionAttribute);
				
				if (pagingSortingAndFilteringDto != null) {
					//pagingSortingAndFilteringDto.addFilter(new PagingSortingAndFilteringDto.Filter(fieldName, "", PagingSortingAndFilteringDto.FilterOperator.CONTAINS));
					pagingSortingAndFilteringDto.addFilter(new FilterModel(fieldName, "", PagingSortingAndFilteringDto.FilterOperator.CONTAINS));
				}
				
//				PagingSortingAndFilteringDto dto = (PagingSortingAndFilteringDto)context.getVariable("filterDto");
//				dto = pagingSortingAndFilteringDto;
			}
		}
		*/
		
		
		/*
		FilterDto filterDto = (FilterDto)context.getVariable("filterDto");
		if (filterDto != null) {
			filterDto.addFilter(new FilterModel(fieldName, "", PagingSortingAndFilteringDto.FilterOperator.CONTAINS));
		}
		*/
		
		Optional<PagingSortingAndFilteringDto> dto = getPagingSortingAndFilteringDto(context, sessionAttribute); //.ifPresent(dto -> dto.addFilter(new FilterModel(fieldName, PagingSortingAndFilteringDto.FilterOperator.CONTAINS)));
		
		if (dto.isPresent()) {
			final FilterModel filter = new FilterModel(fieldName, FilterOperators.valueOf(filterOperation));
			dto.get().addFilter(filter);
			return dto.get().getFilters().indexOf(filter);
		}
		
		return -1;
	}
	
//	public static List<FilterModel> getFilters(IContext context, final String sessionAttribute) {
//		Map<String, PagingSortingAndFilteringDto> pagingSortingAndFilteringDtoMap = (Map<String, PagingSortingAndFilteringDto>)context.getVariable("pagingAndSortingDto");
//		
//		if (pagingSortingAndFilteringDtoMap != null) {
//			if (sessionAttribute != null) {
//				return pagingSortingAndFilteringDtoMap.get(sessionAttribute).getFilters();
//			}
//		}
//		
//		return new ArrayList<FilterModel>();
//	}
	
//	public static Integer getFieldIndex(IContext context, final String sessionAttribute, final String fieldName) {
//		Optional<PagingSortingAndFilteringDto> dto = getPagingSortingAndFilteringDto(context, sessionAttribute);
//		
//		//System.out.println(String.format("dto.get().getFilters().size()=%s", dto.get().getFilters().size()));
//		
//		if (dto.isPresent()) {
//			for (Integer i = 0; i < dto.get().getFilters().size(); i++) {
//				if (dto.get().getFilters().get(i).getFilterField().equalsIgnoreCase(fieldName)) {
//					return i;
//				}
//			}
//		}
//		
//		return -1;
//	}
	
//	public static String getIndex() {
//		
//		return "69";
//	}
	
	@SuppressWarnings("unchecked")
	private static Optional <PagingSortingAndFilteringDto> getPagingSortingAndFilteringDto(IContext context, final String sessionAttribute) {
		final Map<String, PagingSortingAndFilteringDto> pagingSortingAndFilteringDtoMap = (Map<String, PagingSortingAndFilteringDto>)context.getVariable("pagingAndSortingDto");
		
		if (pagingSortingAndFilteringDtoMap != null && sessionAttribute != null) {
			return Optional.ofNullable(pagingSortingAndFilteringDtoMap.get(sessionAttribute));
		}
		
		return Optional.empty();
	}
}
