package com.mpodda.thymeleaf_sample.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "thysa_continents")
public class Continent extends IdentifiableEntity {
	
	@Column(name = "cont_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Continent id(Long id) {
		this.setId(id);
		
		return this;
	}
	
	public Continent name(String name) {
		this.setName(name);
		
		return this;
	}
	
	@Override
	public String toString() {
		return super.toJson();
	}
}
