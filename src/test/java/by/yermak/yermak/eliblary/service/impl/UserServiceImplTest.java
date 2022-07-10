package by.yermak.yermak.eliblary.service.impl;

import by.yermak.eliblary.entity.user.Role;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceImplTest {

    private final UserService userService = new UserServiceImpl();

    @Test
    void shouldGetAllUsers() throws ServiceException {
        int expectedNumberOfUsers = 20;
        List<User> actualUsers = userService.findAll();
        assertNotNull(actualUsers);
        assertThat(actualUsers.size(), is(expectedNumberOfUsers));
    }

    @Test
    void shouldGetUserById() throws ServiceException {
        User actualUser = userService.find(438L);
        assertNotNull(actualUser);
    }

    @Test
    void shouldRemoveUser() throws ServiceException {
        int expectedNumberOfUsers = 19;
        userService.delete(391L);
        List<User> actualUsers = userService.findAll();
        assertNotNull(actualUsers);
        assertThat(actualUsers.size(), is(expectedNumberOfUsers));
        userService.create(userCreator(391L));
    }

    @Test
    void shouldCreateUser() throws ServiceException {
        int expectedNumberOfUsers = 20;
        userService.create(userCreator(400L));
        List<User> actualUsers = userService.findAll();
        assertNotNull(actualUsers);
        assertThat(actualUsers.size(), is(expectedNumberOfUsers));
        userService.delete(400L);
    }

    User userCreator(Long id) {
        User user = new User();
        user.setId(id);
        user.setLogin("test");
        user.setPassword("Test19979");
        user.setFirstName("test");
        user.setSecondName("test");
        user.setEmail("test@mail.ru");
        user.setRole(Role.ADMIN);
        return user;
    }
}
