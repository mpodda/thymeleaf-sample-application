package com.mpodda.thymeleaf_sample.domain.dto;

import com.mpodda.thymeleaf_sample.domain.dto.interfaces.IFilterableDto;
import com.mpodda.thymeleaf_sample.domain.entities.Country;

public class CountryDto extends BaseIdentifiableDto<Country, CountryDto> implements IFilterableDto {
	private static final long serialVersionUID = -3008445710518613613L;
	
	private String name;
	
	private ContinentDto continent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContinentDto getContinent() {
		return continent;
	}

	public void setContinent(ContinentDto continent) {
		this.continent = continent;
	}

	@Override
	public String id() {
		return this.isNewEntry() ? null : String.valueOf(this.getId());
	}

	@Override
	public String label() {
		return this.name;
	}
}
