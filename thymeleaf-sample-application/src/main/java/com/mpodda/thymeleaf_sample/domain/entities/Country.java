package com.mpodda.thymeleaf_sample.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "thysa_countries")
public class Country extends IdentifiableEntity {
	
	@Column(name = "cou_name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cou_contid")
	private Continent continent;

}
