package com.mpodda.thymeleaf_sample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PagingAndSortingDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

@Component
public class PagingAndSortingDtoConverter implements Converter<String, PagingAndSortingDto> {

	@Override
	@Nullable
	public PagingAndSortingDto convert(String pagingAndSortingDtoJson) {
		return Serializer.jsonStringToObject(pagingAndSortingDtoJson, PagingAndSortingDto.class);
	}
}
