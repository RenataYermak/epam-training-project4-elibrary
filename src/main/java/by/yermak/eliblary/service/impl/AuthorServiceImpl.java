package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.AuthorDao;
import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.OrderDao;
import by.yermak.eliblary.dao.UserDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.AuthorDaoImpl;
import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.dao.impl.OrderDaoImpl;
import by.yermak.eliblary.dao.impl.UserDaoImpl;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.BookValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AuthorDao authorDao;


    public AuthorServiceImpl() {
        this.authorDao = new AuthorDaoImpl();
    }

    @Override
    public Author findAuthor(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method find");
        try {
            var authorOptional = authorDao.find(id);
            if (authorOptional.isEmpty()) {
                throw new ServiceException("There is no such author");
            }
            return authorOptional.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new ServiceException("Exception when find author: {}", e);
        }
    }

    @Override
    public List<Author> findAllAuthors() throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return authorDao.findAll();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new ServiceException("Exception when findAll authors: {}", e);
        }
    }
}
