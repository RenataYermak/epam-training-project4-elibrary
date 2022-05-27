package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
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

    public EditBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAuthorized(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParam.BOOK_ID));
                var title = request.getParameter(RequestParam.BOOK_TITLE);
                var author = request.getParameter(RequestParam.BOOK_AUTHOR);
                var category = request.getParameter(RequestParam.BOOK_CATEGORY);
                var publishYear = parseIntParameter(request.getParameter(RequestParam.BOOK_PUBLISH_YEAR));
                var number = parseIntParameter(request.getParameter(RequestParam.BOOK_NUMBER));
                var book = bookService.findBook(id);
                if (book != null) {
                    if (category != null) {
                        book.setCategory(Category.valueOf(category.toUpperCase()));
                    }
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setPublishYear(publishYear);
                    book.setNumber(number);
                    bookService.update(book);
                    request.setAttribute(RequestAttribute.BOOK, book);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, message.getText(currentLocale,SUCCESS_BOOK_UPDATE));
                    return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating book: ", e);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale,BOOK_NOT_UPDATE));
        return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
    }
}
