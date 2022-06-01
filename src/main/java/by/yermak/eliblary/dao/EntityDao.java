package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Entity dao.
 *
 * @param <E> the type entity
 */
public interface EntityDao<E> {
    /**
     * Find entity by id
     *
     * @return the optional object of a found entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> find(Long id) throws DaoException;

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
     * @return the optional object of a created entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> create(E entity) throws DaoException;

    /**
     * Updates entity
     *
     * @return the optional object of an updated entity
     * @throws DaoException if there is any problem during access
     */
    Optional<E> update(E entity) throws DaoException;

    /**
     * @param id the entity's id
     * @throws DaoException if there is any problem occurred during  access
     */
    void delete(Long id) throws DaoException;
}
