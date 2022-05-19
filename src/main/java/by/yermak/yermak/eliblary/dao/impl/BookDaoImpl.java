package by.yermak.yermak.eliblary.dao.impl;

import by.yermak.yermak.eliblary.dao.BookDao;
import by.yermak.yermak.eliblary.model.book.Book;
import by.yermak.yermak.eliblary.model.book.Category;
import by.yermak.yermak.eliblary.dao.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger();

    private static class Query {
        public static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
        public static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE book_id=?";
        public static final String INSERT_BOOK =
                "INSERT INTO books (title, author, category, publish_year, description, number) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?);";
        public static final String UPDATE_BOOK =
                "UPDATE books SET title=?, author=?, category=?, publish_year=?, description=?,  number=? " +
                        "WHERE book_id=?";
        public static final String DELETE_BOOK = "DELETE FROM books WHERE book_id=?";
        public static final String BOOK_SEARCH =
                "SELECT * FROM books WHERE title LIKE CONCAT('%',?,'%') or author LIKE CONCAT('%',?,'%')";
    }

    private static class ColumnName {
        public static final String ID = "book_id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String CATEGORY = "category";
        public static final String PUBLISH_YEAR = "publish_year";
        public static final String DESCRIPTION = "description";
       // public static final String OVERALL_RATING = "overall_rating";
        public static final String NUMBER = "number";
    }

    @Override
    public Book find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        Book book = null;
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_BOOK_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = new Book();
                    constructBook(book, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find book: {}", e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.SELECT_ALL_BOOKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Book book = new Book();
                constructBook(book, resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll books: {}", e);
        }
        return books;
    }

    @Override
    public List<Book> findBooksByQuery(String searchQuery) throws DaoException {
        LOGGER.log(Level.INFO, "method findBooksByQuery");
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.BOOK_SEARCH)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    constructBook(book, resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByQuery: ", e);
            throw new DaoException("Exception when findBooksByQuery: {}", e);
        }
        return books;
    }

    @Override
    public Book create(Book book) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.INSERT_BOOK)) {
            constructPrepareStatement(preparedStatement, book);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create book: {}", e);
        }
        return book;
    }

    @Override
    public Book update(Book entity) throws DaoException {
        LOGGER.log(Level.INFO, "method update");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.UPDATE_BOOK)) {
            constructPrepareStatement(preparedStatement, entity);
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new DaoException("Exception when update book: {}", e);
        }
        return entity;
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (PreparedStatement preparedStatement = getPrepareStatement(Query.DELETE_BOOK)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete book: {}", e);
        }
    }

    private void constructBook(Book book, ResultSet resultSet) throws SQLException {
        book.setId(resultSet.getLong(ColumnName.ID));
        book.setTitle(resultSet.getString(ColumnName.TITLE));
        book.setAuthor(resultSet.getString(ColumnName.AUTHOR));
        book.setPublishYear(resultSet.getInt(ColumnName.PUBLISH_YEAR));
        book.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
        book.setNumber(returnResultSetIntValue(resultSet, ColumnName.NUMBER));
        book.setCategory(Category.valueOf(resultSet.getString(ColumnName.CATEGORY).toUpperCase()));
    }

    private void constructPrepareStatement(PreparedStatement preparedStatement, Book book) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getCategory().toString());
        preparedStatement.setInt(4, book.getPublishYear());
        preparedStatement.setString(5, book.getDescription());
        setIntOrNull(preparedStatement, 6, book.getNumber());
    }
}