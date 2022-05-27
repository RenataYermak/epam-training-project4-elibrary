package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao
 */
public interface UserDao extends EntityDao<User> {
    /**
     * Checks if email already exists.
     *
     * @param email the user's email
     * @return whether an email was found
     * @throws DaoException if there is any problem during access
     */
    boolean isEmailExist(String email) throws DaoException;

    /**
     * Finds a user with the specified login.
     *
     * @param login the user's login
     * @return the optional object of a user
     * @throws DaoException if there is any problem during access
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Finds a user with the specified login and password.
     *
     * @param login    the user's login
     * @param password the user's email
     * @return the optional object of a user
     * @throws DaoException if there is any problem during access
     */
    Optional<User> find(String login, String password) throws DaoException;

    /**
     * Finds users in search query and collect them in the list
     *
     * @return a list of users
     * @throws DaoException if there is any problem during access
     */
    List<User> findUsersByQuery(String searchQuery) throws DaoException;

    /**
     * Find all activated users and collect them in the list
     *
     * @return the list of an activated users
     * @throws DaoException if there is any problem during access
     */
    List<User> findActivatedUsers() throws DaoException;

    /**
     * Find all deactivated users and collect them in the list
     *
     * @return the list of a deactivated users
     * @throws DaoException if there is any problem during access
     */
    List<User> findDeactivatedUsers() throws DaoException;

    /**
     * Deactivated users
     *
     * @param id the id
     * @throws DaoException if there is any problem during access
     */
    void deactivate(Long id) throws DaoException;

    /**
     * Update password
     *
     * @param id       the id
     * @param password the new password
     * @throws DaoException if there is any problem during access
     */
    void updatePassword(Long id, String password) throws DaoException;
}
