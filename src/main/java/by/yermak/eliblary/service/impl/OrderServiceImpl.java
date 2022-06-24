package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookDao bookDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final Validator validator = Validator.getInstance();

    public OrderServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.orderDao = new OrderDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws ServiceException {
        LOGGER.log(Level.INFO, "method findBooksByOrderStatus");
        try {
           return orderDao.findOrdersByOrderStatus(orderStatus);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByOrderStatus: ", e);
            throw new ServiceException("Exception when findBooksByOrderStatus: {}", e);
        }
    }

    @Override
    public List<Order> findOrdersByUserIdAndStatus(Long userId, Status orderStatus) throws ServiceException {
        LOGGER.log(Level.INFO, "method findBooksByUserIdAndOrderStatus");
        try {
            return orderDao.findOrdersByUserIdAndOrderStatus(userId, orderStatus);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
            throw new ServiceException("Exception when findBooksByUserIdAndOrderStatus: {}", e);
        }
    }

    @Override
    public Long orderBook(Order order) throws ServiceException {
        LOGGER.log(Level.INFO, "method orderBook");

        try {
            return orderDao.orderBook(order);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
            throw new ServiceException("Exception when orderBook: {}", e);
        }
    }


    @Override
    public void reserveBook(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method reserveBook");
        try {
            orderDao.reserveBook(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method reserveBook: ", e);
            throw new ServiceException("Exception when reserveBook: {}", e);
        }
    }

    @Override
    public void returnBook(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method returnBook");
        try {
            orderDao.returnBook(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
            throw new ServiceException("Exception when returnBook: {}", e);
        }
    }

    @Override
    public void rejectedOrder(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method rejectedOrder");
        try {
            orderDao.rejectOrder(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectedOrder: ", e);
            throw new ServiceException("Exception when reject order: {}", e);
        }
    }
    @Override
    public List<Order> findAll(int page,Status orderStatus) throws ServiceException {
        try {
            return orderDao.findAlL(page, orderStatus);
        } catch (DaoException e) {
            throw new ServiceException("Exception in findAllUsers method", e);
        }
    }
}
