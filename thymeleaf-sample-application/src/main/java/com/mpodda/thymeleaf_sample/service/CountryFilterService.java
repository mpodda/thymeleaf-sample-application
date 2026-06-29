package com.mpodda.thymeleaf_sample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.entities.Continent;
import com.mpodda.thymeleaf_sample.domain.entities.Country;
import com.mpodda.thymeleaf_sample.utils.Serializer;

import jakarta.annotation.PostConstruct;

@Service
public class CountryFilterService {
	private List<Country> countriesList = null;
	
	private CountryService countryService;
	
	private ContinentService continentService;


	public CountryFilterService(List<Country> countriesList, CountryService countryService,
			ContinentService continentService) {
		this.countriesList = countriesList;
		this.countryService = countryService;
		this.continentService = continentService;
	}

	@PostConstruct
	private void init() {
		this.refresh();
	}
	
	public void refresh() {
		this.countriesList = this.countryService.findAll();
	}
	
	public List<Country> filterByName(final String name) {
		return this.countriesList.stream().filter(c -> c.getName().contains(name)).toList();
	}
	
	public List<Country> filterByContinent(ContinentDto continentDto) throws Exception {
		return this.countriesList.stream().filter(
				country ->
		        country.getContinent() != null &&
		        		continentDto.getId().equals(country.getContinent().getId())
		).toList();
	}
}
