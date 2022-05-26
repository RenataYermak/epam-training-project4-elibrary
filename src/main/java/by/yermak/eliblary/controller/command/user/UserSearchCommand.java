package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserSearchCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public UserSearchCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String searchQuery = request.getParameter(RequestParam.SEARCH_QUERY);
        if (isAuthorized(session)) {
            try {
                List<User> users = userService.findUsersByQuery(searchQuery);
                if (!users.isEmpty()) {
                    request.setAttribute(RequestAttribute.USERS, users);
                } else {
                    request.setAttribute(RequestAttribute.SEARCH_QUERY, searchQuery);
                    request.setAttribute(
                            RequestAttribute.WARNING_MESSAGE_USER_SEARCH, "There were no users found for ");
                }
                return new Router(PagePath.USERS, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during search users: ", e);
            }
        }
        return new Router(PagePath.USERS, Router.RouterType.FORWARD);
    }
}