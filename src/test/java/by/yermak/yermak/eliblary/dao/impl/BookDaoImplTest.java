package by.yermak.yermak.eliblary.dao.impl;

import by.yermak.yermak.eliblary.model.book.Book;
import by.yermak.yermak.eliblary.dao.exception.DaoException;
import by.yermak.yermak.eliblary.dao.BookDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

class BookDaoImplTest {

    private final BookDao bookDao = new BookDaoImpl();

    @Test
    @DisplayName("Should find book by id")
    void shouldFindBookById() throws DaoException {
        Book actualBook = constructTestBook();
        bookDao.create(actualBook);
        Book expectedBook = bookDao.find(actualBook.getId());
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.getId());
    }

    @Test
    @DisplayName("Should find all books")
    void shouldFindAllBooks() throws DaoException {
        int count = 5;
        for (int i = 0; i < count; i++) {
            bookDao.create(constructTestBook());
        }
        List<Book> expectedBook = bookDao.findAll();
        assertThat(expectedBook.size(), is(count));
        for (int i = 0; i < count; i++) {
            bookDao.delete(expectedBook.get(i).getId());
        }
    }

    @Test
    @DisplayName("Should find books by search query")
    void shouldFindBooksBySearchQuery() throws DaoException {
        int count = 2;
        for (int i = 0; i < count; i++) {
            bookDao.create(constructTestBook());
        }
        List<Book> expectedBook = bookDao.findBooksByQuery("ta");
        assertThat(expectedBook.size(), is(count));
        for (int i = 0; i < count; i++) {
            bookDao.delete(expectedBook.get(i).getId());
        }
    }

    @Test
    @DisplayName("Should create book")
    void shouldCreateBook() throws DaoException {
        Book actualBook = constructTestBook();
        Book expectedBook = bookDao.create(actualBook);
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.getId());
    }

    @Test
    @DisplayName("Should update book")
    void shouldUpdateBook() throws DaoException {
        Book actualBook = constructTestBook();
        bookDao.create(actualBook);
        actualBook.setDescription("Update Description");
        Book expectedBook = bookDao.update(actualBook);
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.getId());
    }

    @Test
    @DisplayName("Should delete book")
    void shouldDeleteBook() throws DaoException {
        Book actualBook = constructTestBook();
        bookDao.create(actualBook);
        bookDao.delete(actualBook.getId());
        Book expected = bookDao.find(actualBook.getId());
        assertThat(expected, nullValue());
    }
}
