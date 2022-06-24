package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.yermak.eliblary.util.locale.MessagesKey.BOOK_NOT_ADD;
import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_BOOK_ADD;

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();
    private final BookService bookService;

    public AddBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                var title = request.getParameter(RequestParameter.BOOK_TITLE);
                var author = request.getParameter(RequestParameter.BOOK_AUTHOR);
                var category = request.getParameter(RequestParameter.BOOK_CATEGORY);
                var publishYear = parseIntParameter(request.getParameter(RequestParameter.BOOK_PUBLISH_YEAR));
                var number = parseIntParameter(request.getParameter(RequestParameter.BOOK_NUMBER));
                var book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setCategory(Category.valueOf(category.toUpperCase()));
                book.setPublishYear(publishYear);
                book.setNumber(number);
                book = bookService.create(book);
                if (book.getId() != null) {
                    LOGGER.log(Level.INFO, "book was created successfully");
                    List<Book> allBooks = bookService.findAllBooks();
                    request.setAttribute(RequestAttribute.BOOKS, allBooks);
                }
                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_BOOK_CREATE, message.getText(currentLocale, SUCCESS_BOOK_ADD));
                return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during book creation: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_ADD));
        return new Router(PagePath.ADD_BOOK, Router.RouterType.FORWARD);
    }
}
