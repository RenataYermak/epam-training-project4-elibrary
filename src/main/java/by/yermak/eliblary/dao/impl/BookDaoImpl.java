package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.BookDao;
import by.yermak.eliblary.dao.mapper.impl.BookMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import java.util.*;

import static by.yermak.eliblary.dao.sql.BookSql.*;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookMapper bookMapper = new BookMapper();

    @Override
    public List<Book> findAllBooks(int page) throws DaoException {
        List<Book> booksOnPage = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PAGE_QUERY_BOOKS)) {
            preparedStatement.setInt(1, ELEMENTS_ON_PAGE * (page - 1));
            preparedStatement.setInt(2, ELEMENTS_ON_PAGE);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = bookMapper.map(resultSet);
                    optionalBook.ifPresent(booksOnPage::add);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all users on defined page: {} ", e);
        }
        return booksOnPage;
    }

    @Override
    public Optional<Book> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
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
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalBook = bookMapper.map(resultSet);
                optionalBook.ifPresent(books::add);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll books: {}", e);
        }
        return books;
    }

    @Override
    public Optional<Book> create(Book entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Book> findBooksByQuery(String searchQuery) throws DaoException {
        LOGGER.log(Level.INFO, "method findBooksByQuery");
        List<Book> books = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(BOOK_SEARCH)) {
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var optionalBook = bookMapper.map(resultSet);
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
    public boolean create(Book book, byte[] picture) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        InputStream pictureStream = null;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            pictureStream = new ByteArrayInputStream(picture);
            constructPreparedStatement(preparedStatement, book);
            preparedStatement.setBlob(7, pictureStream);
            preparedStatement.execute();
            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "BookDao error while create new book. {}", e.getMessage());
            throw new DaoException("BookDao error while create new book", e);
        } finally {
            try {
                if (pictureStream != null) {
                    pictureStream.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "BookDao error while create new product closing resources. {}", e.getMessage());
            }
        }
        return true;
    }

    @Override
    public Optional<Book> update(Book book) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_BOOK_DATA)) {
            constructPreparedStatement(preparedStatement, book);
            preparedStatement.setLong(7, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "ProductDao error while create new product. {}", e.getMessage());
            throw new DaoException("ProductDao error while create new product", e);
        }
        return Optional.of(book);
    }

    @Override
    public boolean updatePicture(Long id, byte[] picture) throws DaoException {
        InputStream photoStream = null;
        int result = 0;
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_PICTURE)) {
            photoStream = new ByteArrayInputStream(picture);
            preparedStatement.setBlob(1, photoStream);
            preparedStatement.setLong(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "ProductDao error while updatePhoto. {}", e.getMessage());
            throw new DaoException("ProductDao error while updatePhoto", e);
        } finally {
            try {
                if (photoStream != null) {
                    photoStream.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "ProductDao error while updatePhoto closing resources. {}", e.getMessage());
            }
        }
        return result > 0;
    }

    @Override
    public void delete(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method delete");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_BOOK)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method delete: ", e);
            throw new DaoException("Exception when delete book: {}", e);
        }
    }

    private void constructPreparedStatement(PreparedStatement preparedStatement, Book book) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setLong(2, book.getAuthor().getId());
        preparedStatement.setString(3, book.getCategory().toString());
        preparedStatement.setInt(4, book.getPublishYear());
        preparedStatement.setString(5, book.getDescription());
        preparedStatement.setInt(6, book.getNumber());
    }
}