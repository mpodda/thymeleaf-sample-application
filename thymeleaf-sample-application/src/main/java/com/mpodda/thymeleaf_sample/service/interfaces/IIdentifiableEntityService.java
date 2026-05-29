package com.mpodda.thymeleaf_sample.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.mpodda.thymeleaf_sample.domain.entities.IdentifiableEntity;

import jakarta.persistence.EntityNotFoundException;

public interface IIdentifiableEntityService<E extends IdentifiableEntity> {
    E create();

    E save(E entity);

    void delete(E entity);

    List<E> findAll();

    Optional<E> get(E entity);

    Optional<E> findOne(Long id) throws EntityNotFoundException;
    
    boolean hasRecords();
    
    int countRecords();
    
    boolean recordExists (Long id);
}
