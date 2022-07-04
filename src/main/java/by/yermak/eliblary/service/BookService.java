package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.UserValidator;

import java.util.List;

/**
 * Describes the behavior of {@link Book} entity.
 */
public interface BookService {

    /**
     * Creat {@link Book} with filled fields
     *
     * @param book    is the {@link Book}  filled book instance
     * @param picture is the {@link Book}'s picture
     * @return true if book created successfully
     * @throws ServiceException if an error occurs while writing new {@link Book} into
     *                          data source
     */
    boolean create(Book book, byte[] picture) throws ServiceException;


    /**
     * Find book {@link Book} instance by <tt>id</tt>
     *
     * @param id {@link Book}'s id
     * @return {@link Book} instance
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
     * Update {@link Book} with filled fields
     *
     * @param book {@link Book} is filled user instance
     * @return book {@link Book}
     * @throws ServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@link UserValidator} or if an error occurs while writing new
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
     * Find {@link Book}'s in page
     *
     * @param page count pages
     * @return all {@link Book}'s list
     * @throws ServiceException if  {@link Book}'s list don"t into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    List<Book> findAllBooks(int page) throws ServiceException;

    /**
     * Update {@link Book} with filled fields
     *
     * @param book    is the {@link Book}  filled book instance
     * @param picture is the {@link Book}'s picture
     * @return true if book update successfully
     * @throws ServiceException if an error occurs while writing new {@link Book} into data source
     */
    boolean update(Book book, byte[] picture) throws ServiceException;
}
