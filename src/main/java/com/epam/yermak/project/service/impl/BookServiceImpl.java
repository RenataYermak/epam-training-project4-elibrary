package com.epam.yermak.project.service.impl;

import com.epam.yermak.project.dao.BookDao;
import com.epam.yermak.project.dao.BookOrderDao;
import com.epam.yermak.project.dao.BookStatisticDao;
import com.epam.yermak.project.dao.UserDao;
import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.dao.impl.BookDaoImpl;
import com.epam.yermak.project.dao.impl.BookOrderDaoImpl;
import com.epam.yermak.project.dao.impl.BookStatisticDaoImpl;
import com.epam.yermak.project.dao.impl.UserDaoImpl;
import com.epam.yermak.project.model.book.Book;
import com.epam.yermak.project.model.order.Issue;
import com.epam.yermak.project.model.order.Order;
import com.epam.yermak.project.model.order.Status;
import com.epam.yermak.project.model.user.User;
import com.epam.yermak.project.service.BookService;
import com.epam.yermak.project.service.exception.ServiceException;
import com.epam.yermak.project.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookDao bookDao;
    private final BookOrderDao bookOrderDao;
    private final BookStatisticDao bookStatisticDao;
    private final UserDao userDao;
    private final Validator validator;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.bookOrderDao = new BookOrderDaoImpl();
        this.bookStatisticDao = new BookStatisticDaoImpl();
        this.userDao = new UserDaoImpl();
        this.validator = new Validator();
    }

    @Override
    public Book findBook(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find");
        try {
            return bookDao.find(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new ServiceException("Exception when find book: {}", e);
        }
    }

    @Override
    public List<Book> findAllBooks() throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return bookDao.findAll();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new ServiceException("Exception when findAll books: {}", e);
        }
    }

    @Override
    public List<Book> findBooksByQuery(String searchQuery) throws ServiceException {
        LOGGER.log(Level.INFO, "method findBooksByQuery");
        if (validator.isSearchValid(searchQuery)) {
            try {
                return bookDao.findBooksByQuery(searchQuery);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "exception in method findBooksByQuery: ", e);
                throw new ServiceException("Exception when findBooksByQuery: {}", e);
            }

        } else {
            throw new ServiceException("Input data is invalid");
        }
    }


    @Override
    public Book create(Book book) throws ServiceException {
        LOGGER.log(Level.INFO, "method create");
        try {
            return bookDao.create(book);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new ServiceException("Exception when create book: {}", e);
        }
    }

    @Override
    public Book update(Book book) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
        try {
            return bookDao.update(book);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new ServiceException("Exception when update book: {}", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method delete");
        try {
            bookDao.delete(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new ServiceException("Exception when delete book: {}", e);
        }
    }

    @Override
    public List<Order> findOrdersByOrderStatus(Status orderStatus) throws ServiceException {
        LOGGER.log(Level.INFO, "method findBooksByOrderStatus");
        try {
            List<Order> orders = bookOrderDao.findOrdersByOrderStatus(orderStatus);
            for (Order order : orders) {
                User user = userDao.find(order.getUserId());
                order.setUserFirstName(user.firstName);
                order.setUserSecondName(user.secondName);
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
            return bookOrderDao.findOrdersByUserIdAndOrderStatus(userId, orderStatus);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByUserIdAndOrderStatus: ", e);
            throw new ServiceException("Exception when findBooksByUserIdAndOrderStatus: {}", e);
        }
    }

    @Override
    public Long orderBook(Long bookId, Long userId, Issue issue) throws ServiceException {
        LOGGER.log(Level.INFO, "method orderBook");
        try {
            Order order = new Order(bookId, userId, issue);
            return bookOrderDao.orderBook(order);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method orderBook: ", e);
            throw new ServiceException("Exception when orderBook: {}", e);
        }
    }

    @Override
    public void reserveBook(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method reserveBook");
        try {
            bookOrderDao.reserveBook(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method reserveBook: ", e);
            throw new ServiceException("Exception when reserveBook: {}", e);
        }
    }

    @Override
    public void returnBook(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method returnBook");
        try {
            bookOrderDao.returnBook(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method returnBook: ", e);
            throw new ServiceException("Exception when returnBook: {}", e);
        }
    }

    @Override
    public void rejectedOrder(Long orderId) throws ServiceException {
        LOGGER.log(Level.INFO, "method rejectedOrder");
        try {
            bookOrderDao.rejectOrder(orderId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method rejectedOrder: ", e);
            throw new ServiceException("Exception when reject order: {}", e);
        }
    }
}
