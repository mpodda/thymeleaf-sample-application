package com.mpodda.thymeleaf_sample.validators;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.service.ContinentService;

@Component
public class ContinentDtoValidator implements Validator {
	private ContinentService continentService;

	public ContinentDtoValidator(ContinentService continentService) {
		this.continentService = continentService;
	}

	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return clazz.isAssignableFrom(ContinentDto.class);
	}

	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		final ContinentDto continentDto = (ContinentDto)target;
		
		if (continentDto.getName() == null || continentDto.getName().isBlank()) {
			errors.rejectValue("name", "continent.name.required", "Fallback message");
		}
		
		if (/*continentDto.isNewEntry() &&*/ this.continentService.isNameExists(continentDto.getName())) {
			errors.rejectValue("name", "continent.name.exists", new Object[] {continentDto.getName()}, "Fallback message");
		}
	}
}
