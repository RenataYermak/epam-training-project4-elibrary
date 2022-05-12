package com.epam.project.controller.command.book;

import com.epam.project.controller.PagePath;
import com.epam.project.controller.RequestAttribute;
import com.epam.project.controller.RequestParam;
import com.epam.project.controller.ResponseContext;
import com.epam.project.controller.command.Command;
import com.epam.project.model.book.Book;
import com.epam.project.model.order.Issue;
import com.epam.project.model.order.Order;
import com.epam.project.model.order.Status;
import com.epam.project.service.BookService;
import com.epam.project.service.exception.ServiceException;
import com.epam.project.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrderBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final BookService bookService;

    public OrderBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = Long.parseLong(request.getParameter(RequestParam.USER_ID));
                Long bookId = Long.parseLong(request.getParameter(RequestParam.BOOK_ID));
                String bookTitle = request.getParameter(RequestParam.BOOK_TITLE);
                String issue = request.getParameter(RequestParam.ISSUE);
                Long orderId = bookService.orderBook(bookId, userId, Issue.valueOf(issue.toUpperCase()));
                List<Book> books = bookService.findAllBooks();
                request.setAttribute(RequestAttribute.ORDER_ID, orderId);
                request.setAttribute(RequestAttribute.BOOKS, books);
                request.setAttribute(RequestAttribute.BOOK_TITLE, bookTitle);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during reserve book: ", e);
            }
        }
        return new ResponseContext(PagePath.BOOKS, ResponseContext.ResponseContextType.FORWARD);
    }
}
