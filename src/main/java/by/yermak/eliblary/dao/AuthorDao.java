package by.yermak.eliblary.dao;

import by.yermak.eliblary.entity.book.Author;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore {@link Author} entity
 */
public interface AuthorDao extends EntityDao<Author, Long> {
}
