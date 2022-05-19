package by.yermak.yermak.eliblary.controller.command.user;

import by.yermak.yermak.eliblary.controller.PagePath;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.yermak.eliblary.controller.RequestAttribute;
import by.yermak.yermak.eliblary.controller.RequestParam;
import by.yermak.yermak.eliblary.controller.ResponseContext;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.model.user.User;
import by.yermak.yermak.eliblary.service.UserService;
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
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long userId = Long.parseLong(request.getParameter(RequestParam.USER_ID));
                User user = userService.findUser(userId);
                request.setAttribute(RequestAttribute.USER, user);
                return new ResponseContext(PagePath.USER, ResponseContext.ResponseContextType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during find user: ", e);
            }
        }
        return new ResponseContext(PagePath.SIGN_IN, ResponseContext.ResponseContextType.FORWARD);
    }
}
