package by.yermak.eliblary.controller.command.user;

import by.yermak.eliblary.controller.PagePath;
import by.yermak.eliblary.service.exception.ServiceException;
import by.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.eliblary.controller.RequestAttribute;
import by.yermak.eliblary.controller.RequestParam;
import by.yermak.eliblary.controller.Router;
import by.yermak.eliblary.controller.command.Command;
import by.yermak.eliblary.model.user.Role;
import by.yermak.eliblary.model.user.User;
import by.yermak.eliblary.service.UserService;
import by.yermak.eliblary.util.email.MailLanguageText;
import by.yermak.eliblary.util.email.MessagesKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public RegistrationCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Router execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        String currentLocale = request.getSession().getAttribute(RequestAttribute.LOCALE_NAME).toString();
        if (isAdmin(session)) {
            try {
                String login = request.getParameter(RequestParam.USER_LOGIN);
                String password = request.getParameter(RequestParam.USER_PASSWORD);
                String role = request.getParameter(RequestParam.USER_ROLE);
                String firstName = request.getParameter(RequestParam.USER_FIRSTNAME);
                String secondName = request.getParameter(RequestParam.USER_SECONDNAME);
                String email = request.getParameter(RequestParam.USER_EMAIL);
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setSecondName(secondName);
                if (!userService.isEmailExist(email)) {
                    user.setEmail(email);
                } else {
                    request.setAttribute(
                            RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, "Email is already exists ");
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                user.setRole(Role.valueOf(role.toUpperCase()));
                user = userService.create(user);
                userService.sendEmailRegisteredUser(firstName, secondName, email, currentLocale);
                if (user.getId() != null) {
                    LOGGER.log(Level.INFO, "user was registered successfully");
                    List<User> allUsers = userService.findAll();
                    request.setAttribute(RequestAttribute.USERS, allUsers);
                }

                request.setAttribute(
                        RequestAttribute.SUCCESS_MESSAGE_USER_UPDATE, "User was registered  successfully");
                return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during user registration: ", e);
            }
        }
        request.setAttribute(
                RequestAttribute.WARNING_MESSAGE_PASS_MISMATCH, "User didn't registered ");
        return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
    }
}
