package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.util.locale.LanguageMessage;
import by.yermak.eliblary.util.email.MailSender;
import by.yermak.eliblary.util.locale.MessagesKey;
import by.yermak.eliblary.util.exception.UtilException;
import by.yermak.eliblary.util.hash.HashGenerator;
import by.yermak.eliblary.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserDao userDao;
    private final Validator validator;
    private final HashGenerator hashGenerator;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.validator = new Validator();
        this.hashGenerator = new HashGenerator();
    }
    @Override
    public List<User> findAll(int page) throws ServiceException {
        try {
            return userDao.findAlL(page);
        } catch (DaoException e) {
            throw new ServiceException("Exception in findAllUsers method", e);
        }
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
            var optionalUser = userDao.find(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ServiceException("Input data is invalid");
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find user by id: ", e);
            throw new ServiceException("Exception when find user by id: {}", e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        LOGGER.log(Level.INFO, "method find user by id");
        try {
            var optionalUser = userDao.findByLogin(login);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ServiceException("There is no such user with email: " + login);
            }
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
            var optionalUser = userDao.findByLogin(login);
//            if (optionalUser.isPresent() && hashGenerator.validatePassword(pass, optionalUser.get().getPassword())) {
                return optionalUser.get();
//            } else {
//                throw new ServiceException("Password is invalid22 ");
//            }
        } catch (DaoException e) {
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
        if (!validator.isUserValid(user)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            user.setPassword(hashGenerator.generateHash(user.getPassword()));
            var optionalUser = userDao.create(user);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ServiceException("Input data is invalid");
            }
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
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new ServiceException("Password is invalid ");
            }
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

    @Override
    public void sendEmailRegisteredUser(String firstName,String secondName, String email, String currentLocale) {
        var finalRegistrationMessage =firstName + " " + secondName + ", " + LanguageMessage.getInstance().getText(currentLocale, "successful.reg.email.body");
        MailSender.getInstance().send(email, MessagesKey.SUCCESSFUL_REG_EMAIL_HEADER, finalRegistrationMessage);
    }
    public static final String SELECT_USER_BY_ID = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.user_id=?""";
    public static final String SELECT_USER_BY_LOGIN = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login=?""";
    public static final String SELECT_USER_BY_LOGIN_AND_PASS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login=? and u.password=? AND us.status_name='ACTIVATED'""";
    public static final String SELECT_ALL_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id""";
    public static final String SELECT_ALL_ACTIVATED_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE us.status_name='ACTIVATED'""";
    public static final String SELECT_ALL_DEACTIVATED_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE us.status_name='DEACTIVATED'""";
    public static final String INSERT_USER = """
            INSERT INTO users
            (login, password, firstname, secondname, email, role_id)
            VALUES (?, ?, ?, ?, ?, (SELECT ur.role_id FROM user_roles ur WHERE role_name = ?))""";
    public static final String UPDATE_USER = """
            UPDATE users u
            SET u.login =?,
                u.password=?,
                u.firstname=?,
                u.secondname=?,
                u.email=?,
                u.role_id=(SELECT ur.role_id FROM user_roles ur WHERE role_name = ?)
            WHERE u.user_id =?""";
    public static final String DEACTIVATE_USER = """
            UPDATE users u
            SET u.status_id=(SELECT status_id FROM user_statuses us WHERE status_name='DEACTIVATED',
                u.deactivation_date=?
            WHERE u.user_id =?""";
    public static final String DELETE_USER = """
            DELETE
            FROM users u
            WHERE u.user_id =?""";
    public static final String USER_SEARCH = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            WHERE u.login LIKE CONCAT('%',?,'%') OR u.firstname LIKE CONCAT('%',?,'%')
                  OR u.secondname LIKE CONCAT('%',?,'%')""";
    public static final String UPDATE_PASSWORD = """
            UPDATE users u
            SET u.password =?
            WHERE u.user_id =?""";
    public static final String SQL_IS_EMAIL_EXIST = """
            SELECT user_id
            FROM users u
            WHERE u.email = ?
            LIMIT 1""";
    public static final String FIND_PAGE_QUERY_USERS = """
            SELECT u.user_id,
                   u.login,
                   u.password,
                   u.firstname,
                   u.secondname,
                   u.email,
                   ur.role_name,
                   us.status_name,
                   u.activation_date,
                   u.deactivation_date
            FROM users u
            JOIN user_roles ur ON u.role_id = ur.role_id
            JOIN user_statuses us ON us.user_status_id = u.status_id
            LIMIT ?,?""";
}

