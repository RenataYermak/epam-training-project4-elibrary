package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.dao.exception.DaoException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public interface EntityDao<E, K> {
    Logger LOGGER = LogManager.getLogger();
    Connection connection = ConnectionPool.getInstance().getConnection();

    E find(K id) throws DaoException;

    List<E> findAll() throws DaoException;

    E create(E entity) throws DaoException;

    E update(E entity) throws DaoException;

    void delete(K id) throws DaoException;

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
