package com.mpodda.thymeleaf_sample.validators;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;

@Component
public class PersonDtoValidator implements Validator {

	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return clazz.isAssignableFrom(PersonDto.class);
	}

	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		final PersonDto personDto = (PersonDto)target;
		
		if (personDto.getName() == null || personDto.getName().isBlank()) {
			errors.rejectValue("name", "person.name.required", "Fallback message");
		}
		
		if (personDto.getCountry() == null) {
			errors.rejectValue("country", "person.country.required", "Fallback message");
		}
		
		if (personDto.getDateOfBirth() == null) {
			errors.rejectValue("dateOfBirth", "person.birthdate.required", "Fallback message");
		}
		
		
	}

}
