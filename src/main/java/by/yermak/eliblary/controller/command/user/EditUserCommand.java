package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.*;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.entity.user.Role;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.util.locale.LanguageMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.yermak.eliblary.util.locale.MessagesKey.SUCCESS_USER_UPDATE;
import static by.yermak.eliblary.util.locale.MessagesKey.USER_NOT_UPDATE;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    LanguageMessage message = LanguageMessage.getInstance();
    private final UserService userService;

    public EditUserCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        var currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAuthorized(session) ) {
            try {
                Long id = parseLongParameter(request.getParameter(RequestParameter.USER_ID));
                var login = request.getParameter(RequestParameter.USER_LOGIN);
                var pass = request.getParameter(RequestParameter.USER_PASSWORD);
                var firstName = request.getParameter(RequestParameter.USER_FIRSTNAME);
                var secondName = request.getParameter(RequestParameter.USER_SECONDNAME);
                var email = request.getParameter(RequestParameter.USER_EMAIL);
                var role = request.getParameter(RequestParameter.USER_ROLE);
                var user = userService.findUser(id);
                if (user != null) {
                    if (login != null) {
                        user.setLogin(login);
                    }
                    if (role != null) {
                        user.setRole(Role.valueOf(role.toUpperCase()));
                    }
                    user.setPassword(pass);
                    user.setFirstName(firstName);
                    user.setSecondName(secondName);
                    user.setEmail(email);
                    userService.update(user);
                    if (getAuthUser(session).getId().equals(user.getId())) {
                        session.removeAttribute(SessionAttribute.AUTHORIZED_USER);
                        session.setAttribute(SessionAttribute.AUTHORIZED_USER, user);
                    }
                    request.setAttribute(RequestAttribute.USER, user);
                    request.setAttribute(
                            RequestAttribute.SUCCESS_MESSAGE_USER_UPDATE, message.getText(currentLocale, SUCCESS_USER_UPDATE));
                    return new Router(PagePath.USER_PROFILE, Router.RouterType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating user: ", e);
                return new Router(PagePath.ERROR_PAGE_500,Router.RouterType.FORWARD);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, message.getText(currentLocale, USER_NOT_UPDATE));
        return new Router(PagePath.USER_PROFILE, Router.RouterType.FORWARD);
    }
}
