package by.yermak.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.impl.UserDaoImpl;

import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.entity.user.Status;
import by.yermak.eliblary.entity.user.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

class UserDaoImplTest {

    private final UserDao userDao = new UserDaoImpl();

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() throws DaoException {
        User user = constructTestUser();
        Optional<User> actualUser = userDao.create(user);
        Optional<User> expectedUser = userDao.find(actualUser.get().getId());
        assertThat(actualUser, is(expectedUser));
        userDao.delete(expectedUser.get().getId());
    }

    @Test
    @DisplayName("Should find user by login and password")
    void shouldFindUserByLoginAndPassword() throws DaoException {
        User user = constructTestUser();
        Optional<User> actualUser= userDao.create(user);
        Optional<User> expectedUser = userDao.find(actualUser.get().getLogin(), actualUser.get().getPassword());
        assertThat(actualUser, is(expectedUser));
        userDao.delete(expectedUser.get().getId());
    }

    @Test
    @DisplayName("Should find all users")
    void shouldFindAllUsers() throws DaoException {
        int count = 1;
        for (int i = 0; i < count; i++) {
            userDao.create(constructTestUser());
        }
        List<User> expectedUser = userDao.findAll();
        assertThat(expectedUser.size(), is(count));
        for (int i = 0; i < count; i++) {
            userDao.delete(expectedUser.get(i).getId());
        }
    }

    @Test
    @DisplayName("Should find all activated users")
    void shouldFindActivatedUsers() throws DaoException {
        int count = 3;
        for (int i = 0; i < count; i++) {
            userDao.create(constructTestUser());
        }
        List<User> expectedUser = userDao.findActivatedUsers();
        assertThat(expectedUser.size(), is(count));
        for (int i = 0; i < count; i++) {
            userDao.delete(expectedUser.get(i).getId());
        }
    }

    @Test
    @DisplayName("Should find all deactivated users")
    void shouldFindDeactivatedUsers() throws DaoException {
        int count = 3;
        for (int i = 0; i < count; i++) {
            Optional<User> actualUser = userDao.create(constructTestUser());
            userDao.deactivate(actualUser.get().getId());
        }
        List<User> expectedUser = userDao.findDeactivatedUsers();
        assertThat(expectedUser.size(), is(count));
        for (int i = 0; i < count; i++) {
            userDao.delete(expectedUser.get(i).getId());
        }
    }
//
    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() throws DaoException {
        User actualUser = constructTestUser();
        Optional<User> expectedUser = userDao.create(actualUser);
        assertThat(actualUser, is(expectedUser));
        userDao.delete(expectedUser.get().getId());
    }

    @Test
    @DisplayName("Should update user")
    void shouldUpdateUser() throws DaoException {
        User user = constructTestUser();
        Optional<User> actualUser = userDao.create(user);
        actualUser.get().setPassword("UpdatedPassword1");
        Optional<User> expectedUser = userDao.update(user);
        assertThat(actualUser, is(expectedUser));
        userDao.delete(expectedUser.get().getId());
    }

    @Test
    @DisplayName("Should delete user")
    void shouldDeleteUser() throws DaoException {
        User user = constructTestUser();
        Optional<User> actualUser = userDao.create(user);
        userDao.delete(actualUser.get().getId());
        Optional<User> expected = userDao.find(actualUser.get().getId());
        assertThat(expected,is(Optional.empty()));
    }

    @Test
    @DisplayName("Should deactivate user")
    void shouldDeactivateUser() throws DaoException {
        User user = constructTestUser();
        Optional<User> actualUser = userDao.create(user);
        assertThat(actualUser.get().getStatus(), is(Status.ACTIVATED));
        userDao.deactivate(actualUser.get().getId());
        actualUser = userDao.find(actualUser.get().getId());
        assertThat(actualUser.get().getStatus(), is(Status.DEACTIVATED));
        assertThat(actualUser.get().getDeactivationDate(), notNullValue());
        userDao.delete(actualUser.get().getId());
    }
}