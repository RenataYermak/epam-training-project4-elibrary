package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.BookOrderDao;
import by.yermak.eliblary.dao.mapper.impl.BookOrderMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.entity.order.BookOrder;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.order.Type;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.yermak.eliblary.dao.QuerySQL.*;
import static by.yermak.eliblary.dao.mapper.ColumnName.*;

public class OrderDaoImpl implements BookOrderDao {
    private static final Logger LOGGER = LogManager.getLogger();
    BookOrderMapper orderMapper = new BookOrderMapper();

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException {
        LOGGER.log(Level.INFO, "method findOrdersByOrderStatus");
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_ORDER_STATUS)) {
            preparedStatement.setString(1, orderStatus.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong(ORDER_ID));
                    order.setBookId(resultSet.getLong(BOOK_ID));
                    order.setUserId(resultSet.getLong(USER_ID));
                    order.setType(Type.valueOf(resultSet.getString(TYPE).toUpperCase()));
                    order.setStatus(Status.valueOf(resultSet.getString(STATUS).toUpperCase()));
                    order.setOrderedDate(resultSet.getTimestamp(ORDERED_DATE));
                    order.setReservedDate(resultSet.getTimestamp(RESERVED_DATE));
                    order.setReturnedDate(resultSet.getTimestamp(RETURNED_DATE));
                    order.setRejectedDate(resultSet.getTimestamp(REJECTED_DATE));
                    order.setBookTitle(resultSet.getString(BOOK_TITLE));
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, orderStatus.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getLong(ORDER_ID));
                    order.setBookId(resultSet.getLong(BOOK_ID));
                    order.setUserId(resultSet.getLong(USER_ID));
                    order.setType(Type.valueOf(resultSet.getString(TYPE).toUpperCase()));
                    order.setStatus(Status.valueOf(resultSet.getString(STATUS).toUpperCase()));
                    order.setOrderedDate(resultSet.getTimestamp(ORDERED_DATE));
                    order.setReservedDate(resultSet.getTimestamp(RESERVED_DATE));
                    order.setReturnedDate(resultSet.getTimestamp(RETURNED_DATE));
                    order.setRejectedDate(resultSet.getTimestamp(REJECTED_DATE));
                    order.setBookTitle(resultSet.getString(BOOK_TITLE));
                    order.setUserFirstName(resultSet.getString(USER_FIRSTNAME));
                    order.setUserSecondName(resultSet.getString(USER_SECONDNAME));
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psOrderBook = connection.prepareStatement(ORDER_BOOK, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_LESS)) {
            psOrderBook.setLong(1, order.getBookId());
            psOrderBook.setLong(2, order.getUserId());
            psOrderBook.setString(3, order.getType().toString());
            psOrderBook.executeUpdate();
            Long orderId = null;
            try (ResultSet resultSet = psOrderBook.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orderId = resultSet.getLong(1);
                }
                if (orderId != null) {
                    psUpdateBookNumber.setLong(1, orderId);
                    psUpdateBookNumber.executeUpdate();
                }
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psReserveBook = connection.prepareStatement(RESERVE_BOOK);) {
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psReturnBook = connection.prepareStatement(RETURN_BOOK);
             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
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
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement psRejectBook = connection.prepareStatement(REJECT_ORDER);
             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
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
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete order: {}", e);
        }
    }

    @Override
    public Optional<BookOrder> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return orderMapper.map(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find order: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public List<BookOrder> findAll() throws DaoException {
        return null;
    }


    @Override
    public Optional<BookOrder> create(BookOrder entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<BookOrder> update(BookOrder entity) throws DaoException {
        return Optional.empty();
    }
}
//    @Override
//    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException {
//        LOGGER.log(Level.INFO, "method findOrdersByOrderStatus");
//        List<Order> orders = new ArrayList<>();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_ORDER_STATUS)) {
//            preparedStatement.setString(1, orderStatus.toString());
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    var optionalBook = orderMapper.map(resultSet);
//                    optionalBook.ifPresent(orders::add);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method findOrdersByOrderStatus: ", e);
//            throw new DaoException("Exception when find books by order status: {}", e);
//        }
//        return orders;
//    }
//
//
//    @Override
//    public List<Order> findOrdersByUserIdAndOrderStatus(Long userId, Status orderStatus) throws DaoException {
//        LOGGER.log(Level.INFO, "method findBooksByUserIdAndOrderStatus");
//        List<Order> orders = new ArrayList<>();
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS)) {
//            preparedStatement.setLong(1, userId);
//            preparedStatement.setString(2, orderStatus.toString());
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    var optionalBook = orderMapper.map(resultSet);
//                    optionalBook.ifPresent(orders::add);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
//            throw new DaoException("Exception when find reserved books by userId: {}", e);
//        }
//        return orders;
//    }
//
//
//    @Override
//    public Long orderBook(Order order) throws DaoException {
//        LOGGER.log(Level.INFO, "method orderBook");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement psOrderBook = connection.prepareStatement(ORDER_BOOK, Statement.RETURN_GENERATED_KEYS);
//             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_LESS)) {
//            psOrderBook.setLong(1, order.getBookId());
//            psOrderBook.setLong(2, order.getUserId());
//            psOrderBook.setString(3, order.getType().toString());
//            psOrderBook.executeUpdate();
//            Long orderId = null;
//            try (ResultSet resultSet = psOrderBook.getGeneratedKeys()) {
//                if (resultSet.next()) {
//                    orderId = resultSet.getLong(1);
//                }
//                if (orderId != null) {
//                    psUpdateBookNumber.setLong(1, orderId);
//                    psUpdateBookNumber.executeUpdate();
//                }
//            }
//            return orderId;
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
//            throw new DaoException("Exception when order book: {}", e);
//        }
//    }
//
//    @Override
//    public void reserveBook(Long orderId) throws DaoException {
//        LOGGER.log(Level.INFO, "method reserveBook");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement psReserveBook = connection.prepareStatement(RESERVE_BOOK);) {
//            psReserveBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//            psReserveBook.setLong(2, orderId);
//            psReserveBook.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method reserveBook: ", e);
//            throw new DaoException("Exception when reserve book: {}", e);
//        }
//    }
//
//    @Override
//    public void returnBook(Long orderId) throws DaoException {
//        LOGGER.log(Level.INFO, "method returnBook");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement psReturnBook = connection.prepareStatement(RETURN_BOOK);
//             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
//            psReturnBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//            psReturnBook.setLong(2, orderId);
//            psUpdateBookNumber.setLong(1, orderId);
//            psReturnBook.executeUpdate();
//            psUpdateBookNumber.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
//            throw new DaoException("Exception when return book: {}", e);
//        }
//    }
//
//    @Override
//    public void rejectOrder(Long orderId) throws DaoException {
//        LOGGER.log(Level.INFO, "method rejectOrder");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement psRejectBook = connection.prepareStatement(REJECT_ORDER);
//             PreparedStatement psUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
//            psRejectBook.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//            psRejectBook.setLong(2, orderId);
//            psUpdateBookNumber.setLong(1, orderId);
//            psRejectBook.executeUpdate();
//            psUpdateBookNumber.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method rejectOrder: ", e);
//            throw new DaoException("Exception when reject order: {}", e);
//        }
//    }
//
//
//    @Override
//    public void delete(Long id) throws DaoException {
//        LOGGER.log(Level.INFO, "method delete");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);) {
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
//            throw new DaoException("Exception when delete order: {}", e);
//        }
//    }
//
//    @Override
//    public Optional<Order> find(Long id) throws DaoException {
//        LOGGER.log(Level.INFO, "method find");
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID);) {
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return orderMapper.map(resultSet);
//            }
//        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, "exception in method find: ", e);
//            throw new DaoException("Exception when find order: {}", e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Order> findAll() throws DaoException {
//        return null;
//    }
//
//
//    @Override
//    public Optional<Order> create(Order entity) throws DaoException {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Order> update(Order entity) throws DaoException {
//        return Optional.empty();
//    }
//}


