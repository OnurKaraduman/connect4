package com.onrkrdmn.service;

import com.onrkrdmn.domain.Entity;
import com.onrkrdmn.repository.Repository;

import java.util.List;

/**
 * Created by onur on 23.01.17.
 * Base class for Business level operations
 */
public abstract class AbstractService<T extends Entity> implements Service<T> {

    public abstract Repository getRepository();

    public List<T> list() {
        return getRepository().findByActive(true);
    }

    public T findById(String id) {
        return (T) getRepository().findOne(id);
    }

    public T save(T entity) {
        return (T) getRepository().save(entity);
    }

    public T delete(T entity) {
        entity.setActive(false);
        return (T) getRepository().save(entity);
    }
}
