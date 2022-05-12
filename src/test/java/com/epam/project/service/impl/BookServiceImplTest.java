package com.epam.project.service.impl;

import com.epam.project.model.book.Book;
import com.epam.project.model.book.Category;
import com.epam.project.service.BookService;
import com.epam.project.service.exception.ServiceException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookServiceImplTest {

    private final BookService bookService = new BookServiceImpl();

    @Test
    void shouldGetBookById() throws ServiceException {
        Book actualBook = bookService.findBook(1L);
        assertNotNull(actualBook);
    }

    @Test
    void shouldGetAllBooks() throws ServiceException {
        int expectedNumberOfBooks = 3;
        List<Book> actualBooks = bookService.findAllBooks();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
    }

    @Test
    void shouldRemoveBook() throws ServiceException {
        int expectedNumberOfBooks = 2;
        bookService.delete(3L);
        List<Book> actualBooks = bookService.findAllBooks();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
        bookService.create(bookCreator(3L));
    }

    @Test
    void shouldCreateBook() throws ServiceException {
        int expectedNumberOfBooks = 4;
        bookService.create(bookCreator(4L));
        List<Book> actualBooks = bookService.findAllBooks();
        assertNotNull(actualBooks);
        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
        bookService.delete(4L);
    }

    Book bookCreator(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle("Title" + id);
        book.setAuthor("Author");
        book.setCategory(Category.SCI_FI);
        book.setDescription("Description");
        return book;
    }
}