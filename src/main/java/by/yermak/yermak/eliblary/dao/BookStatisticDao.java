package by.yermak.yermak.eliblary.dao;

import by.yermak.yermak.eliblary.dao.exception.DaoException;
import by.yermak.yermak.eliblary.model.statistic.BookStatistic;

public interface BookStatisticDao extends EntityDao<BookStatistic, Long> {
    void rateBookAndUpdateOverallRating(Long id) throws DaoException;
}
