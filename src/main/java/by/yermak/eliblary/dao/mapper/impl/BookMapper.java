package by.yermak.eliblary.dao.mapper.impl;

import by.yermak.eliblary.dao.mapper.EntityMapper;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.util.converter.ImageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.yermak.eliblary.dao.mapper.ColumnName.*;

public class BookMapper implements EntityMapper<Book> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<Book> map(ResultSet resultSet) {
        try {
            var book = new Book();
            book.setId(resultSet.getLong(BOOK_ID));
            book.setTitle(resultSet.getString(TITLE));
            var authorMapper =new AuthorMapper();
            Optional<Author> author = authorMapper.map(resultSet);
            author.ifPresent(book::setAuthor);
            book.setPublishYear(resultSet.getInt(PUBLISH_YEAR));
            book.setDescription(resultSet.getString(DESCRIPTION));
            book.setNumber(resultSet.getInt(NUMBER));
            book.setCategory(Category.valueOf(resultSet.getString(CATEGORY).toUpperCase()));
            Blob picture = resultSet.getBlob(PICTURE);
            if (picture != null) {
                byte[] imageContent = picture.getBytes(1, (int) picture.length());
                String encodeBase64 = ImageConverter.imageToString(imageContent);
                book.setPicture(encodeBase64);
            }
            return Optional.of(book);
        } catch (SQLException e) {
            LOGGER.error("failed to fetch data from the result set ", e);
            return Optional.empty();
        }
    }
}
