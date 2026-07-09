package com.mpodda.thymeleaf_sample.domain.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "thysa_persons")
public class Person extends IdentifiableEntity {
	@Column(name = "p_name")
	private String name;
	
//	@Column(name = "p_age")
//	private Integer age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="p_couid")
	private Country country;
	
	@OneToMany (fetch = FetchType.LAZY)
	@JoinColumn(name="paddr_pid")
	private Set<PersonAddress> addresses;
	
	@Column(name = "p_date_of_birth")
	private LocalDate dateOfBirth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<PersonAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<PersonAddress> addresses) {
		this.addresses = addresses;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
