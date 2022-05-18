package com.epam.yermak.project.dao;

import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.model.user.User;

import java.util.List;
import java.util.Optional;

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
