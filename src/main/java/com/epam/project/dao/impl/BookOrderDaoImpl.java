package com.epam.project.dao.impl;

import com.epam.project.dao.BookOrderDao;
import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.order.BookOrder;
import com.epam.project.model.order.Issue;
import com.epam.project.model.order.Order;
import com.epam.project.model.order.Status;
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
                "select * from book_order bo inner join book b on bo.book_id=b.book_id where status=?";
        public static final String ORDER_BOOK =
                "insert into book_order (book_id, user_id, status, issue) values (?, ?, 'ORDERED', ?);";
        public static final String RESERVE_BOOK =
                "update book_order set status='RESERVED', reserved_date=? where order_id=?;";
        public static final String RETURN_BOOK =
                "update book_order set status='RETURNED', returned_date=? where order_id=?;";
        public static final String REJECT_ORDER =
                "update book_order set status='REJECTED', rejected_date=? where order_id=?;";
        public static final String SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS =
                "select * from book b \n" +
                        "inner join book_order bo on b.book_id = bo.book_id\n" +
                        "inner join user u on bo.user_id = u.user_id\n" +
                        "where u.user_id=? and bo.status=?";
        public static final String SET_BOOKS_NUMBER_TO_ONE_LESS =
                "update book set number=number-1 where book_id=(select book_id from book_order where order_id=?);";
        public static final String SET_BOOKS_NUMBER_TO_ONE_MORE =
                "update book set number=number+1 where book_id=(select book_id from book_order where order_id=?);";
        public static final String SELECT_ORDER_BY_ID = "select * from book_order where order_id=?";
        public static final String DELETE_ORDER = "delete from book_order where order_id=?";
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
        PreparedStatement ps = getPrepareStatement(Query.SELECT_BOOKS_BY_ORDER_STATUS);
        try {
            ps.setString(1, orderStatus.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(ColumnName.ID));
                order.setBookId(rs.getLong(ColumnName.BOOK_ID));
                order.setUserId(rs.getLong(ColumnName.USER_ID));
                order.setIssue(Issue.valueOf(rs.getString(ColumnName.ISSUE).toUpperCase()));
                order.setStatus(Status.valueOf(rs.getString(ColumnName.STATUS).toUpperCase()));
                order.setOrderedDate(rs.getTimestamp(ColumnName.ORDERED_DATE));
                order.setReservedDate(rs.getTimestamp(ColumnName.RESERVED_DATE));
                order.setReturnedDate(rs.getTimestamp(ColumnName.RETURNED_DATE));
                order.setRejectedDate(rs.getTimestamp(ColumnName.REJECTED_DATE));
                order.setBookTitle(rs.getString(ColumnName.BOOK_TITLE));
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findOrdersByOrderStatus: ", e);
            throw new DaoException("Exception when find books by order status: {}", e);
        } finally {
            closePrepareStatement(ps);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException {
        LOGGER.log(Level.INFO, "method findBooksByUserIdAndOrderStatus");
        List<Order> orders = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(Query.SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS);
        try {
            ps.setLong(1, userId);
            ps.setString(2, orderStatus.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(ColumnName.ID));
                order.setBookId(rs.getLong(ColumnName.BOOK_ID));
                order.setUserId(rs.getLong(ColumnName.USER_ID));
                order.setIssue(Issue.valueOf(rs.getString(ColumnName.ISSUE).toUpperCase()));
                order.setStatus(Status.valueOf(rs.getString(ColumnName.STATUS).toUpperCase()));
                order.setOrderedDate(rs.getTimestamp(ColumnName.ORDERED_DATE));
                order.setReservedDate(rs.getTimestamp(ColumnName.RESERVED_DATE));
                order.setReturnedDate(rs.getTimestamp(ColumnName.RETURNED_DATE));
                order.setRejectedDate(rs.getTimestamp(ColumnName.REJECTED_DATE));
                order.setBookTitle(rs.getString(ColumnName.BOOK_TITLE));
                order.setUserFirstName(rs.getString(ColumnName.USER_FIRSTNAME));
                order.setUserSecondName(rs.getString(ColumnName.USER_SECONDNAME));
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
            throw new DaoException("Exception when find reserved books by userId: {}", e);
        } finally {
            closePrepareStatement(ps);
        }
        return orders;
    }

    @Override
    public Long orderBook(Order order) throws DaoException {
        LOGGER.log(Level.INFO, "method orderBook");
        PreparedStatement psOrderBook = getPrepareStatement(Query.ORDER_BOOK);
        PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_LESS);
        try {
            psOrderBook.setLong(1, order.getBookId());
            psOrderBook.setLong(2, order.getUserId());
            psOrderBook.setString(3, order.getIssue().toString());
            psOrderBook.executeUpdate();
            Long orderId = null;
            ResultSet rs = psOrderBook.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getLong(1);
            }
            if (orderId != null) {
                psUpdateBookNumber.setLong(1, orderId);
                psUpdateBookNumber.executeUpdate();
            }
            return orderId;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
            throw new DaoException("Exception when order book: {}", e);
        } finally {
            closePrepareStatement(psOrderBook);
            closePrepareStatement(psUpdateBookNumber);
        }
    }

    @Override
    public void reserveBook(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method reserveBook");
        PreparedStatement psReserveBook = getPrepareStatement(Query.RESERVE_BOOK);
        try {
            psReserveBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psReserveBook.setLong(2, orderId);
            psReserveBook.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method reserveBook: ", e);
            throw new DaoException("Exception when reserve book: {}", e);
        } finally {
            closePrepareStatement(psReserveBook);
        }
    }

    @Override
    public void returnBook(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method returnBook");
        PreparedStatement psReturnBook = getPrepareStatement(Query.RETURN_BOOK);
        PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_MORE);
        try {
            psReturnBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psReturnBook.setLong(2, orderId);
            psUpdateBookNumber.setLong(1, orderId);
            psReturnBook.executeUpdate();
            psUpdateBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
            throw new DaoException("Exception when return book: {}", e);
        } finally {
            closePrepareStatement(psReturnBook);
            closePrepareStatement(psUpdateBookNumber);
        }
    }

    @Override
    public void rejectOrder(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method rejectOrder");
        PreparedStatement psRejectBook = getPrepareStatement(Query.REJECT_ORDER);
        PreparedStatement psUpdateBookNumber = getPrepareStatement(Query.SET_BOOKS_NUMBER_TO_ONE_MORE);
        try {
            psRejectBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            psRejectBook.setLong(2, orderId);
            psUpdateBookNumber.setLong(1, orderId);
            psRejectBook.executeUpdate();
            psUpdateBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectOrder: ", e);
            throw new DaoException("Exception when reject order: {}", e);
        } finally {
            closePrepareStatement(psRejectBook);
            closePrepareStatement(psUpdateBookNumber);
        }
    }

    @Override
    public BookOrder find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        BookOrder bookOrder = null;
        PreparedStatement ps = getPrepareStatement(Query.SELECT_ORDER_BY_ID);
        try {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bookOrder = new BookOrder();
                bookOrder.setId(rs.getLong(ColumnName.ID));
                bookOrder.setBookId(rs.getLong(ColumnName.BOOK_ID));
                bookOrder.setUserId(rs.getLong(ColumnName.USER_ID));
                bookOrder.setStatus(Status.valueOf(
                        rs.getString(ColumnName.STATUS).toUpperCase()));
                bookOrder.setIssue(Issue.valueOf(
                        rs.getString(ColumnName.ISSUE).toUpperCase()));
                bookOrder.setOrderedDate(rs.getTimestamp(ColumnName.ORDERED_DATE));
                bookOrder.setReservedDate(rs.getTimestamp(ColumnName.RESERVED_DATE));
                bookOrder.setReturnedDate(rs.getTimestamp(ColumnName.RETURNED_DATE));
                bookOrder.setRejectedDate(rs.getTimestamp(ColumnName.REJECTED_DATE));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find order: {}", e);
        } finally {
            closePrepareStatement(ps);
        }
        return bookOrder;
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        PreparedStatement ps = getPrepareStatement(Query.DELETE_ORDER);
        try {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete order: {}", e);
        } finally {
            closePrepareStatement(ps);
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