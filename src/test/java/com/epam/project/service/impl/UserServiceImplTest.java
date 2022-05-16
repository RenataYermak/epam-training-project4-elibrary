package com.epam.project.service.impl;

import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.UserService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceImplTest {

    private final UserService userService = new UserServiceImpl();

    @Test
    void shouldGetAllUsers() throws ServiceException {
        int expectedNumberOfUsers = 2;
        List<User> actualUsers = userService.findAll();
        assertNotNull(actualUsers);
        assertThat(actualUsers.size(), is(expectedNumberOfUsers));
    }
}
