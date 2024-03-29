package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.yermak.eliblary.util.locale.MessagesKey.SEARCH_BOOK_FAIL;

public class BookSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService;
    private final LanguageMessage message = LanguageMessage.getInstance();

    public BookSearchCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        var searchQuery = request.getParameter(RequestParameter.SEARCH_QUERY);
        if (isAuthorized(session)) {
            try {
                List<Book> books = bookService.findBooksByQuery(searchQuery);
                if (!books.isEmpty()) {
                    request.setAttribute(RequestAttribute.BOOKS, books);
                } else {
                    request.setAttribute(RequestAttribute.SEARCH_QUERY, searchQuery);
                    request.setAttribute(
                            RequestAttribute.WARNING_MESSAGE_BOOK_SEARCH, message.getText(currentLocale, SEARCH_BOOK_FAIL));
                }
                return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during search books: ", e);
            }
        }
        return new Router(PagePath.BOOKS_TABLE, Router.RouterType.FORWARD);
    }
}
