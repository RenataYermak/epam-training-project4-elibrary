package by.yermak.eliblary.service.impl;

import by.yermak.eliblary.dao.AuthorDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.impl.AuthorDaoImpl;
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
    private final BookValidator validator = BookValidator.getInstance();

    public AuthorServiceImpl() {
        this.authorDao = new AuthorDaoImpl();
    }

    @Override
    public Author find(Long id) throws ServiceException {
        LOGGER.log(Level.INFO, "method findAuthor");
        try {
            var authorOptional = authorDao.find(id);
            if (authorOptional.isEmpty()) {
                throw new ServiceException("There is no such author");
            }
            return authorOptional.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAuthor: ", e);
            throw new ServiceException("Exception when find author: {}", e);
        }
    }

    @Override
    public List<Author> findAll() throws ServiceException {
        LOGGER.log(Level.INFO, "method findAll");
        try {
            return authorDao.findAll();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new ServiceException("Exception when findAll authors: {}", e);
        }
    }

    @Override
    public Author create(Author author) throws ServiceException {
        LOGGER.log(Level.INFO, "method create");
        if (!validator.isAuthorValid(author.getName())) {
            throw new ServiceException("Input data is invalid");
        }
        try {
            var optionalAuthor = authorDao.create(author);
            if (optionalAuthor.isEmpty()) {
                throw new ServiceException("Input data is invalid");
            }
            return optionalAuthor.get();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new ServiceException("Exception when create author: {}", e);
        }
    }

    @Override
    public boolean isAuthorExist(String authorName) throws ServiceException {
        boolean result;
        try {
            result = authorDao.isAuthorExist(authorName);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "failed to check if author with {} exists", e);
            throw new ServiceException("Exception when find author  : {}", e);
        }
        return result;
    }

    @Override
    public Author update(Author entity) throws ServiceException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException {
    }
}
