package com.epam.yermak.project.service;

import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.validator.Validator;

import java.util.List;

/**
 * Describes the behavior of {@link User} entity.
 */
public interface UserService {
    boolean isEmailExist(String email) throws ServiceException;

    /**
     * Find user {@link User} instance by <tt>id</tt>
     *
     * @param id {@link User}'s id
     * @return {@link User} instance
     * @throws ServiceException if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUser(Long id) throws ServiceException;

    /**
     * Find user {@link User} instance by <tt>login</tt>
     *
     * @param login {@link User}'s login
     * @return {@link User} instance
     * @throws ServiceException if <tt>login</tt> is null or empty or if <tt>login</tt>
     *                          or not accords to specify pattern {@link Validator}
     *                          or if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUserByLogin(String login) throws ServiceException;

    /**
     * Log in and return {@link User} instance
     *
     * @param login {@link User}'s login
     * @param pass  {@link User}'s password
     * @return {@link User} instance
     * @throws ServiceException if <tt>login</tt> or <tt>password</tt> is null or
     *                          empty or if <tt>login</tt> or <tt>password</tt> do
     *                          not accords to specify pattern {@link Validator}
     *                          or if {@link User} with <tt>login</tt> and <tt>password</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User findUser(String login, String pass) throws ServiceException;

    /**
     * Find all users list {@link User}
     *
     * @return all users list {@link User}
     * @throws ServiceException if {@link User} in empty
     *                          occurs after searching {@link User} into the data source
     */
    List<User> findAll() throws ServiceException;

    /**
     * Find  user in list {@link User}
     *
     * @return user {@link User}
     * @throws ServiceException if {@link User} in empty
     *                          occurs after searching {@link User} into the data source
     */
    List<User> findUsersByQuery(String searchQuery) throws ServiceException;

    /**
     * Creat {@link User} with filled fields, allow access to service after using
     * single-use auth-token, which sent to email
     *
     * @param user {@link User} is filled user instance
     * @throws ServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@link Validator}
     *                          or if user with <tt>email</tt> or <tt>login</tt> has already exist
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    User create(User user) throws ServiceException;

    /**
     * Update {@link User} with filled fields
     *
     * @param user {@link User} is filled user instance
     * @throws ServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@link Validator}
     *                          or if user with <tt>email</tt> has already exist
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    User update(User user) throws ServiceException;

    /**
     * Delete {@link User} with filled fields
     *
     * @param id {@link User}'s id
     * @throws ServiceException if {@link User} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    void delete(Long id) throws ServiceException;

    /**
     * Find all activation users list {@link User}
     *
     * @return all activation users list {@link User} instance
     * @throws ServiceException if  {@link User}'s list don"t into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    List<User> findActivatedUsers() throws ServiceException;

    /**
     * Find all deactivation users list {@link User}
     *
     * @return all deactivation users list {@link User} instance
     * @throws ServiceException if  {@link User}'s list don"t into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    List<User> findDeactivatedUsers() throws ServiceException;

    /**
     * Deactivation {@link User}'s set deactivation status
     *
     * @param id {@link User} 's id
     * @throws ServiceException if an error occurs while writing new
     *                          {@link User} into data source
     */
    void deactivate(Long id) throws ServiceException;

    /**
     * Update {@link User}'s with password
     *
     * @param user {@link User} instance
     * @throws ServiceException if  <tt>password</tt> is null or
     *                          empty or if  <tt>password</tt> do
     *                          not accords to specify pattern {@link Validator}
     *                          or if {@link User} with  <tt>password</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    void updatePassword(User user) throws ServiceException;
}

