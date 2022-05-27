package by.yermak.eliblary.dao;

import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.entity.statistic.BookStatistic;

public interface BookStatisticDao extends EntityDao<BookStatistic> {
    void rateBookAndUpdateOverallRating(Long id) throws DaoException;
}
