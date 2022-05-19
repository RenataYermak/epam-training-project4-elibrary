package by.yermak.yermak.eliblary.controller.command.user;

import by.yermak.yermak.eliblary.controller.*;
import by.yermak.yermak.eliblary.service.exception.ServiceException;
import by.yermak.yermak.eliblary.service.impl.UserServiceImpl;
import by.yermak.yermak.eliblary.controller.command.Command;
import by.yermak.yermak.eliblary.model.user.Role;
import by.yermak.yermak.eliblary.model.user.User;
import by.yermak.yermak.eliblary.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    public EditUserCommand() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public ResponseContext execute(HttpServletRequest request, HttpSession session) {
        LOGGER.log(Level.INFO, "method execute()");
        if (isAuthorized(session)) {
            try {
                Long id = Long.parseLong(request.getParameter(RequestParam.USER_ID));
                String login = request.getParameter(RequestParam.USER_LOGIN);
                String pass = request.getParameter(RequestParam.USER_PASSWORD);
                String firstName = request.getParameter(RequestParam.USER_FIRSTNAME);
                String secondName = request.getParameter(RequestParam.USER_SECONDNAME);
                String email = request.getParameter(RequestParam.USER_EMAIL);
                String role = request.getParameter(RequestParam.USER_ROLE);
                User user = userService.findUser(id);
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
                            RequestAttribute.SUCCESS_MESSAGE_USER_UPDATE, "User updated successfully");
                    return new ResponseContext(PagePath.USER, ResponseContext.ResponseContextType.FORWARD);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, "error during updating user: ", e);
            }
        }
        return new ResponseContext(PagePath.USER, ResponseContext.ResponseContextType.FORWARD);
    }
}
