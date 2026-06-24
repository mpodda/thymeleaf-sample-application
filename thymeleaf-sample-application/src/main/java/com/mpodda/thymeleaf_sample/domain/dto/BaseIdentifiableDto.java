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
}
