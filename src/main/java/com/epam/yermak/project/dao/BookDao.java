package com.epam.yermak.project.dao;

import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.model.book.Book;

import java.util.List;

public interface BookDao extends EntityDao<Book, Long> {
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;
}
