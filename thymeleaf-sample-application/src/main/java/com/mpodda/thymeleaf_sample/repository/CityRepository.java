package com.mpodda.thymeleaf_sample.repository;

import org.springframework.stereotype.Repository;

import com.mpodda.thymeleaf_sample.domain.entities.City;

@Repository
public class CityRepository extends AbstractJpaDao<City> {

	@Override
	protected Class<City> getEntityBean() {
		return City.class;
	}
}
