package by.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.AuthorDao;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.mapper.impl.AuthorMapper;
import by.yermak.eliblary.dao.pool.ConnectionPool;
import by.yermak.eliblary.entity.book.Author;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.yermak.eliblary.dao.sql.AuthorSql.*;

public class AuthorDaoImpl implements AuthorDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AuthorMapper authorMapper = new AuthorMapper();

    @Override
    public Optional<Author> find(Long id) throws DaoException {
        LOGGER.log(Level.INFO, "method find");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return authorMapper.map(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method find: ", e);
            throw new DaoException("Exception when find author: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() throws DaoException {
        LOGGER.log(Level.INFO, "method findAll");
        List<Author> authors = new ArrayList<>();
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS);
             var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                var optionalAuthor = authorMapper.map(resultSet);
                optionalAuthor.ifPresent(authors::add);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method findAll: ", e);
            throw new DaoException("Exception when findAll authors: {}", e);
        }
        return authors;
    }


    @Override
    public Optional<Author> create(Author author) throws DaoException {
        LOGGER.log(Level.INFO, "method create");
        try (var connection = ConnectionPool.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.executeUpdate();
            try (var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    author.setId(resultSet.getLong(1));
                    return Optional.of(author);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "exception in method create: ", e);
            throw new DaoException("Exception when create author: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> update(Author entity) throws DaoException {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) throws DaoException {

    }
}
