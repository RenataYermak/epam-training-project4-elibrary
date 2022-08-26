package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.UserValidator;

import java.util.List;

/**
 * Describes the behavior of {@link User} entity.
 */
public interface UserService extends EntityService<User, Long> {
    /**
     * Log in and return {@link User} instance
     *
     * @param login {@link User}'s login
     * @param pass  {@link User}'s password
     * @return {@link User} instance
     * @throws ServiceException if <tt>login</tt> or <tt>password</tt> is null or
     *                          empty or if <tt>login</tt> or <tt>password</tt> do
     *                          not accords to specify pattern {@link UserValidator}
     *                          or if {@link User} with <tt>login</tt> and <tt>password</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User find(String login, String pass) throws ServiceException;

    /**
     * Find  user in list {@link User}
     *
     * @return user {@link User}
     * @throws ServiceException if {@link User} in empty
     *                          occurs after searching {@link User} into the data source
     */
    List<User> findUsersByQuery(String searchQuery) throws ServiceException;

    /**
     * Creat {@link User} with filled fields
     *
     * @param user {@link User} is filled user instance
     * @return user {@link User}
     * @throws ServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@link UserValidator}
     *                          or if user with <tt>email</tt> or <tt>login</tt> has already exist
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    User create(User user) throws ServiceException;

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
     * Send message about successful registration on user email.
     *
     * @param firstName     the {@link User}'s firstname
     * @param secondName    the {@link User}'s secondname
     * @param login         the {@link User}'s login
     * @param password      the {@link User}'s password
     * @param email         the {@link User}'s email
     * @param currentLocale the current locale, chosen by current user
     */
    void sendEmailRegisteredUser(String firstName, String secondName, String login, String password, String email, String currentLocale);

    /**
     * Find users in page  {@link User}
     *
     * @param page count pages
     * @return all  activated users list {@link User} instance
     * @throws ServiceException if  {@link User}'s list don"t into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    List<User> findActivatedUsers(int page) throws ServiceException;

    /**
     * Find users in page  {@link User}
     *
     * @param page count pages
     * @return all deactivated users list {@link User} instance
     * @throws ServiceException if  {@link User}'s list don"t into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    List<User> findDeactivatedUsers(int page) throws ServiceException;

    /**
     * Checks whether an {@link User}'s email is already exist
     *
     * @param email a {@link User}'s email
     * @return whether an {@link User}'s email is already exist
     */
    boolean isEmailExist(String email) throws ServiceException;

    /**
     * Checks whether a {@link User}'s login is already exist
     *
     * @param login a {@link User}'s login
     * @return whether a {@link User}'s login is already exist
     */
    boolean isLoginExist(String login) throws ServiceException;
}
