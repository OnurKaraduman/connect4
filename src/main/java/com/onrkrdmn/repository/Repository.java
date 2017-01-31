package com.onrkrdmn.repository;

import com.onrkrdmn.domain.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

/**
 * Created by onur on 22.01.17.
 * Base operations with base properties
 * All mongodb repository class should generated from {@link Repository}
 */
@NoRepositoryBean
public interface Repository<T extends Entity> extends MongoRepository<T, String> {

    /**
     * find the entity by created date
     *
     * @param createdDate
     * @return
     */
    T findByCreatedDate(Date createdDate);

    /**
     * find the data by updated date
     *
     * @param updatedDate
     * @return
     */
    T findByUpdatedDate(Date updatedDate);


    /**
     * find the entity by id
     *
     * @param id
     * @return
     */
    T findById(long id);

    /**
     * find the list of entity by active state
     *
     * @param active
     * @return
     */
    List<T> findByActive(boolean active);
}
