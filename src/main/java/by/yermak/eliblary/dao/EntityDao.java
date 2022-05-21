package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.dao.exception.DaoException;

import by.yermak.eliblary.model.user.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public interface EntityDao<E> {
    Logger LOGGER = LogManager.getLogger();
    Connection connection = ConnectionPool.getInstance().getConnection();

   Optional< E> find(Long id) throws DaoException;

    List<E> findAll() throws DaoException;

    Optional<E> create(E entity) throws DaoException;

    Optional<E> update(E entity) throws DaoException;

    void delete(Long id) throws DaoException;

    default PreparedStatement getPrepareStatement(String sql) {
        LOGGER.log(Level.INFO, "get prepare statement");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method getPrepareStatement: ", e);
        }
        return preparedStatement;
    }
}
