package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.entity.order.Order;
import by.yermak.eliblary.entity.order.Status;
import by.yermak.eliblary.service.OrderService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.util.email.MailSender;
import by.yermak.eliblary.util.locale.LanguageMessage;
import by.yermak.eliblary.util.locale.MessagesKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderDao orderDao;

    public OrderServiceImpl() {
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public Order find(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method findOrder");
        try {
            var orderOptional = orderDao.find(id);
            if (orderOptional.isEmpty()) {
                throw new ServiceException("There is no such order");
            }
            return orderOptional.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findOrder: ", e);
            throw new ServiceException("Exception when find order: {}", e);
        }
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
    public void delete(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method rejectedOrder");
        try {
            orderDao.rejectOrder(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectedOrder: ", e);
            throw new ServiceException("Exception when reject order: {}", e);
        }
    }

    @Override
    public List<Order> findAll(int page, Status orderStatus) throws ServiceException {
        try {
            return orderDao.findAlL(page, orderStatus);
        } catch (DaoException e) {
            throw new ServiceException("Exception in findAllUsers method", e);
        }
    }

    @Override
    public List<Order> findAll(int page, Long userId, Status orderStatus) throws ServiceException {
        try {
            return orderDao.findAlL(page, userId, orderStatus);
        } catch (DaoException e) {
            throw new ServiceException("Exception in findAll method", e);
        }
    }

    @Override
    public boolean isOrderExist(Long bookId, Long userId) throws ServiceException {
        boolean result;
        try {
            result = orderDao.isOrderExist(bookId, userId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "failed to check if order with {} exists", e);
            throw new ServiceException("Exception when find bookId : {}", e);
        }
        return result;
    }

    @Override
    public void sendEmailRejectedOrder(String firstName, String secondName, String bookName, String email, String currentLocale) {
        var finalRegistrationMessage =
                firstName + " " + secondName + ", " + bookName + "." + LanguageMessage.getInstance().getText(currentLocale, "reject.order.mail");
        MailSender.getInstance().send(email, MessagesKey.REJECT_ORDER_HEADER, finalRegistrationMessage);
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Order update(Order entity) throws ServiceException {
        return null;
    }
}
