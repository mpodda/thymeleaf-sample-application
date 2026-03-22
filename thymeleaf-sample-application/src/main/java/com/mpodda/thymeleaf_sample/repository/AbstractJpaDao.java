package com.mpodda.thymeleaf_sample.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public abstract class AbstractJpaDao<T extends IdentifiableEntity> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	

	protected abstract Class<T> getEntityBean();

	public String entityName() {
		return this.getEntityBean().getSimpleName();
	}

	public T createEntity() {
		try {
			return this.getEntityBean().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	public T findOne(Long id) {
		return this.entityManager.find(this.getEntityBean(), id);
	}

	public List<T> findAll() {
		return this.entityManager.createQuery("from " + this.getEntityBean().getName(), this.getEntityBean()).getResultList();
	}

	public void persist(T entity) {
		this.entityManager.persist(entity);
	}

	public T merge(T entity) {
		return this.entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}

	public void deleteById(Long entityId) {
		T entity = findOne(entityId);
		delete(entity);
	}
	
	public int countRecords() {
		return ((Number)this.entityManager.createQuery("select count(entity) from " + this.entityName() + " entity").getSingleResult()).intValue();
	}
	
	public boolean recordExists(Long id) {
		return ((Number)this.entityManager.createQuery("select count(entity) from " + this.entityName() + " entity where entity.id = :id").setParameter("id", id).getSingleResult()).intValue() > 0;
	}
}