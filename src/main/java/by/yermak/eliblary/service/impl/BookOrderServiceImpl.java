package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.BookOrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.order.Issue;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.BookOrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookOrderServiceImpl implements BookOrderService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookDao bookDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final Validator validator;

    public BookOrderServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.orderDao = new BookOrderDaoImpl();
        this.userDao = new UserDaoImpl();
        this.validator = new Validator();
    }

//    @Override
//    public BookOrder findOrder(Long id) throws ServiceException {
//        LOGGER.log(Level.INFO, "method find");
//        try {
//            Optional<BookOrder> optionalOrder = orderDao.find(id);
//            if (optionalOrder.isPresent()) {
//                return optionalOrder.get();
//            } else {
//                throw new ServiceException("There is no such order");
//            }
//        } catch (DaoException e) {
//            LOGGER.log(Level.ERROR, "exception in method find: ", e);
//            throw new ServiceException("Exception when find order: {}", e);
//        }
//    }
//
//    @Override
//    public List<BookOrder> findAll() throws ServiceException {
//        LOGGER.log(Level.INFO, "method findAll");
//        try {
//            return orderDao.findAll();
//        } catch (DaoException e) {
//            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
//            throw new ServiceException("Exception when findAll orders: {}", e);
//        }
//    }

    @Override
    public Long orderBook(Long bookId, Long userId, Issue issue) throws ServiceException {
        LOGGER.log(Level.INFO, "method orderBook");
        try {
            Order order = new Order(bookId, userId, issue);
            return orderDao.orderBook(order);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
            throw new ServiceException("Exception when orderBook: {}", e);
        }
    }

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws ServiceException {
        LOGGER.log(Level.INFO, "method findBooksByOrderStatus");
        try {
            List<Order> orders = orderDao.findOrdersByOrderStatus(orderStatus);
            for (Order order : orders) {
                Optional<User> optionalUser = userDao.find(order.getUserId());
                if (optionalUser.isPresent()) {
                    order.setUserFirstName(optionalUser.get().firstName);
                    order.setUserSecondName(optionalUser.get().secondName);
                }
            }
            return orders;
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
}
