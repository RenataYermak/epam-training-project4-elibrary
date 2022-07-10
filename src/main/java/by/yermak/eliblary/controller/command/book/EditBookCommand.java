package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.entity.book.Author;
import by.yermak.eliblary.service.AuthorService;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.AuthorServiceImpl;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Category;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import static by.yermak.eliblary.util.locale.MessagesKey.*;

public class EditBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();

    private final BookService bookService;
    private final AuthorService authorService;

    public EditBookCommand() {
        this.bookService = new BookServiceImpl();
        this.authorService = new AuthorServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                var id = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                var title = request.getParameter(RequestParameter.BOOK_TITLE);
                var author = parseLongParameter(request.getParameter(RequestParameter.BOOK_AUTHOR));
                var category = request.getParameter(RequestParameter.BOOK_CATEGORY);
                var publishYear = parseIntParameter(request.getParameter(RequestParameter.BOOK_PUBLISH_YEAR));
                var number = parseIntParameter(request.getParameter(RequestParameter.BOOK_NUMBER));
                var book = bookService.find(id);
                if (book != null) {
                    if (category != null) {
                        book.setCategory(Category.valueOf(category.toUpperCase()));
                    }
                    book.setTitle(title);
                    book.setAuthor(new Author(author));
                    book.setPublishYear(publishYear);
                    book.setNumber(number);
                    bookService.update(book);
                    authorService.find(author);
                    request.setAttribute(RequestAttribute.BOOK, book);
                    request.setAttribute(RequestAttribute.AUTHOR, author);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, message.getText(currentLocale, SUCCESS_BOOK_UPDATE));
                    return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
                //  return new Router(PagePath.ERROR_PAGE_500,Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_UPDATE));
        return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
    }
}
