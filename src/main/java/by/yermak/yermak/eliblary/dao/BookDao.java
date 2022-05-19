package by.yermak.yermak.eliblary.dao;

import by.yermak.yermak.eliblary.model.book.Book;
import by.yermak.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public interface BookDao extends EntityDao<Book, Long> {
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;
}
