package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.mapper.impl.OrderMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.yermak.eliblary.dao.sql.BookSql.ELEMENTS_ON_PAGE;
import static by.yermak.eliblary.dao.sql.OrderSql.*;

public class OrderDaoImpl implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger();
    OrderMapper orderMapper = new OrderMapper();

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws DaoException {
        LOGGER.log(Level.INFO, "method findOrdersByOrderStatus");
        List<Order> orders = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_ORDER_STATUS)) {
            preparedStatement.setString(1, orderStatus.toString());
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = orderMapper.map(resultSet);
                    optionalBook.ifPresent(orders::add);
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ORDERED_BOOKS_BY_USER_ID_AND_STATUS)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, orderStatus.toString());
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = orderMapper.map(resultSet);
                    optionalBook.ifPresent(orders::add);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
            throw new DaoException("Exception when find books by order status and  userId: {}", e);
        }
        return orders;
    }


    @Override
    public Long orderBook(Order order) throws DaoException {
        LOGGER.log(Level.INFO, "method orderBook");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatementOrderBook = connection.prepareStatement(ORDER_BOOK, Statement.RETURN_GENERATED_KEYS);
             var preparedStatementBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_LESS)) {
            preparedStatementOrderBook.setLong(1, order.getBook().getId());
            preparedStatementOrderBook.setLong(2, order.getUser().getId());
            preparedStatementOrderBook.setString(3, order.getType().toString());
            preparedStatementOrderBook.executeUpdate();
            Long orderId = null;
            try (var resultSet = preparedStatementOrderBook.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orderId = resultSet.getLong(1);
                }
                if (orderId != null) {
                    preparedStatementBookNumber.setLong(1, orderId);
                    preparedStatementBookNumber.executeUpdate();
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
        LocalDateTime localDate = LocalDateTime.now().plusMonths(1);
        try (var connection = ConnectionPool.getInstance().getConnection();
             var psReserveBook = connection.prepareStatement(RESERVE_BOOK);) {
            psReserveBook.setTimestamp(1, Timestamp.valueOf(localDate));
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
        LocalDateTime localDate = LocalDateTime.now();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatementReturnBook = connection.prepareStatement(RETURN_BOOK);
             var preparedStatementBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
            preparedStatementReturnBook.setTimestamp(1, Timestamp.valueOf(localDate));
            preparedStatementReturnBook.setLong(2, orderId);
            preparedStatementBookNumber.setLong(1, orderId);
            preparedStatementReturnBook.executeUpdate();
            preparedStatementBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
            throw new DaoException("Exception when return book: {}", e);
        }
    }

    @Override
    public void rejectOrder(Long orderId) throws DaoException {
        LOGGER.log(Level.INFO, "method rejectOrder");
        LocalDateTime localDate = LocalDateTime.now();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatementRejectBook = connection.prepareStatement(REJECT_ORDER);
             var preparedStatementUpdateBookNumber = connection.prepareStatement(SET_BOOKS_NUMBER_TO_ONE_MORE);) {
            preparedStatementRejectBook.setTimestamp(1, Timestamp.valueOf(localDate));
            preparedStatementRejectBook.setLong(2, orderId);
            preparedStatementUpdateBookNumber.setLong(1, orderId);
            preparedStatementRejectBook.executeUpdate();
            preparedStatementUpdateBookNumber.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectOrder: ", e);
            throw new DaoException("Exception when reject order: {}", e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_ORDER);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete order: {}", e);
        }
    }

    @Override
    public Optional<Order> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID);) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
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
    public List<Order> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<Order> orders = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalBook = orderMapper.map(resultSet);
                optionalBook.ifPresent(orders::add);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll orders: {}", e);
        }
        return orders;
    }

    @Override
    public List<Order> findAlL(int page, Long userId, Status orderStatus) throws DaoException {
        List<Order> ordersOnPage = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(FIND_PAGE_QUERY_ORDERS_BY_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, orderStatus.toString());
            preparedStatement.setInt(3, ELEMENTS_ON_PAGE * (page - 1));
            preparedStatement.setInt(4, ELEMENTS_ON_PAGE);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = orderMapper.map(resultSet);
                    optionalBook.ifPresent(ordersOnPage::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all users on defined page ", e);
        }
        return ordersOnPage;
    }

    @Override
    public List<Order> findAlL(int page, Status orderStatus) throws DaoException {
        List<Order> ordersOnPage = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(FIND_PAGE_QUERY_ORDERS)) {
            preparedStatement.setString(1, orderStatus.toString());
            preparedStatement.setInt(2, ELEMENTS_ON_PAGE * (page - 1));
            preparedStatement.setInt(3, ELEMENTS_ON_PAGE);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = orderMapper.map(resultSet);
                    optionalBook.ifPresent(ordersOnPage::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all users on defined page ", e);
        }
        return ordersOnPage;
    }

    @Override
    public boolean isOrderExist(Long bookId, Long userId) throws DaoException {
        boolean isExist = false;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SQL_IS_BOOK_ORDER_EXIST)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, userId);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isExist = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("failed to check if user with {} email exists", bookId, e);
            throw new DaoException("failed to check if user with " + bookId + " exists", e);
        }
        return isExist;
    }

    @Override
    public Optional<Order> create(Order entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Order> update(Order entity) throws DaoException {
        return Optional.empty();
    }
}


