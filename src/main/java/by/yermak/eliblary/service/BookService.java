package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.Validator;

import java.util.List;
import java.util.Set;

/**
 * Describes the behavior of {@link Book} entity.
 */
public interface BookService {
    /**
     * Find book {@link Book} instance by <tt>id</tt>
     *
     * @param id {@link Book}'s id
     * @return {@link Book instance
     * @throws ServiceException if {@link Book} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    Book findBook(Long id) throws ServiceException;

    /**
     * Find all books list {@link Book}
     *
     * @return all books list {@link Book}
     * @throws ServiceException if {@link Book} in empty occurs after searching {@link Book}
     *                          into the data source
     */
    List<Book> findAllBooks() throws ServiceException;

    /**
     * Find  book in list {@link Book}
     *
     * @return book {@link Book}
     * @throws ServiceException if {@link Book} in empty occurs after searching {@link Book}
     *                          into the data source
     */
    List<Book> findBooksByQuery(String searchQuery) throws ServiceException;

    /**
     * Creat {@link Book} with filled fields
     *
     * @param book {@link Book} is filled book instance
     * @return book {@link Book}
     * @throws ServiceException if an error occurs while writing new {@link Book} into
     *                          data source
     */
    Book create(Book book) throws ServiceException;

    /**
     * Update {@link Book} with filled fields
     *
     * @param book {@link Book} is filled user instance
     * @return book {@link Book}
     * @throws ServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@link Validator} or if an error occurs while writing new
     *                          {@link User} into data source
     */
    Book update(Book book) throws ServiceException;

    /**
     * Delete {@link Book} with filled fields
     *
     * @param id {@link Book}'s id
     * @throws ServiceException if {@link Book} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    void delete(Long id) throws ServiceException;

    /**
     * Find books in page  {@link User}
     *
     * @param page count pages
     * @return all  users list {@link User} instance
     * @throws ServiceException if  {@link User}'s list don"t into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    List<Book> findAllBooks(int page) throws ServiceException;
}
