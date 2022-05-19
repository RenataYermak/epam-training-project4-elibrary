package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.*;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.book.Book;
import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;
    private final BookService bookService;

    public SignInCommand() {
        this.userService = new UserServiceImpl();
        this.bookService = new BookServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        try {
            String login = request.getParameter(RequestParam.USER_LOGIN);
            String pass = request.getParameter(RequestParam.USER_PASSWORD);
            User user = userService.findUser(login, pass);
            if (user != null) {
                LOGGER.log(Level.INFO, "user logged in successfully");
                session.setAttribute(SessionAttribute.AUTHORIZED_USER, user);
                List<Book> books = bookService.findAllBooks();
                request.setAttribute(RequestAttribute.BOOKS, books);
                session.setAttribute(RequestAttribute.CURRENT_PAGE, PagePath.BOOKS);
                return new ResponseContext(PagePath.BOOKS, ResponseContext.ResponseContextType.FORWARD);
            }
            LOGGER.log(Level.INFO, "failed to login, bad credentials");
            request.setAttribute(RequestAttribute.ERROR_MESSAGE_SIGN_IN, "Incorrect login or password");
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "error during user log in: ", e);
        }
        request.setAttribute(RequestAttribute.CURRENT_PAGE, PagePath.SIGN_IN);
        return new ResponseContext(PagePath.SIGN_IN, ResponseContext.ResponseContextType.FORWARD);
    }
}
