package com.epam.yermak.project.service.impl;

import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.dao.impl.UserDaoImpl;
import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.dao.UserDao;
import com.epam.yermak.project.service.UserService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.util.exception.UtilException;
import com.epam.yermak.project.util.hash.HashGeneratorUtil;
import com.epam.yermak.project.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserDao userDao;
    private final Validator validator;
    private final HashGeneratorUtil hashGeneratorUtil;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.validator = new Validator();
        hashGeneratorUtil = new HashGeneratorUtil();
    }

    @Override
    public boolean isEmailExist(String email) throws ServiceException {

        boolean result;
        try {
            result = userDao.isEmailExist(email);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "failed to check if user with {} exists", e);
            throw new ServiceException("Exception when find email : {}", e);
        }
        return result;
    }

    @Override
    public User findUser(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by id");

        try {
            return userDao.find(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find user by id: ", e);
            throw new ServiceException("Exception when find user by id: {}", e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by id");
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find user by id: ", e);
            throw new ServiceException("Exception when find user by id: {}", e);
        }
    }

    @Override
    public User findUser(String login, String pass) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by login and pass");
        if (!validator.isLoginValid(login) || !validator.isPasswordValid(pass)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            User user = userDao.findByLogin(login);
            if (hashGeneratorUtil.validatePassword(pass, user.getPassword())) {
                return user;
            } else {
                throw new ServiceException("Password is invalid ");
            }
        } catch (DaoException|UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method find user by login and pass: ", e);
            throw new ServiceException("Exception when find user by login and pass: {}", e);
        }
    }

        @Override
    public List<User> findAll() throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new ServiceException("Exception when find all users: {}", e);
        }
    }

    @Override
    public List<User> findUsersByQuery(String searchQuery) throws ServiceException {
        LOGGER.log(Level.INFO, "method findUsersByQuery");
        if (validator.isSearchValid(searchQuery)) {
            try {
                return userDao.findUsersByQuery(searchQuery);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "exception in method findUsersByQuery: ", e);
                throw new ServiceException("Exception when findUsersByQuery: {}", e);
            }
        } else {
            throw new ServiceException("Input data is invalid");
        }
    }

    @Override
    public User create(User user) throws ServiceException {
        LOGGER.log(Level.INFO, "method create");
        if (validator.isUserValid(user)) {
            try {
                return userDao.create(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "exception in method create: ", e);
                throw new ServiceException("Exception when create user: {}", e);
            }
        } else {
            throw new ServiceException("Input data is invalid");
        }
    }


    @Override
    public User update(User user) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
        if (validator.isUserValid(user)) {
            try {
                return userDao.update(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "exception in method update: ", e);
                throw new ServiceException("Exception when update user: {}", e);
            }
        } else {
            throw new ServiceException("Input data is invalid");
        }
    }


    @Override
    public void delete(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method delete");
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new ServiceException("Exception when delete user: {}", e);
        }
    }

    @Override
    public List<User> findActivatedUsers() throws ServiceException {
        LOGGER.log(Level.INFO, "method findActivatedUsers");
        try {
            return userDao.findActivatedUsers();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findActivatedUsers: ", e);
            throw new ServiceException("Exception when find activated users: {}", e);
        }
    }

    @Override
    public List<User> findDeactivatedUsers() throws ServiceException {
        LOGGER.log(Level.INFO, "method findDeactivatedUsers");
        try {
            return userDao.findActivatedUsers();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findDeactivatedUsers: ", e);
            throw new ServiceException("Exception when find deactivated users: {}", e);
        }
    }

    @Override
    public void deactivate(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method deactivate");
        try {
            userDao.deactivate(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method deactivate: ", e);
            throw new ServiceException("Exception when deactivate user: {}", e);
        }
    }

    @Override
    public void updatePassword(User user) throws ServiceException {
        LOGGER.log(Level.INFO, "method updatePassword");
        if (validator.isUserValid(user)) {
            try {
                userDao.updatePassword(user.getId(), user.getPassword());
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "exception in method updatePassword: ", e);
                throw new ServiceException("Exception when update user password: {}", e);
            }
        } else {
            throw new ServiceException("Input data is invalid");
        }
    }
}

