package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.BookOrderDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookDao bookDao;
    private final BookOrderDao orderDao;
    private final UserDao userDao;
    private final Validator validator;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.orderDao = new OrderDaoImpl();
        this.userDao = new UserDaoImpl();
        this.validator = new Validator();
    }

    @Override
    public Book findBook(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find");
        try {
            var bookOptional = bookDao.find(id);
            if (bookOptional.isPresent()) {
                return bookOptional.get();
            } else {
                throw new ServiceException("There is no such book");
            }
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
            var optionalBook = bookDao.create(book);
            if (optionalBook.isPresent()) {
                return optionalBook.get();
            } else {
                throw new ServiceException("There is no such book");
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new ServiceException("Exception when create book: {}", e);
        }
    }

    @Override
    public Book update(Book book) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
        try {
            var optionalBook = bookDao.update(book);
            if (optionalBook.isPresent()) {
                return optionalBook.get();
            } else {
                throw new ServiceException("There is no such book");
            }
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
    public List<Book> findAllBooks(int page) throws ServiceException {
        try {
            return bookDao.findAllBooks(page);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAllBooks: ", e);
            throw new ServiceException("Exception in findAllUsers method: {}", e);
        }
    }

}
