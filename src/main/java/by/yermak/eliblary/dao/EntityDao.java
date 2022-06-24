package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore  entity
 *
 * @param <E> the type of  entity
 */
public interface EntityDao<E, K> {
    /**
     * Find entity by id
     *
     * @param id is the entity's id
     * @return the optional object of a found entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> find(K id) throws DaoException;

    /**
     * Find all data from database and save in the list.
     *
     * @return the list of data
     * @throws DaoException if there is any problem during access
     */
    List<E> findAll() throws DaoException;

    /**
     * Creates a new entity.
     *
     * @param entity is the entity
     * @return the optional object of a created entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> create(E entity) throws DaoException;

    /**
     * Updates entity
     *
     * @param entity is the entity
     * @return the optional object of an updated entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> update(E entity) throws DaoException;

    /**
     * Delete entity
     *
     * @param id is the entity's id
     * @throws DaoException if there is any problem occurred during  access
     */
    void delete(K id) throws DaoException;
}
