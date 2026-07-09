package com.mpodda.thymeleaf_sample.service;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.CityDto;
import com.mpodda.thymeleaf_sample.domain.entities.City;
import com.mpodda.thymeleaf_sample.repository.AbstractJpaDao;
import com.mpodda.thymeleaf_sample.repository.CityRepository;
import com.mpodda.thymeleaf_sample.service.implementations.IdentifiableEntityAndDtoService;

@Service
public class CityService extends IdentifiableEntityAndDtoService<City, CityDto> {
	private static CityDto cityDto;
	
	private CityRepository cityRepository;
	
	private CountryService countryService;

	public CityService(CityRepository cityRepository, CountryService countryService) {
		this.cityRepository = cityRepository;
		this.countryService = countryService;
	}

	@Override
	public CityDto dtoDefaultInstance() {
		if (cityDto == null) {
			cityDto = new CityDto();
			cityDto.setCountry(this.countryService.dtoDefaultInstance());
		}
		
		return cityDto;
	}

	@Override
	public CityDto fromEntity(City city) {
		return new CityDto().id(city.getId()).name(city.getName()).country(this.countryService.fromEntity(city.getCountry()));
	}

	@Override
	public City assignValuesFromDto(City city, CityDto cityDto) throws Exception {
		city.setName(cityDto.getName());
		city.setCountry(this.countryService.fromDto(cityDto.getCountry()));
		
		return city;
	}

	@Override
	public AbstractJpaDao<City> getRepository() {
		return this.cityRepository;
	}
}
