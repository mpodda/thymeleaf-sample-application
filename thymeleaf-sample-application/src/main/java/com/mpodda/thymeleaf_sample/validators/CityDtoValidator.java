package com.mpodda.thymeleaf_sample.validators;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpodda.thymeleaf_sample.domain.dto.CityDto;

@Component
public class CityDtoValidator implements Validator {
	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return clazz.isAssignableFrom(CityDto.class);
	}

	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		final CityDto cityDto = (CityDto)target;
		
		if (cityDto.getName() == null || cityDto.getName().isBlank()) {
			errors.rejectValue("name", "city.name.required", "Fallback message");
		}
		
		if (cityDto.getCountry() == null) {
			errors.rejectValue("country", "city.country.required", "Fallback message");
		}
	}

}
