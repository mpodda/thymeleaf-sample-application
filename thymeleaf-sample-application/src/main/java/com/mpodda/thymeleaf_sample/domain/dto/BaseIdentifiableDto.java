package com.mpodda.thymeleaf_sample.domain.dto;

public class BaseIdentifiableDto extends BaseDto {
	private static final long serialVersionUID = 8551439515602948447L;
	
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
