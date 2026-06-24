package com.mpodda.thymeleaf_sample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

@Component
public class ContinentDtoConverter implements Converter<String, ContinentDto> {

	@Override
	@Nullable
	public ContinentDto convert(String continentDtoJson) {
		return Serializer.jsonStringToObject(continentDtoJson, ContinentDto.class);
	}

}
