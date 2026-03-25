package com.mpodda.thymeleaf_sample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mpodda.thymeleaf_sample.domain.dto.ps.PageNumberDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

@Component
public class PageNumberDtoConverter implements Converter<String, PageNumberDto> {

	@Override
	@Nullable
	public PageNumberDto convert(String pageNumberDtoJson) {
		return Serializer.jsonStringToObject(pageNumberDtoJson, PageNumberDto.class);
	}
}
