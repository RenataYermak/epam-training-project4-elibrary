package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.user.User;
import by.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FindUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public FindUserCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = parseLongParameter(request.getParameter(RequestParam.USER_ID));
                var user = userService.findUser(userId);
                request.setAttribute(RequestAttribute.USER, user);
                return new Router(PagePath.USER_PROFILE, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find user: ", e);
            }
        }
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
