package com.epam.yermak.project.dao;

import com.epam.yermak.project.dao.config.ConnectionPool;
import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.dao.config.exception.ConnectionPoolException;

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

    default void setIntOrNull(PreparedStatement preparedStatement, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(parameterIndex, Types.INTEGER);
        } else {
            preparedStatement.setInt(parameterIndex, value);
        }
    }

    default Integer returnResultSetIntValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) != null ? rs.getInt(columnName) : null;
    }
}
