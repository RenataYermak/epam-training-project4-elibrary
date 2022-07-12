package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.controller.RequestParameter;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.yermak.eliblary.util.locale.MessagesKey.*;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();

    private final UserService userService;

    public DeleteUserCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) && isAdmin(session)) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParameter.USER_ID));
                userService.delete(id);
                Router currentRouter = new Router();
                currentRouter.setPagePath(PagePath.DEACTIVATED_USERS_URL);
                currentRouter.setRouteType(Router.RouterType.FORWARD);
                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_USER_DELETE, message.getText(currentLocale, SUCCESS_USER_DELETED));
                return currentRouter;
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during deleting user: ", e);
                return new Router(PagePath.ERROR_PAGE_500, Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, USER_NOT_DELETED));
        return new Router(PagePath.DEACTIVATED_USERS_URL, Router.RouterType.FORWARD);
    }
}
