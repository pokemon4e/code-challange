package com.social.app.data.dao;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * An interface for generic operations on repository for a specific type.
 *
 * @param <T>  the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
@Repository
public interface IRepository<T, ID> {

    /**
     * Retrieves an entity by its username.
     *
     * @param username the username of the entity - must not be null
     * @return the entity with given username or Optional.empty() if not found
     * @throws IllegalArgumentException if username is null
     */
    Optional<T> findByUsername(String username);

    /**
     * Returns all instances of the type.
     *
     * @return all instance of the type
     */
    Collection<T> findAll();

    /**
     * Saves a given entity. Use the returned instance for further operations.
     * If user with same username was already saved the old value is replaced.
     *
     * @param object the entity to be save - must not be null
     * @return the saved entity - will never be null
     * @throws IllegalArgumentException if entity is null
     */
    T save(T object);
}
