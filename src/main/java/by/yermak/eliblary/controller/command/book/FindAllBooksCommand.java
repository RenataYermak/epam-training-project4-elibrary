package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class FindAllBooksCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService;

    public FindAllBooksCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String page = request.getParameter(RequestAttribute.PAGE);
        int currentPage = page != null ? parseIntParameter(page) : RequestAttribute.DEFAULT_PAGE_NUMBER;
        if (isAuthorized(session)) {
            try {
                List<Book> bookList = new ArrayList<>(bookService.findAll());
                List<Book> books = new ArrayList<>(bookService.findAll(currentPage));
                request.setAttribute(RequestAttribute.BOOKS, books);
                int numberOfPages = (int) Math.ceil(bookList.size() * 1.0 / RequestAttribute.RECORDS_PER_PAGE);
                request.setAttribute(RequestAttribute.BOOKS, books);
                request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
                request.setAttribute(RequestAttribute.PAGE, currentPage);
                return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find all books: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
