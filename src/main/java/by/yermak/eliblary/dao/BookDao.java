package by.yermak.eliblary.dao;

import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public interface BookDao extends EntityDao<Book> {
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;
}
