package com.epam.yermak.project.dao.impl;

import com.epam.yermak.project.dao.BookOrderDao;
import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.model.order.BookOrder;
import com.epam.yermak.project.model.order.Issue;
import com.epam.yermak.project.model.order.Order;
import com.epam.yermak.project.model.order.Status;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookOrderDaoImpl implements BookOrderDao {

    private static class Query {
        public static final String SELECT_BOOKS_BY_ORDER_STATUS =
                "SELECT * FROM book_orders bo" +
                        "INNER JOIN books b ON bo.book_id=b.book_id " +
                        "WHERE status=?";
        public static final String ORDER_BOOK =
                "INSERT INTO book_orders (book_id, user_id, status, issue) VALUES (?, ?, 'ORDERED', ?);";
        public static final String RESERVE_BOOK =
                "UPDATE book_orders SET status='RESERVED', reserved_date=? WHERE order_id=?;";
        public static final String RETURN_BOOK =
                "UPDATE book_orders SET status='RETURNED', returned_date=? WHERE order_id=?;";
        public static final String REJECT_ORDER =
                "UPDATE book_orders SET status='REJECTED', rejected_date=? WHERE order_id=?;";
        public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS =
                "SELECT * FROM books b" +
                        "INNER JOIN book_orders bo ON b.book_id = bo.book_id" +
                        "INNER JOIN users u ON bo.user_id = u.user_id" +
                        "WHERE u.user_id=? AND bo.status=?";
        public static final String SET_BOOKS_NUMBER_TO_ONE_LESS =
                "UPDATE books SET number=number-1 " +
                        "WHERE book_id=(SELECT book_id FROM book_orders WHERE order_id=?);";
        public static final String SET_BOOKS_NUMBER_TO_ONE_MORE =
                "UPDATE books SET number=number+1" +
                        " WHERE book_id=(SELECT book_id FROM book_orders WHERE order_id=?);";
        public static final String SELECT_ORDER_BY_ID = "SELECT * FROM book_orders WHERE order_id=?";
        public static final String DELETE_ORDER = "DELETE FROM book_orders WHERE order_id=?";
    }

    private static class ColumnName {
        public static final String ID = "order_id";
        public static final String BOOK_ID = "book_id";
        public static final String USER_ID = "user_id";
        public static final String STATUS = "status";
        public static final String ISSUE = "issue";
        public static final String ORDERED_DATE = "ordered_date";
        public static final String RESERVED_DATE = "reserved_date";
        public static final String RETURNED_DATE = "returned_date";
        public static final String REJECTED_DATE = "rejected_date";
        public static final String USER_FIRSTNAME = "firstname";
        public static final String USER_SECONDNAME = "secondname";
        public static final String BOOK_TITLE = "title";
    }

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException {
        LOGGER.log(Level.INFO, "method findOrdersByOrderStatus");
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_BOOKS_BY_ORDER_STATUS)) {
            preparedStatement.setString(1, orderStatus.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong(ColumnName.ID));
                    order.setBookId(resultSet.getLong(ColumnName.BOOK_ID));
                    order.setUserId(resultSet.getLong(ColumnName.USER_ID));
                    order.setIssue(Issue.valueOf(resultSet.getString(ColumnName.ISSUE).toUpperCase()));
                    order.setStatus(Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
                    order.setOrderedDate(resultSet.getTimestamp(ColumnName.ORDERED_DATE));
                    order.setReservedDate(resultSet.getTimestamp(ColumnName.RESERVED_DATE));
                    order.setReturnedDate(resultSet.getTimestamp(ColumnName.RETURNED_DATE));
                    order.setRejectedDate(resultSet.getTimestamp(ColumnName.REJECTED_DATE));
                    order.setBookTitle(resultSet.getString(ColumnName.BOOK_TITLE));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findOrdersByOrderStatus: ", e);
            throw new DaoException("Exception when find books by order status: {}", e);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException {
        LOGGER.log(Level.INFO, "method findBooksByUserIdAndOrderStatus");
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, orderStatus.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong(ColumnName.ID));
                    order.setBookId(resultSet.getLong(ColumnName.BOOK_ID));
                    order.setUserId(resultSet.getLong(ColumnName.USER_ID));
                    order.setIssue(Issue.valueOf(resultSet.getString(ColumnName.ISSUE).toUpperCase()));
                    order.setStatus(Status.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
                    order.setOrderedDate(resultSet.getTimestamp(ColumnName.ORDERED_DATE));
                    order.setReservedDate(resultSet.getTimestamp(ColumnName.RESERVED_DATE));
                    order.setReturnedDate(resultSet.getTimestamp(ColumnName.RETURNED_DATE));
                    order.setRejectedDate(resultSet.getTimestamp(ColumnName.REJECTED_DATE));
                    order.setBookTitle(resultSet.getString(ColumnName.BOOK_TITLE));
                    order.setUserFirstName(resultSet.getString(ColumnName.USER_FIRSTNAME));
                    order.setUserSecondName(resultSet.getString(ColumnName.USER_SECONDNAME));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
            throw new DaoException("Exception when find reserved books by userId: {}", e);
        }
        return orders;
    }

    @Override
    public Long orderBook(Order order) throws DaoException {
        LOGGER.log(Level.INFO, "method orderBook");
        try (PreparedStatement psOrderBook = getPrepareStatement(Query.ORDER_BOOK);
             PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_LESS)) {
            psOrderBook.setLong(1, order.getBookId());
            psOrderBook.setLong(2, order.getUserId());
            psOrderBook.setString(3, order.getIssue().toString());
            psOrderBook.executeUpdate();
            Long orderId = null;
            try (ResultSet resultSet = psOrderBook.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orderId = resultSet.getLong(1);
                }
            }
            if (orderId != null) {
                psUpdateBookNumber.setLong(1, orderId);
                psUpdateBookNumber.executeUpdate();
            }
            return orderId;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
            throw new DaoException("Exception when order book: {}", e);
        }
    }

    @Override
    public void reserveBook(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method reserveBook");
        try (PreparedStatement psReserveBook = getPrepareStatement(Query.RESERVE_BOOK);) {
            psReserveBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psReserveBook.setLong(2, orderId);
            psReserveBook.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method reserveBook: ", e);
            throw new DaoException("Exception when reserve book: {}", e);
        }
    }

    @Override
    public void returnBook(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method returnBook");
        try (PreparedStatement psReturnBook = getPrepareStatement(Query.RETURN_BOOK);
             PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_MORE);) {
            psReturnBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psReturnBook.setLong(2, orderId);
            psUpdateBookNumber.setLong(1, orderId);
            psReturnBook.executeUpdate();
            psUpdateBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
            throw new DaoException("Exception when return book: {}", e);
        }
    }

    @Override
    public void rejectOrder(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method rejectOrder");
        try (PreparedStatement psRejectBook = getPrepareStatement(Query.REJECT_ORDER);
             PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_MORE);) {
            psRejectBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psRejectBook.setLong(2, orderId);
            psUpdateBookNumber.setLong(1, orderId);
            psRejectBook.executeUpdate();
            psUpdateBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectOrder: ", e);
            throw new DaoException("Exception when reject order: {}", e);
        }
    }

    @Override
    public BookOrder find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        BookOrder bookOrder = null;

        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ORDER_BY_ID);) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    bookOrder = new BookOrder();
                    bookOrder.setId(resultSet.getLong(ColumnName.ID));
                    bookOrder.setBookId(resultSet.getLong(ColumnName.BOOK_ID));
                    bookOrder.setUserId(resultSet.getLong(ColumnName.USER_ID));
                    bookOrder.setStatus(Status.valueOf(
                            resultSet.getString(ColumnName.STATUS).toUpperCase()));
                    bookOrder.setIssue(Issue.valueOf(
                            resultSet.getString(ColumnName.ISSUE).toUpperCase()));
                    bookOrder.setOrderedDate(resultSet.getTimestamp(ColumnName.ORDERED_DATE));
                    bookOrder.setReservedDate(resultSet.getTimestamp(ColumnName.RESERVED_DATE));
                    bookOrder.setReturnedDate(resultSet.getTimestamp(ColumnName.RETURNED_DATE));
                    bookOrder.setRejectedDate(resultSet.getTimestamp(ColumnName.REJECTED_DATE));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find order: {}", e);
        }
        return bookOrder;
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.DELETE_ORDER);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete order: {}", e);
        }
    }

    @Override
    public List<BookOrder> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookOrder create(BookOrder entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookOrder update(BookOrder entity) throws DaoException {
        throw new UnsupportedOperationException();
    }
}