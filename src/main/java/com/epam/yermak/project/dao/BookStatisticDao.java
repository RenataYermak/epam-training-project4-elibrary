package com.epam.yermak.project.dao;

import com.epam.yermak.project.dao.exception.DaoException;
import com.epam.yermak.project.model.statistic.BookStatistic;

public interface BookStatisticDao extends EntityDao<BookStatistic, Long> {
    void rateBookAndUpdateOverallRating(Long id) throws DaoException;
}
