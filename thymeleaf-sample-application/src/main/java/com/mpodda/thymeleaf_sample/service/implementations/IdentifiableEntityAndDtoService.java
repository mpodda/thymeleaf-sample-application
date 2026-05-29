package com.mpodda.thymeleaf_sample.service.implementations;

import java.util.List;
import java.util.Optional;

import com.mpodda.thymeleaf_sample.domain.dto.BaseIdentifiableDto;
import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;
import com.mpodda.thymeleaf_sample.service.interfaces.IIdentifiableDtoService;

public abstract class IdentifiableEntityAndDtoService<E extends IdentifiableEntity, DTO extends BaseIdentifiableDto<E, DTO>> extends IdentifiableEntityService<E> implements IIdentifiableDtoService<E, DTO> {
	public abstract E assignValuesFromDto(E entity, DTO dto) throws Exception;
	
	@Override
	public DTO dtoByEntityId(Long entityId) throws Exception {
		Optional <E> oEntity = this.findOne(entityId);
		
		if (oEntity.isEmpty()) {
			//TODO: Throws Exception
		}
		
		return fromEntity(oEntity.get());
	}
	
	@Override
	public List<DTO> allDto() {
		return this.fromEntityList(this.findAll());
	}
	
	@Override
	public E fromDto(DTO dto) throws Exception {
		E entity = this.create();
		
		if (!dto.isNewEntry()) {
			Optional<E> oEntity = this.findOne(dto.getId());
			
			if (oEntity.isEmpty()) {
				//TODO: Throw Exception
			}
			
			entity.setId(oEntity.get().getId());
			entity.setVersion(oEntity.get().getVersion());
		}
		
		return this.assignValuesFromDto(entity, dto);
	}
	
	@Override
	public DTO saveFromDto(DTO dto) throws Exception {
		return this.fromEntity(this.save(fromDto(dto)));
	}
}
