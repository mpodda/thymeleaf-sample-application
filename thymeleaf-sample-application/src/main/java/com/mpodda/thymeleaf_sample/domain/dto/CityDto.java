package com.mpodda.thymeleaf_sample.domain.dto;

import com.mpodda.thymeleaf_sample.domain.entities.City;

public class CityDto extends BaseIdentifiableDto<City, CityDto> {
	private static final long serialVersionUID = -5362820259905348179L;

	private String name;
	
	private CountryDto country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}
	
	public CityDto name(String name) {
		this.name = name;
		
		return this;
	}
	
	public CityDto country(CountryDto country) {
		this.country = country;
		
		return this;
	}
}
