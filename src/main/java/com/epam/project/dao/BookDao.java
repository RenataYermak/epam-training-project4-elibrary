package com.epam.project.dao;

import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.book.Book;

import java.util.List;

public interface BookDao extends EntityDao<Book, Long> {
    List<Book> findBooksByQuery(String searchQuery) throws DaoException;
}
