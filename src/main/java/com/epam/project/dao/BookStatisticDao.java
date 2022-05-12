package com.epam.project.dao;

import com.epam.project.dao.exception.DaoException;
import com.epam.project.model.statistic.BookStatistic;

public interface BookStatisticDao extends EntityDao<BookStatistic, Long> {
    void rateBookAndUpdateOverallRating(Long id) throws DaoException;
}
