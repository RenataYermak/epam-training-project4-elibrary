package com.epam.project.service;

import com.epam.project.model.user.User;
import com.epam.project.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    User findUser(Long id) throws ServiceException;
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
