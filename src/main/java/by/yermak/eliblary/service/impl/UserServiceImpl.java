package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.util.locale.LanguageMessage;
import by.yermak.eliblary.util.email.MailSender;
import by.yermak.eliblary.util.locale.MessagesKey;
import by.yermak.eliblary.util.exception.UtilException;
import by.yermak.eliblary.util.hash.HashGenerator;
import by.yermak.eliblary.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final HashGenerator hashGenerator = HashGenerator.getInstance();
    private final UserValidator validator = UserValidator.getInstance();
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public User find(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by id");
        try {
            var optionalUser = userDao.find(id);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("Input data is invalid");
            }
            return optionalUser.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find user by id: ", e);
            throw new ServiceException("Exception when find user by id: {}", e);
        }
    }

    @Override
    public User find(String login, String pass) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by login and pass");
        if (!validator.isLoginValid(login) || !validator.isPasswordValid(pass)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            var optionalUser = userDao.findByLogin(login);
            if (!(optionalUser.isPresent() && hashGenerator.validatePassword(pass, optionalUser.get().getPassword()))) {
                throw new ServiceException("Password is invalid");
            }
            return optionalUser.get();
        } catch (DaoException | UtilException e) {
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
        if (!validator.isSearchValid(searchQuery)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            return userDao.findUsersByQuery(searchQuery);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findUsersByQuery: ", e);
            throw new ServiceException("Exception when findUsersByQuery: {}", e);
        }
    }

    @Override
    public User create(User user) throws ServiceException {
        LOGGER.log(Level.INFO, "method create");
        if (!validator.isUserValid(user)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            user.setPassword(hashGenerator.generateHash(user.getPassword()));
            var optionalUser = userDao.create(user);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("Input data is invalid");
            }
            return optionalUser.get();
        } catch (DaoException | UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new ServiceException("Exception when create user: {}", e);
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
        if (!validator.isUserValid(user)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            user.setPassword(hashGenerator.generateHash(user.getPassword()));
            var optionalUser = userDao.update(user);
            if (optionalUser.isEmpty()) {
                throw new ServiceException("Password is invalid ");
            }
            return optionalUser.get();
        } catch (DaoException | UtilException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new ServiceException("Exception when update user: {}", e);
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
            return userDao.findDeactivatedUsers();
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
    public List<User> findActivatedUsers(int page) throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return userDao.findActivatedUsers(page);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll user in page : ", e);
            throw new ServiceException("Exception in findAll method: {}", e);
        }
    }

    @Override
    public List<User> findDeactivatedUsers(int page) throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return userDao.findDeactivatedUsers(page);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll user in page : ", e);
            throw new ServiceException("Exception in findAll method: {}", e);
        }
    }

    @Override
    public boolean isEmailExist(String email) throws ServiceException {
        boolean result;
        try {
            result = userDao.isEmailExist(email);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "failed to check if user with {} exists ", e);
            throw new ServiceException("Exception when find email : {}", e);
        }
        return result;
    }

    @Override
    public boolean isLoginExist(String login) throws ServiceException {
        boolean result;
        try {
            result = userDao.isLoginExist(login);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "failed to check if user with {} exists", e);
            throw new ServiceException("Exception when find login : {}", e);
        }
        return result;
    }

    @Override
    public void sendEmailRegisteredUser(String firstName, String secondName, String login, String password, String email, String currentLocale) {
        var finalRegistrationMessage =
                firstName + " " + secondName + ", " + LanguageMessage.getInstance().getText(currentLocale, "successful.reg.email.body") +
                login + "," + password + ". ";
        MailSender.getInstance().send(email, MessagesKey.SUCCESSFUL_REG_EMAIL_HEADER, finalRegistrationMessage);
    }
}

