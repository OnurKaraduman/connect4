package com.onrkrdmn.service;


import com.onrkrdmn.domain.Entity;

import java.util.List;

/**
 * Created by onur on 23.01.17.
 * Business level operations
 */
public interface Service<T extends Entity> {
    /**
     * list all the datas
     *
     * @return
     */
    public List<T> list();

    /**
     * find the data by id
     *
     * @param id
     * @return
     */
    public T findById(String id);

    /**
     * save the data
     *
     * @param entity
     * @return
     */
    public T save(T entity);

    /**
     * delete the data by id
     *
     * @param entity
     * @return
     */
    public T delete(T entity);
}
