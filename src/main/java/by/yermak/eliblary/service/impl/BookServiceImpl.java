package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.entity.book.Category;
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
import by.yermak.eliblary.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.yermak.eliblary.controller.RequestParameter.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.yermak.eliblary.controller.RequestParameter.*;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Validator validator = Validator.getInstance();
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
    public boolean create(Map<String, String> bookData, byte[] picture) throws ServiceException {
        try {
            Book book = new Book();
            book.setTitle(bookData.get(BOOK_TITLE));
            book.setAuthor(bookData.get(BOOK_AUTHOR));
            book.setCategory(Category.valueOf(bookData.get(BOOK_CATEGORY).toUpperCase()));
            book.setPublishYear(Integer.parseInt(bookData.get(BOOK_PUBLISH_YEAR)));
            book.setDescription(bookData.get(BOOK_DESCRIPTION));
            book.setNumber(Integer.parseInt(bookData.get(BOOK_NUMBER)));

            bookDao.create(book, picture);
            return true;
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "ProductService error while addNewProduct. {}", e.getMessage());
            throw new ServiceException("ProductService error while addNewProduct.", e);
        }

    }

    @Override
    public Book update(Book book) throws ServiceException {
        LOGGER.log(Level.INFO, "method update");
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
