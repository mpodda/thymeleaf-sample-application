package com.mpodda.thymeleaf_sample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingSortingAndFilteringDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

@Component
public class PagingAndSortingDtoConverter implements Converter<String, PagingSortingAndFilteringDto> {

	@Override
	@Nullable
	public PagingSortingAndFilteringDto convert(String pagingAndSortingDtoJson) {
		return Serializer.jsonStringToObject(pagingAndSortingDtoJson, PagingSortingAndFilteringDto.class);
	}
}
