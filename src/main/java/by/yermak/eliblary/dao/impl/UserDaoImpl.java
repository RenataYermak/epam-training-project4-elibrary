package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.mapper.impl.UserMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.util.exception.UtilException;
import by.yermak.eliblary.dao.exception.DaoException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserMapper userMapper = new UserMapper();

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


    @Override
    public boolean isEmailExist(String email) throws DaoException {
        boolean isExist = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SQL_IS_EMAIL_EXIST)) {
            preparedStatement.setString(1, email);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("failed to check if user with {} email exists", email, e);
            throw new DaoException("failed to check if user with " + email + " exists", e);
        }
        return isExist;
    }

    @Override
    public Optional<User> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find by id");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by id: ", e);
            throw new DaoException("Exception when find user by id: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        LOGGER.log(Level.INFO, "method find by id");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by id: ", e);
            throw new DaoException("Exception when find user by id: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> find(String login, String password) throws DaoException {
        LOGGER.log(Level.INFO, "method find by login and pass");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_USER_BY_LOGIN_AND_PASS)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find by login and pass: ", e);
            throw new DaoException("Exception when find user by login and pass: {}", e);
        }
        return Optional.empty();
    }


    @Override
    public List<User> findUsersByQuery(String searchQuery) throws DaoException {
        LOGGER.log(Level.INFO, "method findUsersByQuery");
        List<User> users = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.USER_SEARCH)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalUser = userMapper.map(resultSet);
                    optionalUser.ifPresent(users::add);
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_ALL_USERS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalUser = userMapper.map(resultSet);
                optionalUser.ifPresent(users::add);
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_ALL_ACTIVATED_USERS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalUser = userMapper.map(resultSet);
                optionalUser.ifPresent(users::add);
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.SELECT_ALL_DEACTIVATED_USERS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalUser = userMapper.map(resultSet);
                optionalUser.ifPresent(users::add);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findDeactivatedUsers: ", e);
            throw new DaoException("Exception when findDeactivatedUsers users: {}", e);
        }
        return users;
    }

    @Override
    public Optional<User> create(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong(1));
                    var optionalUser = find(user.getId());
                    if (optionalUser.isPresent()) {
                        user.setStatus(optionalUser.get().getStatus());
                        user.setActivationDate(optionalUser.get().getActivationDate());
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException | UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create user: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        LOGGER.log(Level.INFO, "method update");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.UPDATE_USER)) {
            constructPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new DaoException("Exception when update user: {}", e);
        }
        return Optional.of(user);
    }

    @Override
    public void deactivate(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method deactivate");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.DEACTIVATE_USER)) {
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.DELETE_USER);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete user: {}", e);
        }
    }

    @Override
    public void updatePassword(Long id, String password) throws DaoException {
        LOGGER.log(Level.INFO, "method updatePassword");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Query.UPDATE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method updatePassword: ", e);
            throw new DaoException("Exception when update user password: {}", e);
        }
    }

    private void constructPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException, UtilException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getSecondName());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getRole().toString());
    }
}
