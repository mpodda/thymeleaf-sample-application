package com.mpodda.thymeleaf_sample.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;
import com.mpodda.thymeleaf_sample.repository.AbstractJpaDao;
import com.mpodda.thymeleaf_sample.service.interfaces.IIdentifiableEntityService;

import jakarta.persistence.EntityNotFoundException;

@Service
public abstract class IdentifiableEntityService<E extends IdentifiableEntity> implements IIdentifiableEntityService<E> {
	public abstract AbstractJpaDao<E> getRepository();

	public E create() {
		return this.getRepository().createEntity();
	}

	public E save(E entity) {
		return this.getRepository().merge(entity);
	}

	public void delete(E entity) {
		this.getRepository().delete(this.getRepository().findOne(entity.getId()));
	}

	public void delete(Long id) {
		this.getRepository().deleteById(id);
	}
	
	public List<E> findAll() {
		return this.getRepository().findAll();
	}

	public Optional<E> get(E entity) {
		return Optional.of(this.getRepository().findOne(entity.getId()));
	}

	public Optional<E> findOne(Long id) throws EntityNotFoundException {
		return Optional.ofNullable(this.getRepository().findOne(id));
	}

	@Override
	public boolean hasRecords() {
		return this.getRepository().countRecords() > 0;
	}
	
	@Override
	public int countRecords() {
		return this.getRepository().countRecords();
	}
	
	@Override
	public boolean recordExists(Long id) {
		return this.getRepository().recordExists(id);
	}
}
