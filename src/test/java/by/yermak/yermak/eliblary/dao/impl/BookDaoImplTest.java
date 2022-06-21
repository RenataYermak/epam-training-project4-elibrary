package by.yermak.yermak.eliblary.dao.impl;

import by.yermak.eliblary.dao.impl.BookDaoImpl;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.dao.exception.DaoException;
import by.yermak.eliblary.dao.BookDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static by.yermak.yermak.eliblary.dao.impl.util.EntityConstructor.constructTestBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

class BookDaoImplTest {

    private final BookDao bookDao = new BookDaoImpl();

    @Test
    @DisplayName("Should find book by id")
    void shouldFindBookById() throws DaoException {
        Book book = constructTestBook();
        Optional<Book> actualBook = bookDao.create(book);
        Optional<Book> expectedBook = bookDao.find(actualBook.get().getId());
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.get().getId());
    }

    @Test
    @DisplayName("Should find all books")
    void shouldFindAllBooks() throws DaoException {
        int count = 2;
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

    //
    @Test
    @DisplayName("Should create book")
    void shouldCreateBook() throws DaoException {
        Book book = constructTestBook();
        Optional<Book> actualBook = bookDao.create(book);
        Optional<Book> expectedBook = bookDao.create(book);
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.get().getId());
    }

    @Test
    @DisplayName("Should update book")
    void shouldUpdateBook() throws DaoException {
        Book book = constructTestBook();
        Optional<Book> actualBook = bookDao.create(book);
        actualBook.get().setDescription("Update Description");
        Optional<Book> expectedBook = bookDao.update(book);
        assertThat(actualBook, is(expectedBook));
        bookDao.delete(expectedBook.get().getId());
    }

    @Test
    @DisplayName("Should delete book")
    void shouldDeleteBook() throws DaoException {
        Book book = constructTestBook();
        Optional<Book> actualBook = bookDao.create(book);
        bookDao.delete(actualBook.get().getId());
        Optional<Book> expected = bookDao.find(actualBook.get().getId());
        assertThat(expected, is(Optional.empty()));
    }
}
