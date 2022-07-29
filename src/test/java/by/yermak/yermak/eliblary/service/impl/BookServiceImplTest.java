package by.yermak.yermak.eliblary.service.impl;

import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookServiceImplTest {

    private final BookService bookService = new BookServiceImpl();

    @Test
    void shouldGetBookById() throws ServiceException {
        Book actualBook = bookService.find(1L);
        assertNotNull(actualBook);
    }

    @Test
    void shouldGetAllBooks() throws ServiceException {
        int expectedNumberOfBooks = 21;
        List<Book> actualBooks = bookService.findAll();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
    }

    @Test
    void shouldRemoveBook() throws ServiceException {
        int expectedNumberOfBooks = 20;
        bookService.delete(3L);
        List<Book> actualBooks = bookService.findAll();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
    }

    @Test
    void shouldCreateBook() throws ServiceException {
        int expectedNumberOfBooks = 30;
        List<Book> actualBooks = bookService.findAll();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
        bookService.delete(400L);
    }

    Book bookCreator(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle("Title");
        Author author = new Author("Alan");
        book.setAuthor(author);
        book.setPublishYear(1999);
        book.setCategory(Category.FICTION);
        book.setDescription("Description");
        book.setNumber(3);
        return book;
    }
}