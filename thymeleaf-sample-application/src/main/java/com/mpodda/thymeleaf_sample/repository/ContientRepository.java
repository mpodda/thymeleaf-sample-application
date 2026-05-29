package com.mpodda.thymeleaf_sample.repository;

import org.springframework.stereotype.Repository;

import com.mpodda.thymeleaf_sample.domain.entities.Continent;

@Repository
public class ContientRepository extends AbstractJpaDao<Continent> {

	@Override
	protected Class<Continent> getEntityBean() {
		return Continent.class;
	}
	
	public boolean isNameExists(String name) {
		return ((Number)this.entityManager.createQuery("select count(continent) from " + this.entityName() + " continent where continent.name = :name").setParameter("name", name).getSingleResult()).intValue() > 0;
	}
}
