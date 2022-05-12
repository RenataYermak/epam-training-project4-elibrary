package com.epam.project.dao;


import com.epam.project.dao.config.ConnectionPool;
import com.epam.project.dao.exception.DaoException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public interface EntityDao<E, K> {
    Logger LOGGER = LogManager.getLogger();
    ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    E find(K id) throws DaoException;
    List<E> findAll() throws DaoException;
    E create(E entity) throws DaoException;
    E update(E entity) throws DaoException;
    void delete(K id) throws DaoException;


    default Connection getConnection() {
        return connectionPool.getConnection();
    }

    default void returnConnection(Connection connection) {
        connectionPool.returnConnection(connection);
    }


    default PreparedStatement getPrepareStatement(String sql) {
        LOGGER.log(Level.INFO, "get prepare statement");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method getPrepareStatement: ", e);
        }
        return preparedStatement;
    }

    //TODO return connection to pool
    default void closePrepareStatement(PreparedStatement preparedStatement) {
        LOGGER.log(Level.INFO, "close prepare statement");
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method closePrepareStatement: ", e);
        }
    }
//    default void closeResultSet(ResultSet resultSet) throws DaoException {
//        if (resultSet != null) {
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                LOGGER.error("Can`t close resultSet", e);
//                throw new DaoException("service.commonError", e);
//            }
//        }
//    }
//
//    default void closeConnection(Connection connection) throws DaoException {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                LOGGER.error("Can`t close connection", e);
//                throw new DaoException("service.commonError", e);
//            }
//        }
//    }


    default void setIntOrNull(PreparedStatement preparedStatement, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(parameterIndex, Types.INTEGER);
        } else {
            preparedStatement.setInt(parameterIndex, value);
        }
    }

    default void setDoubleOrNull(PreparedStatement preparedStatement, int parameterIndex, Double value) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(parameterIndex, Types.DOUBLE);
        } else {
            preparedStatement.setDouble(parameterIndex, value);
        }
    }

    default Double returnResultSetDoubleValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) != null ? rs.getDouble(columnName) : null;
    }

    default Integer returnResultSetIntValue(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName) != null ? rs.getInt(columnName) : null;
    }

//    default void constructBook(Book book, ResultSet rs) throws SQLException {
//        book.setId(rs.getLong(BookDaoImpl.ColumnName.ID));
//        book.setTitle(rs.getString(BookDaoImpl.ColumnName.TITLE));
//        book.setAuthor(rs.getString(BookDaoImpl.ColumnName.AUTHOR));
//        book.setPublishYear(rs.getInt(BookDaoImpl.ColumnName.PUBLISH_YEAR));
//        book.setDescription(rs.getString(BookDaoImpl.ColumnName.DESCRIPTION));
//       // book.setOverallRating(rs.getDouble(BookDaoImpl.ColumnName.OVERALL_RATING));
//       // book.setNumber(rs.getInt(BookDaoImpl.ColumnName.NUMBER));
//        book.setOverallRating(returnResultSetDoubleValue(rs, BookDaoImpl.ColumnName.OVERALL_RATING));
//        book.setNumber(returnResultSetIntValue(rs, BookDaoImpl.ColumnName.NUMBER));
//        book.setCategory(Category.valueOf(rs.getString(BookDaoImpl.ColumnName.CATEGORY).toUpperCase()));
//    }
//
//    default void constructpreparedStatement(PreparedStatement preparedStatement, Book book) throws SQLException {
//        preparedStatement.setString(1, book.getTitle());
//        preparedStatement.setString(2, book.getAuthor());
//        preparedStatement.setString(3, book.getCategory().toString());
//        preparedStatement.setInt(4, book.getPublishYear());
//        preparedStatement.setString(5, book.getDescription());
//      //  preparedStatement.setDouble(6,book.getOverallRating());
//      //  preparedStatement.setInt(7, book.getNumber());
//      setDoubleOrNull(preparedStatement, 6, book.getOverallRating());
//       setIntOrNull(preparedStatement, 7, book.getNumber());
//    }
//default void constructUser(User user, ResultSet resultSet) throws SQLException {
//    user.setId(resultSet.getLong(UserDaoImpl.ColumnName.ID));
//    user.setLogin(resultSet.getString(UserDaoImpl.ColumnName.LOGIN));
//    user.setPassword(resultSet.getString(UserDaoImpl.ColumnName.PASSWORD));
//    user.setFirstName(resultSet.getString(UserDaoImpl.ColumnName.FIRSTNAME));
//    user.setSecondName(resultSet.getString(UserDaoImpl.ColumnName.SECONDNAME));
//    user.setActivationDate(resultSet.getTimestamp(UserDaoImpl.ColumnName.ACTIVATION_DATE));
//    user.setDeactivationDate(resultSet.getTimestamp(UserDaoImpl.ColumnName.DEACTIVATION_DATE));
//    user.setRole(Role.valueOf(resultSet.getString(UserDaoImpl.ColumnName.ROLE).toUpperCase()));
//    user.setStatus(Status.valueOf(resultSet.getString(UserDaoImpl.ColumnName.STATUS).toUpperCase()));
//}
//
//    default void constructPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
//        preparedStatement.setString(1, user.getLogin());
//        preparedStatement.setString(2, user.getPassword());
//        preparedStatement.setString(3, user.getFirstName());
//        preparedStatement.setString(4, user.getSecondName());
//        preparedStatement.setString(5, user.getRole().toString());
//    }
}
