package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.*;
import by.yermak.eliblary.entity.user.Status;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.yermak.eliblary.util.locale.MessagesKey.ACCOUNT_BLOCKED;
import static by.yermak.eliblary.util.locale.MessagesKey.INCORRECT_LOGIN_OR_PASSWORD;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService;
    private final BookService bookService;
    LanguageMessage message = LanguageMessage.getInstance();

    public SignInCommand() {
        this.userService = new UserServiceImpl();
        this.bookService = new BookServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        try {
            var login = request.getParameter(RequestParameter.USER_LOGIN);
            var pass = request.getParameter(RequestParameter.USER_PASSWORD);
            var user = userService.find(login, pass);
            if (user != null && user.getStatus().equals(Status.ACTIVATED)) {
                LOGGER.log(Level.INFO, "user logged in successfully");
                session.setAttribute(SessionAttribute.AUTHORIZED_USER, user);
                List<Book> books = bookService.findAll();
                request.setAttribute(RequestAttribute.BOOKS, books);
                session.setAttribute(RequestAttribute.CURRENT_PAGE, PagePath.BOOKS_TABLE);
                return new Router(PagePath.BOOKS_TABLE_URL, Router.RouterType.REDIRECT);
            }
            if (user != null && user.getStatus().equals(Status.DEACTIVATED)) {
                LOGGER.log(Level.INFO, "failed to login, not permission");
                request.setAttribute(
                        RequestAttribute.ERROR_MESSAGE_SIGN_IN, message.getText(currentLocale, ACCOUNT_BLOCKED));
            } else {
                LOGGER.log(Level.INFO, "failed to login, bad credentials");
                request.setAttribute(
                        RequestAttribute.ERROR_MESSAGE_SIGN_IN, message.getText(currentLocale, INCORRECT_LOGIN_OR_PASSWORD));
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "error during user log in: ", e);
            request.setAttribute(
                    RequestAttribute.ERROR_MESSAGE_SIGN_IN, message.getText(currentLocale, INCORRECT_LOGIN_OR_PASSWORD));
        }
        request.setAttribute(RequestAttribute.CURRENT_PAGE, PagePath.SIGN_IN);
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
