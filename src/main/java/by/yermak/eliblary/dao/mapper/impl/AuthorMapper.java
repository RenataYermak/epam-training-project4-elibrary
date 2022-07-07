package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.entity.book.Author;;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnName.*;

public class AuthorMapper implements EntityMapper<Author> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<Author> map(ResultSet resultSet) {
        try {
            var author = new Author();
            author.setId(resultSet.getLong(AUTHOR_ID));
            author.setName(resultSet.getString(AUTHOR_NAME));
            return Optional.of(author);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set ", e);
            return Optional.empty();
        }
    }
}
