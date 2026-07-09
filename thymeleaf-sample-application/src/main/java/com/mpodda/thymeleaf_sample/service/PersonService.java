package com.mpodda.thymeleaf_sample.service;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.dto.PersonDto;
import com.mpodda.thymeleaf_sample.domain.entities.Person;
import com.mpodda.thymeleaf_sample.repository.AbstractJpaDao;
import com.mpodda.thymeleaf_sample.repository.PersonRepository;
import com.mpodda.thymeleaf_sample.service.implementations.IdentifiableEntityAndDtoService;

@Service
public class PersonService extends IdentifiableEntityAndDtoService<Person, PersonDto> {
	private static PersonDto personDto;
	
	private PersonRepository personRepository;
	
	private CountryService countryService;

	public PersonService(PersonRepository personRepository, CountryService countryService) {
		this.personRepository = personRepository;
		this.countryService = countryService;
	}

	@Override
	public PersonDto dtoDefaultInstance() {
		if (personDto == null) {
			personDto = new PersonDto();
			personDto.setCountry(this.countryService.dtoDefaultInstance());
		}
		
		return personDto;
	}

	@Override
	public PersonDto fromEntity(Person person) {
		new PersonDto()
			.id(person.getId())
			.name(person.getName())
			.dateOfBirth(person.getDateOfBirth())
			.country(this.countryService.fromEntity(person.getCountry()))
		;
		
		//TODO: Calculate age
		
		return null;
	}

	@Override
	public Person assignValuesFromDto(Person entity, PersonDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractJpaDao<Person> getRepository() {
		return this.personRepository;
	}

}
