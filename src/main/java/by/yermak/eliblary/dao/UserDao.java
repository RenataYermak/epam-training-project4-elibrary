package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link User} entity
 */
public interface UserDao extends EntityDao<User, Long> {

    /**
     * Checks if {@link User}'s email already exists.
     *
     * @param email is the {@link User}'s email
     * @return whether an email was found
     * @throws DaoException if there is any problem during access
     */
    boolean isEmailExist(String email) throws DaoException;

    /**
     * Checks if {@link User}'s login already exists.
     *
     * @param login is  the {@link User}'s login
     * @return whether a login was found
     * @throws DaoException if there is any problem during access
     */
    boolean isLoginExist(String login) throws DaoException;

    /**
     * Finds a {@link User} with the specified {@link User}'s login
     *
     * @param login is the {@link User}'s login
     * @return the optional object of a {@link User}
     * @throws DaoException if there is any problem during access
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Finds a {@link User} with the specified {@link User}'s login and {@link User}'s password.
     *
     * @param login    is the {@link User}'s login
     * @param password is the {@link User}'s password
     * @return the optional object of a {@link User}
     * @throws DaoException if there is any problem during access
     */
    Optional<User> find(String login, String password) throws DaoException;

    /**
     * Finds nd returns List of {@link User}'s in search query
     *
     * @param searchQuery is the string of search {@link User}
     * @return a list of {@link User}
     * @throws DaoException if there is any problem during access
     */
    List<User> findUsersByQuery(String searchQuery) throws DaoException;

    /**
     * Find all activated  {@link User} and collect them in the list
     *
     * @return the list of an activated {@link User}
     * @throws DaoException if there is any problem during access
     */
    List<User> findActivatedUsers() throws DaoException;

    /**
     * Find all deactivated {@link User} and collect them in the list
     *
     * @return the list of a deactivated of {@link User}
     * @throws DaoException if there is any problem during access
     */
    List<User> findDeactivatedUsers() throws DaoException;

    /**
     * Deactivated {@link User}
     *
     * @param id is the{@link User}'s id
     * @throws DaoException if there is any problem during access
     */
    void deactivate(Long id) throws DaoException;

    /**
     * Finds and returns the {@link User} list in page
     *
     * @param page count pages
     * @return a list of {@link User} in page
     * @throws DaoException if there is any problem during access
     */
    List<User> findAlL(int page) throws DaoException;
}
