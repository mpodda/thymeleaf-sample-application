package com.mpodda.thymeleaf_sample.domain.dto;

import com.mpodda.thymeleaf_sample.domain.entities.Continent;

public class ContinentDto extends BaseIdentifiableDto  {
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
	
	public static ContinentDto newInstance () {
//		return new ContinentDto().name("Marcello");
		return new ContinentDto();
	}
	
	public static ContinentDto fromEntity(Continent continent) {
		ContinentDto continentDto = new ContinentDto();
		
		continentDto.setId(continent.getId());
		continentDto.setName(continent.getName());
		
		return continentDto;
	}
}
