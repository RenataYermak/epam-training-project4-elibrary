package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.impl.BookOrderDaoImpl;
import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.model.order.BookOrder;
import by.yermak.eliblary.model.order.Issue;
import by.yermak.eliblary.model.order.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnNameHelper.*;

public class BookOrderMapper implements EntityMapper<BookOrder> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<BookOrder> map(ResultSet resultSet) throws SQLException {
        try {
            BookOrder bookOrder = new BookOrder();
            bookOrder.setId(resultSet.getLong(BOOK_ORDER_ID));
            bookOrder.setBookId(resultSet.getLong(BOOK_ID));
            bookOrder.setUserId(resultSet.getLong(USER_ID));
            bookOrder.setStatus(Status.valueOf(
                    resultSet.getString(STATUS).toUpperCase()));
            bookOrder.setIssue(Issue.valueOf(
                    resultSet.getString(ISSUE).toUpperCase()));
            bookOrder.setOrderedDate(resultSet.getTimestamp(ORDERED_DATE));
            bookOrder.setReservedDate(resultSet.getTimestamp(RESERVED_DATE));
            bookOrder.setReturnedDate(resultSet.getTimestamp(RETURNED_DATE));
            bookOrder.setRejectedDate(resultSet.getTimestamp(REJECTED_DATE));
            return Optional.of(bookOrder);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set");
            return Optional.empty();
        }
    }
}
