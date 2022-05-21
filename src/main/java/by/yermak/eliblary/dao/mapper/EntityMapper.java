package by.yermak.eliblary.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The EntityMapper interface maps rows of a ResultSet.
 *
 * @param <T> the entity class
 */
public interface EntityMapper<T> {
    /**
     * Maps data in the ResultSet.
     *
     * @param resultSet the ResultSet to map
     * @return the optional object of a result object for the current row
     * @throws SQLException if an SQLException is encountered getting column values
     */
    Optional<T> map(ResultSet resultSet) throws SQLException;
}
