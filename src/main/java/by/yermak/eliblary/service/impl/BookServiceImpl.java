package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.BookValidator;
import by.yermak.eliblary.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookValidator validator = BookValidator.getInstance();
    private final BookDao bookDao;
    private final OrderDao orderDao;
    private final UserDao userDao;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.orderDao = new OrderDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    @Override
    public Book findBook(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find");
        try {
            var bookOptional = bookDao.find(id);
            if (bookOptional.isEmpty()) {
                throw new ServiceException("There is no such book");
            }
            return bookOptional.get();
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
        if (!validator.isSearchValid(searchQuery)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            return bookDao.findBooksByQuery(searchQuery);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByQuery: ", e);
            throw new ServiceException("Exception when findBooksByQuery: {}", e);
        }
    }

    @Override
    public boolean create(Book book, byte[] picture) throws ServiceException {
        if (!validator.isBookValid(book)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            bookDao.create(book, picture);
            return true;
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "ProductService error while addNewProduct. {}", e.getMessage());
            throw new ServiceException("ProductService error while addNewProduct.", e);
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

    @Override
    public Book update(Book book) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
        if (!validator.isBookValid(book)) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            var optionalBook = bookDao.update(book);
            if (optionalBook.isEmpty()) {
                throw new ServiceException("There is no such book");
            }
            return optionalBook.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new ServiceException("Exception when update book: {}", e);
        }
    }

    @Override
    public boolean updatePicture(Long id, byte[] picture) throws ServiceException {
        boolean isUpdated = false;
        try {
            isUpdated = bookDao.updatePicture(id, picture);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "ProductService error while updatePhoto. {}", e.getMessage());
            throw new ServiceException("ProductService error while updatePhoto.", e);
        }
        return isUpdated;
    }
}
