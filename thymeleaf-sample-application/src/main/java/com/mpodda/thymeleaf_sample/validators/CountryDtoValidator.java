package com.mpodda.thymeleaf_sample.validators;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.entities.Country;
import com.mpodda.thymeleaf_sample.service.CountryService;

@Component
public class CountryDtoValidator implements Validator {
	private CountryService countryService;
	
	public CountryDtoValidator(CountryService countryService) {
		this.countryService = countryService;
	}

	@Override
	public boolean supports(@NonNull Class<?> clazz) {
		return clazz.isAssignableFrom(CountryDto.class);
	}

	@Override
	public void validate(@NonNull Object target, @NonNull Errors errors) {
		final CountryDto countryDto = (CountryDto)target;
		
		if (countryDto.getName() == null || countryDto.getName().isBlank()) {
			errors.rejectValue("name", "country.name.required", "Fallback message");
		}
		
		if (countryDto.getContinent() == null) {
			errors.rejectValue("continent", "country.continent.required", "Fallback message");
		}
		
		if (countryDto.getContinent() != null && countryDto.getContinent().getId() == null) {
			errors.rejectValue("continent", "country.continent.required", "Fallback message");
		}
		
		if (countryDto.isNewEntry()) {
			
		}
		
		if (countryNameExists(countryDto)) {
			errors.rejectValue("name", "country.name.exists", new Object[] {countryDto.getName()}, "Fallback message");
		}
	}

	private boolean countryNameExists(final CountryDto newCountryDto) {
		final boolean exists = this.countryService.isNameExists(newCountryDto.getName());
		
		/* Add New */
		if (newCountryDto.isNewEntry() && exists) {
			return true;
		}
		
		/* Edit existing */
		if (!newCountryDto.isNewEntry()) {
			final Country existingCountry = this.countryService.findOne(newCountryDto.getId()).get();
			
			/* It's about the same record */
			if (existingCountry.getId().equals(newCountryDto.getId())) {
				return false;
			}
			
			return true;
		}
		
		
		return false;
	}
}
