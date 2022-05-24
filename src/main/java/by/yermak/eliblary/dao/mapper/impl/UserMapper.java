package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.model.user.Role;
import by.yermak.eliblary.model.user.Status;
import by.yermak.eliblary.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnNameHelper.*;

public class UserMapper implements EntityMapper<User> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<User> map(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getLong(USER_ID));
            user.setLogin(resultSet.getString(LOGIN));
            user.setPassword(resultSet.getString(PASSWORD));
            user.setFirstName(resultSet.getString(FIRSTNAME));
            user.setSecondName(resultSet.getString(SECONDNAME));
            user.setEmail(resultSet.getString(EMAIL));
            user.setActivationDate(resultSet.getTimestamp(ACTIVATION_DATE));
            user.setDeactivationDate(resultSet.getTimestamp(DEACTIVATION_DATE));
            user.setRole(Role.valueOf(resultSet.getString(ROLE).toUpperCase()));
            user.setStatus(Status.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
            return Optional.of(user);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set");
            return Optional.empty();
        }
    }
}
