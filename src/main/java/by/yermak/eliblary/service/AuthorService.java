package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {

    /**
     * Find book {@link Author} instance by <tt>id</tt>
     *
     * @param id {@link Author}'s id
     * @return {@link Author} instance
     * @throws ServiceException if {@link Author} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link Author}
     *                          into the data source
     */
    Author findAuthor(Long id) throws ServiceException;

    /**
     * Find all books list {@link Author}
     *
     * @return all books list {@link Author}
     * @throws ServiceException if {@link Author} in empty occurs after searching {@link Author}
     *                          into the data source
     */
    List<Author> findAllAuthors() throws ServiceException;
}
