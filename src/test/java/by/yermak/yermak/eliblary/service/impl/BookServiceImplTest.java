//package by.yermak.yermak.eliblary.service.impl;
//
//import by.yermak.eliblary.model.book.Book;
//import by.yermak.eliblary.model.book.Category;
//import by.yermak.eliblary.service.BookService;
//import by.yermak.eliblary.service.exception.ServiceException;
//import by.yermak.eliblary.service.impl.BookServiceImpl;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class BookServiceImplTest {
//
//    private final BookService bookService = new BookServiceImpl();
//
//    @Test
//    void shouldGetBookById() throws ServiceException {
//        Book actualBook = bookService.findBook(1L);
//        assertNotNull(actualBook);
//    }
//
//    @Test
//    void shouldGetAllBooks() throws ServiceException {
//        int expectedNumberOfBooks = 3;
//        List<Book> actualBooks = bookService.findAllBooks();
//        assertNotNull(actualBooks);
//        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
//    }
//
//    @Test
//    void shouldRemoveBook() throws ServiceException {
//        int expectedNumberOfBooks = 2;
//        bookService.delete(3L);
//        List<Book> actualBooks = bookService.findAllBooks();
//        assertNotNull(actualBooks);
//        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
//        bookService.create(bookCreator(3L));
//    }
//
//    @Test
//    void shouldCreateBook() throws ServiceException {
//        int expectedNumberOfBooks = 4;
//        bookService.create(bookCreator(4L));
//        List<Book> actualBooks = bookService.findAllBooks();
//        assertNotNull(actualBooks);
//        assertThat(actualBooks.size(), is(expectedNumberOfBooks));
//        bookService.delete(4L);
//    }
//
//    Optional<Book> bookCreator(Long id) {
//        Optional<Book> book = new Book();
//        book.setId(id);
//        book.setTitle("Title" + id);
//        book.setAuthor("Author");
//        book.setCategory(Category.SCI_FI);
//        book.setDescription("Description");
//        return book;
//    }
//}