package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.mapper.impl.BookMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookMapper bookMapper = new BookMapper();

    private static class Query {
        public static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
        public static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE book_id=?";
        public static final String INSERT_BOOK =
                "INSERT INTO books (title, author, category, publish_year, description, number) " +
                        "VALUES(?, ?, ?, ?, ?, ?)";
        public static final String UPDATE_BOOK =
                "UPDATE books SET title=?, author=?, category=?, publish_year=?, description=?,  number=? " +
                        "WHERE book_id=?";
        public static final String DELETE_BOOK = "DELETE FROM books WHERE book_id=?";
        public static final String BOOK_SEARCH =
                "SELECT * FROM books WHERE title LIKE CONCAT('%',?,'%') or author LIKE CONCAT('%',?,'%')";
    }

    @Override
    public Optional<Book> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        try (Connection connection  = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_BOOK_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return bookMapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find book: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<Book> books = new ArrayList<>();
        try (Connection connection  = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_ALL_BOOKS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Optional<Book> optionalBook = bookMapper.map(resultSet);
                optionalBook.ifPresent(books::add);
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
        try (Connection connection  = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Query.BOOK_SEARCH)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<Book> optionalBook = bookMapper.map(resultSet);
                    optionalBook.ifPresent(books::add);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findBooksByQuery: ", e);
            throw new DaoException("Exception when findBooksByQuery: {}", e);
        }
        return books;
    }

    @Override
    public Optional<Book> create(Book book) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            constructPreparedStatement(preparedStatement, book);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                    Optional<Book> optionalBook = find(book.getId());
                    if(optionalBook.isPresent()) {
                        return Optional.of(book);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create book: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book book) throws DaoException {
        LOGGER.log(Level.INFO, "method update");
        try (Connection connection  = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Query.UPDATE_BOOK)) {
            constructPreparedStatement(preparedStatement, book);
            preparedStatement.setLong(7, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method update: ", e);
            throw new DaoException("Exception when update book: {}", e);
        }
        return Optional.of(book);
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (Connection connection  = ConnectionPool.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE_BOOK)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete book: {}", e);
        }
    }

    private void constructPreparedStatement(PreparedStatement preparedStatement, Book book) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getCategory().toString());
        preparedStatement.setInt(4, book.getPublishYear());
        preparedStatement.setString(5, book.getDescription());
        preparedStatement.setInt(6, book.getNumber());
    }
}