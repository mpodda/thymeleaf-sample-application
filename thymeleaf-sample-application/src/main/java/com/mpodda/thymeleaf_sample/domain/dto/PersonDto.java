package com.mpodda.thymeleaf_sample.domain.dto;

import java.time.LocalDate;
import java.util.Date;

import com.mpodda.thymeleaf_sample.domain.entities.Person;

public class PersonDto extends BaseIdentifiableDto<Person, PersonDto> {
	private static final long serialVersionUID = 6849199190478217393L;
	
	private String name;
	
	private Integer age;
	
	private CountryDto country;
	
	private LocalDate dateOfBirth;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}
	
	public PersonDto id(Long id) {
		this.setId(id);
		
		return this;
	}
	
	public PersonDto name(String name) {
		this.name = name;
		
		return this;
	}
	
	public PersonDto age(Integer age) {
		this.age = age;
		
		return this;
	}
	
	public PersonDto dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		
		return this;
	}
	
	public PersonDto country(CountryDto country) {
		this.country = country;
		
		return this;
	}
}
