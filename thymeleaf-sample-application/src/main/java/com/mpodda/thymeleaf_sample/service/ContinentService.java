package com.mpodda.thymeleaf_sample.service;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.ContinentDto;
import com.mpodda.thymeleaf_sample.domain.entities.Continent;
import com.mpodda.thymeleaf_sample.repository.AbstractJpaDao;
import com.mpodda.thymeleaf_sample.repository.ContientRepository;
import com.mpodda.thymeleaf_sample.service.implementations.IdentifiableEntityAndDtoService;

@Service
public class ContinentService extends IdentifiableEntityAndDtoService<Continent, ContinentDto> {
	static ContinentDto continentDto = null;
	
	private ContientRepository contientRepository;
	
	public ContinentService(ContientRepository contientRepository) {
		this.contientRepository = contientRepository;
	}

	@Override
	public AbstractJpaDao<Continent> getRepository() {
		return this.contientRepository;
	}
	
	public boolean isNameExists(String name) {
		return this.contientRepository.isNameExists(name);
	}

	@Override
	public ContinentDto fromEntity(Continent continent) {
		ContinentDto continentDto = new ContinentDto();
		
		continentDto.setId(continent.getId());
		continentDto.setName(continent.getName());
		
		return continentDto;
	}
	
	@Override
	public ContinentDto dtoDefaultInstance() {
		if (continentDto == null) {
			continentDto = new ContinentDto();
		}
		
		return continentDto;
	}
	
	@Override
	public Continent assignValuesFromDto(Continent continent, ContinentDto dto) {
		continent.setName(dto.getName());
		return continent;
	}
}
