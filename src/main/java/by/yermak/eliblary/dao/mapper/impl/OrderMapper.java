package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.order.Type;
import by.yermak.eliblary.entity.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnName.*;

public class OrderMapper implements EntityMapper<Order> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<Order> map(ResultSet resultSet) {
        try {
            var order = new Order();
            order.setId(resultSet.getLong(ORDER_ID));
            order.setStatus(Status.valueOf(
                    resultSet.getString(STATUS).toUpperCase()));
            order.setType(Type.valueOf(
                    resultSet.getString(TYPE).toUpperCase()));
            order.setOrderedDate(resultSet.getObject(ORDERED_DATE, LocalDateTime.class));
            order.setReservedDate(resultSet.getObject(RESERVED_DATE, LocalDateTime.class));
            order.setReturnedDate(resultSet.getObject(RETURNED_DATE, LocalDateTime.class));
            order.setRejectedDate(resultSet.getObject(REJECTED_DATE, LocalDateTime.class));
            var userMapper =new UserMapper();
            Optional<User> user = userMapper.map(resultSet);
            user.ifPresent(order::setUser);
            var bookMapper = new BookMapper();
            Optional<Book> book = bookMapper.map(resultSet);
            book.ifPresent(order::setBook);
            return Optional.of(order);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set ", e);
            return Optional.empty();
        }
    }
}

