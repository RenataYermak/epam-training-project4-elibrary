package com.epam.project.dao.impl;

import com.epam.project.dao.BookStatisticDao;
import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.statistic.BookStatistic;

import java.util.List;

public class BookStatisticDaoImpl implements BookStatisticDao {

    private static class Query {
        public static final String RATE_BOOK_AND_UPDATE_OVERALL_RATING =
                "insert into book_statistic(book_id, user_id, `read`, rating, review) values (?, ?, 'YES', ?, ?); " +
                "update book set overall_rating=(select AVG(rating) from book_statistic where book_id=?) where book_id=?";
    }

    private static class ColumnName {
        public static final String ID = "statistic_id";
        public static final String BOOK_ID = "book_id";
        public static final String USER_ID = "user_id";
        public static final String READ = "read";
        public static final String RATING = "rating";
        public static final String REVIEW = "review";
    }

    @Override
    public void rateBookAndUpdateOverallRating(Long id) throws DaoException {

    }

    @Override
    public BookStatistic find(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BookStatistic> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookStatistic create(BookStatistic entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public BookStatistic update(BookStatistic entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws DaoException {

    }
}
