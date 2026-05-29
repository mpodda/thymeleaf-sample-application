package com.mpodda.thymeleaf_sample.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.mpodda.thymeleaf_sample.domain.dto.BaseIdentifiableDto;
import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;

public interface IIdentifiableDtoService<E extends IdentifiableEntity,  DTO extends BaseIdentifiableDto<E, DTO>> {
	public DTO dtoDefaultInstance();
	
	public DTO fromEntity(E entity);
	
	public DTO dtoByEntityId(final Long entityId) throws Exception;
	
	public E fromDto(DTO dto) throws Exception;
	
	public DTO saveFromDto(DTO dto) throws Exception;
	
	public List<DTO> allDto();
	
	default List<DTO> fromEntityList(List<E> entityList) {
		List<DTO> dtoList = new ArrayList<DTO>(entityList == null ? 0 : entityList.size());
		
		if (entityList != null) {
			entityList.forEach (
				e -> dtoList.add(fromEntity(e))
			);
		}
		
		return dtoList;
	}
}
