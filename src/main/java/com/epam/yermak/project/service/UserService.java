package com.epam.yermak.project.service;

import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean isEmailExist(String email) throws ServiceException;

    User findUser(Long id) throws ServiceException;

    User findUserByLogin(String login) throws ServiceException;

   User findUser(String login, String pass) throws ServiceException;

    List<User> findAll() throws ServiceException;

    List<User> findUsersByQuery(String searchQuery) throws ServiceException;

    User create(User user) throws ServiceException;

    User update(User user) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<User> findActivatedUsers() throws ServiceException;

    List<User> findDeactivatedUsers() throws ServiceException;

    void deactivate(Long id) throws ServiceException;

    void updatePassword(User user) throws ServiceException;
}
