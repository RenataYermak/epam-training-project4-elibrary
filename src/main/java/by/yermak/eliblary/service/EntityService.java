package by.yermak.eliblary.service;

import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;

/**
 * Interface describes  the behavior of  entity.
 *
 * @param <E> the type of  entity
 */

public interface EntityService<E, K> {
    /**
     * Find Entity instance by <tt>id</tt>
     *
     * @param id is the entity's id
     * @return Entity instance
     * @throws ServiceException if Entity with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching Entity
     *                          into the data source
     */
    E find(K id) throws ServiceException;

    /**
     * Find all Entity list
     *
     * @return all entities list
     * @throws ServiceException if Entity in empty occurs after searching Entity
     *                          into the data source
     */
    List<E> findAll() throws ServiceException;

    /**
     * Update Entity with filled fields
     *
     * @param entity is the entity
     * @return Entity instance
     * @throws ServiceException if an error occurs while writing new Entity into data source
     */
    E update(E entity) throws ServiceException;

    /**
     * Delete Entity with filled fields
     *
     * @param id is the entity's id
     * @throws ServiceException if Entity with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching Entity
     *                          into the data source
     */
    void delete(K id) throws ServiceException;
}

