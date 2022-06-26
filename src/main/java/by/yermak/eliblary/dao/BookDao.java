package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Book} entity
 */
public interface BookDao extends EntityDao<Book, Long> {
    /**
     * Finds and returns the {@link Book} list
     *
     * @param searchQuery is the string of search {@link Book}
     * @return {@link Book} list
     * @throws DaoException if there is any problem during access
     */
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;

    /**
     * Finds and returns the {@link Book} list in page
     *
     * @param page count pages
     * @return {@link Book} list of books in page
     * @throws DaoException if there is any problem during access
     */
    List<Book> findAllBooks(int page) throws DaoException;

    /**
     * Creates a new book.
     *
     * @param book    is the {@link Book} entity
     * @param picture is the {@link Book}' picture
     * @return true if {@link Book} created successfully
     * @throws DaoException if there is any problem during access
     */
    boolean create(Book book, byte[] picture) throws DaoException;
}
