package com.mpodda.thymeleaf_sample.domain.dto;

import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;

public class BaseIdentifiableDto<E extends IdentifiableEntity, DTO extends BaseIdentifiableDto<E, DTO>> extends BaseDto {
	private static final long serialVersionUID = 8551439515602948447L;

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isNewEntry() {
		return this.id == null;
	}
	
//	public static <E extends IdentifiableEntity, DTO extends BaseIdentifiableDto<E, DTO>> BaseIdentifiableDto<E, DTO> getInstance() {
//		return new BaseIdentifiableDto<E, DTO>();
//	}
	
//	public static <E extends IdentifiableEntity, DTO extends BaseIdentifiableDto<E, DTO>> DTO fromEntity(E entity) {
//		return null;
//	}
	
//	public static <E extends IdentifiableEntity, DTO extends BaseIdentifiableDto<E, DTO>> List<DTO> fromEntityList(List<E> entityList) {
//		List<DTO> dtoList = new ArrayList<DTO>(entityList == null ? 0 : entityList.size());
//		
//		if (entityList != null) {
//			entityList.forEach (
//				e -> dtoList.add(fromEntity(e))
//			);
//		}
//		
//		return dtoList;		
//	}
}
