package com.epam.project.controller.command.book;

import com.epam.project.controller.*;
import com.epam.project.controller.command.Command;
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
import java.sql.Date;

public class EditBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public EditBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long id = Long.parseLong(request.getParameter(RequestParam.BOOK_ID));
                String title = request.getParameter(RequestParam.BOOK_TITLE);
                String author = request.getParameter(RequestParam.BOOK_AUTHOR);
                String category = request.getParameter(RequestParam.BOOK_CATEGORY);
              //  Integer publishYear = Integer.parseInt(request.getParameter(RequestParam.BOOK_PUBLISH_YEAR));
                String publishYear = request.getParameter(RequestParam.BOOK_PUBLISH_YEAR);
                String description = request.getParameter(RequestParam.BOOK_DESCRIPTION);
                //Double overallRating = Double.parseDouble(request.getParameter(RequestParam.BOOK_OVERALL_RATING));
                String overallRating = request.getParameter(RequestParam.BOOK_OVERALL_RATING);
                //Integer number = Integer.parseInt(request.getParameter(RequestParam.BOOK_NUMBER));
                String number = request.getParameter(RequestParam.BOOK_NUMBER);
                Book book = bookService.findBook(id);
                if (book != null) {
                    book.setTitle(title);
                    book.setAuthor(author);
                    if (category != null) {
                        book.setCategory(Category.valueOf(category.toUpperCase()));
                    }
                    if(overallRating != null){
                    //    book.setOverallRating(overallRating);
                        book.setOverallRating(Double.valueOf(overallRating));
                    }
                  //  book.setPublishYear(publishYear);
                    book.setPublishYear(Integer.valueOf(publishYear));
                    book.setDescription(description);
                    book.setNumber(Integer.valueOf(number));
                  //  book.setNumber(number);
                    bookService.update(book);
                    if (getAuthUser(session).getId().equals(book.getId())) {
                        session.removeAttribute(SessionAttribute.AUTHORIZED_USER);
                        session.setAttribute(SessionAttribute.AUTHORIZED_USER, book);
                    }
                    request.setAttribute(RequestAttribute.BOOK, book);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, "Book updated successfully");
                    return new ResponseContext(PagePath.BOOK, ResponseContext.ResponseContextType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
            }
        }
        return new ResponseContext(PagePath.BOOK, ResponseContext.ResponseContextType.FORWARD);
    }
}