package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.ResponseContext;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindAllUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public FindAllUsersCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                List<User> users =
                        (isAdmin(session)) ?
                                userService.findAll() : userService.findActivatedUsers();
                request.setAttribute(RequestAttribute.USERS, users);
                return new ResponseContext(PagePath.USERS, ResponseContext.ResponseContextType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find all users: ", e);
            }
        }
        return new ResponseContext(PagePath.USERS, ResponseContext.ResponseContextType.FORWARD);
    }
}
