package com.mpodda.thymeleaf_sample.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "thysa_person_addresses")
public class PersonAddress extends IdentifiableEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="paddr_pid")
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="paddr_addrid")
	private Address address;
	
}
