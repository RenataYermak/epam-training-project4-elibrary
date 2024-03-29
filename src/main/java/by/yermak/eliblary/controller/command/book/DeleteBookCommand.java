package by.yermak.eliblary.controller.command.book;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.yermak.eliblary.util.locale.MessagesKey.BOOK_NOT_DELETE;
import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_BOOK_DELETE;

public class DeleteBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService;
    private final  LanguageMessage message = LanguageMessage.getInstance();

    public DeleteBookCommand() {
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParameter.BOOK_ID));
                bookService.delete(id);
                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_BOOK_UPDATE, message.getText(currentLocale, SUCCESS_BOOK_DELETE));
                return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during deleting user: ", e);
                return new Router(PagePath.ERROR_PAGE_500,Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, BOOK_NOT_DELETE));
        return new Router(PagePath.EDIT_BOOK, Router.RouterType.FORWARD);
    }
}
