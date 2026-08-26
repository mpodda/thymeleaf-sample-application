package com.mpodda.thymeleaf_sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mpodda.thymeleaf_sample.converters.ContinentDtoConverter;
import com.mpodda.thymeleaf_sample.converters.CountryDtoConverter;
import com.mpodda.thymeleaf_sample.converters.PageNumberDtoConverter;
import com.mpodda.thymeleaf_sample.converters.PagingAndSortingDtoConverter;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(@NonNull FormatterRegistry registry) {
		registry.addConverter(new PagingAndSortingDtoConverter());
		registry.addConverter(new PageNumberDtoConverter());
		registry.addConverter(new ContinentDtoConverter());
		registry.addConverter(new CountryDtoConverter());
	}
}
