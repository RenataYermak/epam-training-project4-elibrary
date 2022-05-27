package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnNameHelper.*;

public class BookMapper implements EntityMapper<Book> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<Book> map(ResultSet resultSet) {
        try {
            var book = new Book();
            book.setId(resultSet.getLong(BOOK_ID));
            book.setTitle(resultSet.getString(TITLE));
            book.setAuthor(resultSet.getString(AUTHOR));
            book.setPublishYear(resultSet.getInt(PUBLISH_YEAR));
            book.setDescription(resultSet.getString(DESCRIPTION));
            book.setNumber(resultSet.getInt(NUMBER));
            book.setCategory(Category.valueOf(resultSet.getString(CATEGORY).toUpperCase()));
            return Optional.of(book);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set");
            return Optional.empty();
        }
    }
}
