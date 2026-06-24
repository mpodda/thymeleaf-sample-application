package com.mpodda.thymeleaf_sample.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.utils.Serializer;

@Component
public class CountryDtoConverter implements Converter<String, CountryDto> {

	@Override
	@Nullable
	public CountryDto convert(String countryDtoJson) {
		return Serializer.jsonStringToObject(countryDtoJson, CountryDto.class);
	}
}
