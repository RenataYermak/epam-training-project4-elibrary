package by.yermak.eliblary.dao;

import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

/**
 * The interface Book dao
 */
public interface BookDao extends EntityDao<Book> {
    /**
     * Finds books in search query and collect them in the list
     *
     * @return a list of users
     * @throws DaoException if there is any problem during access
     */
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;
}
