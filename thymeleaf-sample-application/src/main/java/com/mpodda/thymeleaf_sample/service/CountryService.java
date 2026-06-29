package com.mpodda.thymeleaf_sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.CountryDto;
import com.mpodda.thymeleaf_sample.domain.entities.Continent;
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
			countryDto.setContinent(this.continentService.dtoDefaultInstance());
		}
		
		return countryDto;
	}

	@Override
	public CountryDto fromEntity(Country country) {
		CountryDto countryDto = new CountryDto();
		
		countryDto.setId(country.getId());
		countryDto.setName(country.getName());

		try {
			countryDto.setContinent(this.continentService.fromEntity(country.getContinent()));
		} catch (Exception e) {
			countryDto.setContinent(this.continentService.fromEntity(new Continent().id(country.getContinent().getId())));
		}
		
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
	
	public boolean isNameExists(String name) {
		return this.countryRepository.isNameExists(name);
	}
	
	public List<Country> findAllFullLoad() {
		final List<Continent> continentsList = this.continentService.findAll();
		List<Country> countriesList = this.findAll();
		
		countriesList.forEach (
			country -> {
				country.setContinent(continentsList.stream().filter(continent -> continent.getId().equals(country.getContinent().getId())).findFirst().orElse(null));	
			}
		);
		
		return countriesList;
	}
}
