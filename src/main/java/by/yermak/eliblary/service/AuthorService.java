package by.yermak.eliblary.service;

import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.exception.ServiceException;

/**
 * Describes the behavior of {@link Author} entity.
 */
public interface AuthorService extends EntityService<Author, Long> {

    /**
     * Creat {@link Author} with filled fields
     *
     * @param author {@link Author}
     * @return {@link Author} instance
     * @throws ServiceException if an error occurs while writing new {@link Author} into
     *                          data source
     */
    Author create(Author author) throws ServiceException;

    /**
     * Checks whether an {@link Author}'s name is already exist
     *
     * @param authorName a {@link Author}'s name
     * @return whether an {@link Author}'s name is already exist
     */
    boolean isAuthorExist(String authorName) throws ServiceException;
}
