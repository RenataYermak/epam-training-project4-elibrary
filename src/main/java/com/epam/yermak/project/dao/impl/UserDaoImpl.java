package com.epam.yermak.project.dao.impl;

import com.epam.yermak.project.dao.UserDao;
import com.epam.yermak.project.dao.exception.DaoException;

import com.epam.yermak.project.model.user.Role;
import com.epam.yermak.project.model.user.Status;
import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.util.exception.UtilException;
import com.epam.yermak.project.util.hash.HashGeneratorUtil;
import org.apache.logging.log4j.Level;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static class Query {
        public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
        public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
        public static final String SELECT_USER_BY_LOGIN_AND_PASS = "SELECT * FROM users WHERE login=? and password=? AND `status`='ACTIVATED'";
        public static final String SELECT_ALL_USERS = "SELECT * FROM users";
        public static final String SELECT_ALL_ACTIVATED_USERS = "SELECT * FROM users WHERE `status`='ACTIVATED'";
        public static final String SELECT_ALL_DEACTIVATED_USERS = "SELECT * FROM users WHERE `status`='DEACTIVATED'";
        public static final String INSERT_USER = "INSERT INTO users(login, password, firstname, secondname, email, role) VALUES(?, ?, ?, ?, ?,?)";
        public static final String UPDATE_USER = "UPDATE users SET login=?, password=?, firstname=?, secondname=?, email=?, role=? WHERE user_id=?";
        public static final String DEACTIVATE_USER = "UPDATE users SET `status`='DEACTIVATED', deactivation_date=? WHERE user_id=?";
        public static final String DELETE_USER = "DELETE FROM users WHERE user_id=?";
        public static final String USER_SEARCH = "SELECT * FROM users WHERE login LIKE CONCAT('%',?,'%') or firstname LIKE CONCAT('%',?,'%') " +
                "OR secondname LIKE CONCAT('%',?,'%')";
        public static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE user_id=?";
        private static final String SQL_IS_EMAIL_EXIST =
                "SELECT user_id FROM users WHERE email = ? LIMIT 1";
    }

    private static class ColumnName {
        public static final String ID = "user_id";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String FIRSTNAME = "firstname";
        public static final String SECONDNAME = "secondname";
        public static final String EMAIL = "email";
        public static final String ROLE = "role";
        public static final String STATUS = "status";
        public static final String ACTIVATION_DATE = "activation_date";
        public static final String DEACTIVATION_DATE = "deactivation_date";
    }


    @Override
    public boolean isEmailExist(String email) throws DaoException {
        try (PreparedStatement statement = getPrepareStatement(Query.SQL_IS_EMAIL_EXIST)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            LOGGER.error("failed to check if user with {} email exists", email, e);
            throw new DaoException("failed to check if user with " + email + " exists", e);
        }
    }

    @Override
    public User find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find by id");
        User user = null;
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    constructUser(user, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by id: ", e);
            throw new DaoException("Exception when find user by id: {}", e);
        }
        return user;
    }
    @Override
    public User findByLogin(String login) throws DaoException {
        LOGGER.log(Level.INFO, "method find by id");
        User user = null;
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    constructUser(user, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by id: ", e);
            throw new DaoException("Exception when find user by id: {}", e);
        }
        return user;
    }
    @Override
    public User find(String login, String pass) throws DaoException {
        LOGGER.log(Level.INFO, "method find by login and pass");
        User user = null;
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_USER_BY_LOGIN_AND_PASS)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    constructUser(user, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by login and pass: ", e);
            throw new DaoException("Exception when find user by login and pass: {}", e);
        }
        return user;
    }


    @Override
    public List<User> findUsersByQuery(String searchQuery) throws DaoException {
        LOGGER.log(Level.INFO, "method findUsersByQuery");
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.USER_SEARCH)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    constructUser(user, resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findUsersByQuery: ", e);
            throw new DaoException("Exception when findUsersByQuery: {}", e);
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll users: {}", e);
        }
        return users;
    }

    @Override
    public List<User> findActivatedUsers() throws DaoException {
        LOGGER.log(Level.INFO, "method findActivatedUsers");
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ALL_ACTIVATED_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findActivatedUsers: ", e);
            throw new DaoException("Exception when findActivatedUsers users: {}", e);
        }
        return users;
    }

    @Override
    public List<User> findDeactivatedUsers() throws DaoException {
        LOGGER.log(Level.INFO, "method findDeactivatedUsers");
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ALL_DEACTIVATED_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                constructUser(user, resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findDeactivatedUsers: ", e);
            throw new DaoException("Exception when findDeactivatedUsers users: {}", e);
        }
        return users;
    }

    @Override
    public User create(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.INSERT_USER)) {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                    User createdUser = find(user.getId());
                    user.setStatus(createdUser.getStatus());
                    user.setActivationDate(createdUser.getActivationDate());
                }
            }
        } catch (SQLException | UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create user: {}", e);
        }
        return user;
    }

    @Override
    public User update(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method update");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.UPDATE_USER)) {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException |UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new DaoException("Exception when update user: {}", e);
        }
        return user;
    }

    @Override
    public void deactivate(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method deactivate");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.DEACTIVATE_USER)) {
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method deactivate: ", e);
            throw new DaoException("Exception when deactivate user: {}", e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.DELETE_USER);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete user: {}", e);
        }
    }

    @Override
    public void updatePassword(Long id, String pass) throws DaoException {
        LOGGER.log(Level.INFO, "method updatePassword");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.UPDATE_PASSWORD)) {
            preparedStatement.setString(1, pass);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method updatePassword: ", e);
            throw new DaoException("Exception when update user password: {}", e);
        }
    }

    private void constructUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong(ColumnName.ID));
        user.setLogin(resultSet.getString(ColumnName.LOGIN));
        user.setPassword(resultSet.getString(ColumnName.PASSWORD));
        user.setFirstName(resultSet.getString(ColumnName.FIRSTNAME));
        user.setSecondName(resultSet.getString(ColumnName.SECONDNAME));
        user.setEmail(resultSet.getString(ColumnName.EMAIL));
        user.setActivationDate(resultSet.getTimestamp(ColumnName.ACTIVATION_DATE));
        user.setDeactivationDate(resultSet.getTimestamp(ColumnName.DEACTIVATION_DATE));
        user.setRole(Role.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
        user.setStatus(Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
    }

    private void constructPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException, UtilException {
        HashGeneratorUtil hashGenerator = new HashGeneratorUtil();
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, hashGenerator.generateHash(user.getPassword()));
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getSecondName());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getRole().toString());
    }
}


