package by.yermak.eliblary.dao;

import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public interface UserDao extends EntityDao<User, Long> {
    boolean isEmailExist(String email) throws DaoException;

    User findByLogin(String login) throws DaoException;

    User find(String login, String pass) throws DaoException;

    List<User> findUsersByQuery(String searchQuery) throws DaoException;

    List<User> findActivatedUsers() throws DaoException;

    List<User> findDeactivatedUsers() throws DaoException;

    void deactivate(Long id) throws DaoException;

    void updatePassword(Long id, String pass) throws DaoException;
}
