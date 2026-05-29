package com.mpodda.thymeleaf_sample.repository;

import org.springframework.stereotype.Repository;

import com.mpodda.thymeleaf_sample.domain.entities.Country;

@Repository
public class CountryRepository extends AbstractJpaDao<Country> {

	@Override
	protected Class<Country> getEntityBean() {
		return Country.class;
	}

	public boolean isNameExists(String name) {
		return ((Number)this.entityManager.createQuery("select count(country) from " + this.entityName() + " country where country.name = :name").setParameter("name", name).getSingleResult()).intValue() > 0;
	}
}
