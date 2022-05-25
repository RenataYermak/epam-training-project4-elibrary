package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.model.order.Issue;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
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
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParam.USER_ID));
                Long bookId = parseLongParameter(request.getParameter(RequestParam.BOOK_ID));
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
        return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
    }
}
