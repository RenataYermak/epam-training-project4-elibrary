package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.BookStatisticDao;
import by.yermak.eliblary.model.statistic.BookStatistic;
import by.yermak.eliblary.dao.exception.DaoException;

import java.util.List;

public class BookStatisticDaoImpl implements BookStatisticDao {

    private static class Query {
        public static final String RATE_BOOK_AND_UPDATE_OVERALL_RATING =
                "INSERT INTO book_statistics(book_id, user_id, `read`, rating, review) VALUES (?, ?, 'YES', ?, ?); " +
                "UPDATE SET overall_rating=(select AVG(rating) FROM book_statistic WHERE book_id=?) WHERE book_id=?";
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
