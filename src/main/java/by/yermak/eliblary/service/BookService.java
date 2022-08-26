package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.validator.BookValidator;

import java.util.List;

/**
 * Describes the behavior of {@link Book} entity.
 */
public interface BookService extends EntityService<Book, Long> {
    /**
     * Creat {@link Book} with filled fields
     *
     * @param book    is the {@link Book}  filled book instance
     * @param picture is the {@link Book}'s picture
     * @return true if book created successfully
     * @throws ServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@link BookValidator}if an error occurs while writing new {@link Book} into
     *                          data source
     */
    boolean create(Book book, byte[] picture) throws ServiceException;

    /**
     * Find  book in list {@link Book}
     *
     * @return book {@link Book}
     * @throws ServiceException if {@link Book} in empty occurs after searching {@link Book}
     *                          into the data source
     */
    List<Book> findBooksByQuery(String searchQuery) throws ServiceException;

    /**
     * Find {@link Book}'s in page
     *
     * @param page count pages
     * @return all {@link Book}'s list
     * @throws ServiceException if  {@link Book}'s list don"t into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    List<Book> findAll(int page) throws ServiceException;

    /**
     * Update {@link Book} with filled fields
     *
     * @param id      is the {@link Book}'s id
     * @param picture is the {@link Book}'s picture
     * @return true if {@link Book} picture update successfully
     * @throws ServiceException if an error occurs while writing new {@link Book} into data source
     */
    boolean updatePicture(Long id, byte[] picture) throws ServiceException;
}
