package by.yermak.eliblary.dao.mapper;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * The EntityMapper interface maps rows of a ResultSet.
 *
 * @param <E> the entity class
 */
public interface EntityMapper<E> {
    /**
     * Maps data in the ResultSet.
     *
     * @param resultSet the ResultSet to map
     * @return the optional object of a result object for the current row
     */
    Optional<E> map(ResultSet resultSet);
}
