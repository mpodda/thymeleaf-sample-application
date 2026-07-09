package com.mpodda.thymeleaf_sample.domain.dto;

import com.mpodda.thymeleaf_sample.domain.dto.interfaces.IFilterableDto;
import com.mpodda.thymeleaf_sample.domain.entities.Continent;

public class ContinentDto extends BaseIdentifiableDto<Continent, ContinentDto> implements IFilterableDto {
	private static final long serialVersionUID = -2985528019351432510L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContinentDto name(String name) {
		this.name = name;
		
		return this;
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
