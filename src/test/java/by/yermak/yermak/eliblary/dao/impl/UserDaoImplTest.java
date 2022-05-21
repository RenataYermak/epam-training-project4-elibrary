//package by.yermak.yermak.eliblary.dao.impl;
//
//import by.yermak.eliblary.dao.impl.UserDaoImpl;
//import by.yermak.eliblary.model.user.Status;
//import by.yermak.eliblary.model.user.User;
//import by.yermak.eliblary.dao.UserDao;
//import by.yermak.eliblary.dao.exception.DaoException;
//import org.hamcrest.core.Is;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestUser;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.hamcrest.core.IsNull.nullValue;
//
//class UserDaoImplTest {
//
//    private final UserDao userDao = new UserDaoImpl();
//
//    @Test
//    @DisplayName("Should find user by id")
//    void shouldFindUserById() throws DaoException {
//        User actualUser = constructTestUser();
//        userDao.create(actualUser);
//        User expectedUser = userDao.find(actualUser.getId());
//        assertThat(actualUser, is(expectedUser));
//        userDao.delete(expectedUser.getId());
//    }
//
//    @Test
//    @DisplayName("Should find user by login and password")
//    void shouldFindUserByLoginAndPassword() throws DaoException {
//        User actualUser = constructTestUser();
//        userDao.create(actualUser);
//        User expectedUser = userDao.find(actualUser.getLogin(), actualUser.getPassword());
//        assertThat(actualUser, is(expectedUser));
//        userDao.delete(expectedUser.getId());
//    }
//
//    @Test
//    @DisplayName("Should find all users")
//    void shouldFindAllUsers() throws DaoException {
//        int count = 3;
//        for (int i = 0; i < count; i++) {
//            userDao.create(constructTestUser());
//        }
//        List<User> expectedUser = userDao.findAll();
//        assertThat(expectedUser.size(), is(count));
//        for (int i = 0; i < count; i++) {
//            userDao.delete(expectedUser.get(i).getId());
//        }
//    }
//
//    @Test
//    @DisplayName("Should find all activated users")
//    void shouldFindActivatedUsers() throws DaoException {
//        int count = 3;
//        for (int i = 0; i < count; i++) {
//            userDao.create(constructTestUser());
//        }
//        List<User> expectedUser = userDao.findActivatedUsers();
//        assertThat(expectedUser.size(), is(count));
//        for (int i = 0; i < count; i++) {
//            userDao.delete(expectedUser.get(i).getId());
//        }
//    }
//
//    @Test
//    @DisplayName("Should find all deactivated users")
//    void shouldFindDeactivatedUsers() throws DaoException {
//        int count = 3;
//        for (int i = 0; i < count; i++) {
//            User actualUser = userDao.create(constructTestUser());
//            userDao.deactivate(actualUser.getId());
//        }
//        List<User> expectedUser = userDao.findDeactivatedUsers();
//        assertThat(expectedUser.size(), is(count));
//        for (int i = 0; i < count; i++) {
//            userDao.delete(expectedUser.get(i).getId());
//        }
//    }
//
//    @Test
//    @DisplayName("Should create user")
//    void shouldCreateUser() throws DaoException {
//        User actualUser = constructTestUser();
//        User expectedUser = userDao.create(actualUser);
//        assertThat(actualUser, is(expectedUser));
//        userDao.delete(expectedUser.getId());
//    }
//
//    @Test
//    @DisplayName("Should update user")
//    void shouldUpdateUser() throws DaoException {
//        User actualUser = constructTestUser();
//        userDao.create(actualUser);
//        actualUser.setPassword("UpdatedPassword");
//        User expectedUser = userDao.update(actualUser);
//        assertThat(actualUser, is(expectedUser));
//        userDao.delete(expectedUser.getId());
//    }
//
//    @Test
//    @DisplayName("Should delete user")
//    void shouldDeleteUser() throws DaoException {
//        User actualUser = constructTestUser();
//        userDao.create(actualUser);
//        userDao.delete(actualUser.getId());
//        User expected = userDao.find(actualUser.getId());
//        assertThat(expected, nullValue());
//    }
//
//    @Test
//    @DisplayName("Should deactivate user")
//    void shouldDeactivateUser() throws DaoException {
//        User actualUser = constructTestUser();
//        userDao.create(actualUser);
//        assertThat(actualUser.getStatus(), Is.is(Status.ACTIVATED));
//        userDao.deactivate(actualUser.getId());
//        actualUser = userDao.find(actualUser.getId());
//        assertThat(actualUser.getStatus(), is(Status.DEACTIVATED));
//        assertThat(actualUser.getDeactivationDate(), notNullValue());
//        userDao.delete(actualUser.getId());
//    }
//}