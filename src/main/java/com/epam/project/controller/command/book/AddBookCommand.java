package com.epam.project.controller.command.book;

import com.epam.project.controller.ResponseContext;
import com.epam.project.controller.command.Command;
import com.epam.project.controller.RequestAttribute;
import com.epam.project.controller.RequestParam;
import com.epam.project.controller.PagePath;
import com.epam.project.model.book.Book;
import com.epam.project.model.book.Category;
import com.epam.project.service.BookService;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public AddBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAdmin(session)) {
            try {
                String title = request.getParameter(RequestParam.BOOK_TITLE);
                String author = request.getParameter(RequestParam.BOOK_AUTHOR);
                String category = request.getParameter(RequestParam.BOOK_CATEGORY);
                String publishYear = request.getParameter(RequestParam.BOOK_PUBLISH_YEAR);
                String description = request.getParameter(RequestParam.BOOK_DESCRIPTION);
                String overallRating = request.getParameter(RequestParam.BOOK_OVERALL_RATING);
                String number = request.getParameter(RequestParam.BOOK_NUMBER);
                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setCategory(Category.valueOf(category.toUpperCase()));
                book.setPublishYear(Integer.valueOf(publishYear));
                book.setDescription(description);
                book.setOverallRating(Double.valueOf(overallRating));
                book.setNumber(Integer.valueOf(number));
                book = bookService.create(book);
                if (book.getId() != null) {
                    LOGGER.log(Level.INFO, "book was created successfully");
                    List<Book> allBooks = bookService.findAllBooks();
                    request.setAttribute(RequestAttribute.BOOKS, allBooks);
                }
                request.setAttribute(RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book was added  successfully");
                return new ResponseContext(PagePath.ADD_BOOK, ResponseContext.ResponseContextType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
            }
        }
        return new ResponseContext(PagePath.BOOK, ResponseContext.ResponseContextType.FORWARD);
    }
}
