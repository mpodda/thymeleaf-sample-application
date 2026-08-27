package com.mpodda.thymeleaf_sample.thymeleaf;

import java.util.Map;
import java.util.Optional;

import org.thymeleaf.context.IContext;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.domain.enums.FilterOperators;
import com.mpodda.thymeleaf_sample.domain.model.FilterModel;

public final class FilterUtils {
	public static int addFilterField(IContext context, final String sessionAttribute, final String fieldName, final String filterOperation) {
		Optional<PagingSortingAndFilteringDto> dto = getPagingSortingAndFilteringDto(context, sessionAttribute);
		
		if (dto.isPresent()) {
			final FilterModel filter = new FilterModel(fieldName, FilterOperators.valueOf(filterOperation));
			dto.get().addFilter(filter);
			return dto.get().getFilters().indexOf(filter);
		}
		
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	private static Optional <PagingSortingAndFilteringDto> getPagingSortingAndFilteringDto(IContext context, final String sessionAttribute) {
		final Map<String, PagingSortingAndFilteringDto> pagingSortingAndFilteringDtoMap = (Map<String, PagingSortingAndFilteringDto>)context.getVariable("pagingAndSortingDto");
		
		if (pagingSortingAndFilteringDtoMap != null && sessionAttribute != null) {
			return Optional.ofNullable(pagingSortingAndFilteringDtoMap.get(sessionAttribute));
		}
		
		return Optional.empty();
	}
}
