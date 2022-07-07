package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;

/**
 * Describes the behavior of {@link Author} entity.
 */
public interface AuthorService {

    /**
     * Find {@link Author} instance by <tt>id</tt>
     *
     * @param id {@link Author}'s id
     * @return {@link Author} instance
     * @throws ServiceException if an error occurs while searching {@link Author}
     *                          into the data source
     */
    Author findAuthor(Long id) throws ServiceException;

    /**
     * Find all author list {@link Author}
     *
     * @return all author list {@link Author}
     * @throws ServiceException if {@link Author} in empty occurs after searching {@link Author}
     *                          into the data source
     */
    List<Author> findAllAuthors() throws ServiceException;

    /**
     * Creat {@link Author} with filled fields
     *
     * @param author {@link Author}
     * @return  {@link Author} instance
     * @throws ServiceException  if an error occurs while writing new {@link Author} into
     *                          data source
     */
    Author create(Author author) throws ServiceException;
}
