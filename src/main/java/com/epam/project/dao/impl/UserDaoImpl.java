package com.epam.project.dao.impl;

import com.epam.project.dao.UserDao;
import com.epam.project.dao.exception.DaoException;

import com.epam.project.model.user.Role;
import com.epam.project.model.user.Status;
import com.epam.project.model.user.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();


    private static class Query {
        public static final String SELECT_USER_BY_ID = "select * from user where user_id=?";
        public static final String SELECT_USER_BY_LOGIN_AND_PASS =
                "select * from user where login=? and password=? and `status`='ACTIVATED'";
        public static final String SELECT_ALL_USERS = "select * from user";
        public static final String SELECT_ALL_ACTIVATED_USERS =
                "select * from user where `status`='ACTIVATED'";
        public static final String SELECT_ALL_DEACTIVATED_USERS =
                "select * from user where `status`='DEACTIVATED'";
        public static final String INSERT_USER =
                "insert into user(login, `password`, firstname, secondname, role) values(?, ?, ?, ?, ?);";
        public static final String UPDATE_USER =
                "update user set login=?, password=?, firstname=?, secondname=?, role=? where user_id=?";
        public static final String DEACTIVATE_USER =
                "update user set `status`='DEACTIVATED', deactivation_date=? where user_id=?";
        public static final String DELETE_USER = "delete from user where user_id=?";
        public static final String USER_SEARCH =
                "select * from user where login like CONCAT('%',?,'%') or firstname like CONCAT('%',?,'%') " +
                        "or secondname like CONCAT('%',?,'%')";
        public static final String UPDATE_PASSWORD = "update user set password=? where user_id=?";
    }

    private static class ColumnName {
        public static final String ID = "user_id";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String FIRSTNAME = "firstname";
        public static final String SECONDNAME = "secondname";
        public static final String ROLE = "role";
        public static final String STATUS = "status";
        public static final String ACTIVATION_DATE = "activation_date";
        public static final String DEACTIVATION_DATE = "deactivation_date";
    }

    @Override
    public User find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find by id");
        User user = null;
        PreparedStatement preparedStatement= getPrepareStatement(Query.SELECT_USER_BY_ID);
        try {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                constructUser(user, resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by id: ", e);
            throw new DaoException("Exception when find user by id: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return user;
    }

    @Override
    public User find(String login, String pass) throws DaoException {
        LOGGER.log(Level.INFO, "method find by login and pass");
        User user = null;
        PreparedStatement preparedStatement= getPrepareStatement(Query.SELECT_USER_BY_LOGIN_AND_PASS);
        try {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                constructUser(user, resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by login and pass: ", e);
            throw new DaoException("Exception when find user by login and pass: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return user;
    }

    @Override
    public List<User> findUsersByQuery(String searchQuery) throws DaoException {
        LOGGER.log(Level.INFO, "method findUsersByQuery");
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement= getPrepareStatement(Query.USER_SEARCH);
        try {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findUsersByQuery: ", e);
            throw new DaoException("Exception when findUsersByQuery: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement= getPrepareStatement(Query.SELECT_ALL_USERS);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll users: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return users;
    }

    @Override
    public List<User> findActivatedUsers() throws DaoException {
        LOGGER.log(Level.INFO, "method findActivatedUsers");
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement= getPrepareStatement(Query.SELECT_ALL_ACTIVATED_USERS);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findActivatedUsers: ", e);
            throw new DaoException("Exception when findActivatedUsers users: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return users;
    }

    @Override
    public List<User> findDeactivatedUsers() throws DaoException {
        LOGGER.log(Level.INFO, "method findDeactivatedUsers");
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement= getPrepareStatement(Query.SELECT_ALL_DEACTIVATED_USERS);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findDeactivatedUsers: ", e);
            throw new DaoException("Exception when findDeactivatedUsers users: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return users;
    }

    @Override
    public User create(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        PreparedStatement preparedStatement= getPrepareStatement(Query.INSERT_USER);
        try {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                User createdUser = find(user.getId());
                user.setStatus(createdUser.getStatus());
                user.setActivationDate(createdUser.getActivationDate());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create user: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return user;
    }

    @Override
    public User update(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method update");
        PreparedStatement preparedStatement= getPrepareStatement(Query.UPDATE_USER);
        try {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new DaoException("Exception when update user: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
        return user;
    }

    @Override
    public void deactivate(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method deactivate");
        PreparedStatement preparedStatement= getPrepareStatement(Query.DEACTIVATE_USER);
        try {
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method deactivate: ", e);
            throw new DaoException("Exception when deactivate user: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        PreparedStatement preparedStatement= getPrepareStatement(Query.DELETE_USER);
        try {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete user: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
    }

    @Override
    public void updatePassword(Long id, String pass) throws DaoException {
        LOGGER.log(Level.INFO, "method updatePassword");
        PreparedStatement preparedStatement= getPrepareStatement(Query.UPDATE_PASSWORD);
        try {
            preparedStatement.setString(1, pass);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method updatePassword: ", e);
            throw new DaoException("Exception when update user password: {}", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
    }

    private void constructUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong(ColumnName.ID));
        user.setLogin(resultSet.getString(ColumnName.LOGIN));
        user.setPassword(resultSet.getString(ColumnName.PASSWORD));
        user.setFirstName(resultSet.getString(ColumnName.FIRSTNAME));
        user.setSecondName(resultSet.getString(ColumnName.SECONDNAME));
        user.setActivationDate(resultSet.getTimestamp(ColumnName.ACTIVATION_DATE));
        user.setDeactivationDate(resultSet.getTimestamp(ColumnName.DEACTIVATION_DATE));
        user.setRole(Role.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
        user.setStatus(Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
    }

    private void constructPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getSecondName());
        preparedStatement.setString(5, user.getRole().toString());
    }
}



