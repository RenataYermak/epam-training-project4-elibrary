package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.entity.book.Book;
import by.yermak.eliblary.service.BookService;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.BookServiceImpl;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class FindAllUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public FindAllUsersCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String page = request.getParameter(RequestAttribute.PAGE);
        int currentPage = page != null ? parseIntParameter(page) : RequestAttribute.DEFAULT_PAGE_NUMBER;
        if (isAdmin(session)) {
            try {
                List<User> userList = new ArrayList<>(userService.findAll());
                List<User> users = new ArrayList<>(userService.findAll(currentPage));
                int numberOfPages = (int) Math.ceil(userList.size() * 1.0 / RequestAttribute.RECORDS_PER_PAGE);
                request.setAttribute(RequestAttribute.USERS, users);
                request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, numberOfPages);
                //   request.setAttribute(RequestAttribute.ITEMS_PER_PAGE, countUsers);
                request.setAttribute(RequestAttribute.PAGE, currentPage);
                return new Router(PagePath.USERS, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find all users: ", e);
                return new Router(PagePath.ERROR_PAGE_500,Router.RouterType.FORWARD);
            }
        }
        return new Router(PagePath.USERS, Router.RouterType.FORWARD);
    }
}
