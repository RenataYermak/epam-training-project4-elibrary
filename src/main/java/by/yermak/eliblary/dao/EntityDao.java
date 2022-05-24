package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E> {

    Optional<E> find(Long id) throws DaoException;

    List<E> findAll() throws DaoException;

    Optional<E> create(E entity) throws DaoException;

    Optional<E> update(E entity) throws DaoException;

    void delete(Long id) throws DaoException;
}
