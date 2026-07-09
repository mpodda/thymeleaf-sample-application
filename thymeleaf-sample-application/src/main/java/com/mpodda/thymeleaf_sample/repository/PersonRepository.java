package com.mpodda.thymeleaf_sample.repository;

import org.springframework.stereotype.Repository;

import com.mpodda.thymeleaf_sample.domain.entities.Person;

@Repository
public class PersonRepository extends AbstractJpaDao<Person> {
	
	@Override
	protected Class<Person> getEntityBean() {
		return Person.class;
	}
}
