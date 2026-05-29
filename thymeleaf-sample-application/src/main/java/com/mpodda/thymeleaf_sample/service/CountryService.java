package com.mpodda.thymeleaf_sample.service;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.entities.Country;
import com.mpodda.thymeleaf_sample.repository.AbstractJpaDao;
import com.mpodda.thymeleaf_sample.repository.CountryRepository;
import com.mpodda.thymeleaf_sample.service.implementations.IdentifiableEntityAndDtoService;

@Service
public class CountryService extends IdentifiableEntityAndDtoService<Country, CountryDto> {
	static CountryDto countryDto;
	
	private CountryRepository countryRepository;
	private ContinentService continentService;


	public CountryService(CountryRepository countryRepository, ContinentService continentService) {
		this.countryRepository = countryRepository;
		this.continentService = continentService;
	}

	@Override
	public CountryDto dtoDefaultInstance() {
		if (countryDto == null) {
			countryDto = new CountryDto();
		}
		
		return countryDto;
	}

	@Override
	public CountryDto fromEntity(Country country) {
		CountryDto countryDto = new CountryDto();
		
		countryDto.setId(country.getId());
		countryDto.setName(country.getName());
		
		countryDto.setContinent(this.continentService.fromEntity(country.getContinent()));
		
		return countryDto;
	}

	@Override
	public Country assignValuesFromDto(Country country, CountryDto dto) throws Exception {
		country.setName(dto.getName());
		country.setContinent(this.continentService.fromDto(dto.getContinent()));
		
		return country;
	}

	@Override
	public AbstractJpaDao<Country> getRepository() {
		return this.countryRepository;
	}
}
